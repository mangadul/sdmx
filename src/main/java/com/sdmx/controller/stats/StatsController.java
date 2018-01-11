package com.sdmx.controller.stats;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sdmx.controller.cms.ContentViewer;
import com.sdmx.controller.menu.CategoryMenu;
import com.sdmx.controller.menu.MenuContainer;
import com.sdmx.data.DataFlow;
import com.sdmx.data.DataSet;
import com.sdmx.data.repository.DataFlowRepository;
import com.sdmx.data.repository.DataSetRepository;
import com.sdmx.error.exception.HttpException;
import com.sdmx.support.app.export.ExportFactory;
import com.sdmx.support.app.export.FileExporter;
import com.sdmx.support.app.export.product.CSVProduct;
import com.sdmx.support.app.export.product.ExcelProduct;
import com.sdmx.support.app.export.product.RDFProduct;
import com.sdmx.support.app.export.product.XMLProduct;
import com.sdmx.support.app.menu.*;
//import com.sdmx.support.app.menu.Collection;
import com.sdmx.support.util.HttpUtils;
import com.sdmx.support.util.IOUtils;
import com.sdmx.support.util.StringUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("stats")
class StatsController {

	@Autowired
	private DataFlowRepository dataFlowRepository;

	@Autowired
	private DataSetRepository dataSetRepository;

	@Autowired
	private Pageable pageable;

	@GetMapping(path = {"", "/"})
	public String list(Model model, @RequestParam Map<String, Object> param) {
		ContentViewer cv = new ContentViewer(dataFlowRepository, pageable);
		cv.list(model, param);

		return "stats/list";
	}

	@GetMapping("{data_flow_id}")
	public String stats(Model model, @PathVariable("data_flow_id") Long id) {
		DataFlow dataFlow = dataFlowRepository.findOne(id);

		model.addAttribute("title", dataFlow.getTitle());
		model.addAttribute("dataFlow", dataFlow);
		model.addAttribute("dataFlowId", id);

		Map<String, String> chartTypes = new HashMap<>();
		chartTypes.put("line", "Line");
		chartTypes.put("column", "Bar");
		chartTypes.put("scatter", "Scatter");
		model.addAttribute("chartTypes", chartTypes);

		return "stats/view";
	}

	@GetMapping("data/{data_flow_id}")
	@ResponseBody
	public String data(@PathVariable("data_flow_id") Long id) {
		try {
			return (new ObjectMapper()).writeValueAsString(getDataFlow(id));
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected List<Map<String, Object>> getDataFlow(Long id) {
		List<Map<String, Object>> data = new ArrayList<>();

		for (DataSet item : dataSetRepository.findByDataFlow(id)) {
			Map<String, Object> object = new HashMap<>();

			// merge data with json attributes
			JsonNode attributes = item.getAttributesAsJson();
			Set<String> extAttributes = new HashSet<>();

			if (attributes != null) {
				Iterator<Map.Entry<String, JsonNode>> nodes = attributes.fields();

				while (nodes.hasNext()) {
					Map.Entry<String, JsonNode> entry = nodes.next();
					object.put(entry.getKey(), entry.getValue().textValue());
					extAttributes.add(entry.getKey());
				}
			}

			object.put("extAttributes", extAttributes);
			object.put("variable", item.getVariable().getName());
			object.put("year", item.getYear());
			object.put("month", item.getMonth());
			object.put("value", item.getValue());
			data.add(object);
		}

		return data;
	}

	@PostMapping("export/{data_flow_id}")
	ResponseEntity<byte[]> export(
		@PathVariable("data_flow_id") Long id,
		@RequestParam Map<String, Object> param
	) {
		String type = (String) param.getOrDefault("t", "");
		String downloadName = "export."+type;

		String filepath = IOUtils.storagePath("tmp/"+ StringUtils.uuid()) + "." + type;
		Path path = Paths.get(filepath);

		try {
			switch (type) {
				case "pdf":
					Document document = new Document();

					PdfWriter.getInstance(document, new FileOutputStream(filepath));
					document.open();

					PdfPTable table = null;

					for (Map<String, Object> item : getDataFlow(id)) {
						if (table == null) {
							Set<String> keys = item.keySet();
							table = new PdfPTable(keys.size());

							for (String key : keys) {
								PdfPCell header = new PdfPCell();

								header.setBackgroundColor(BaseColor.LIGHT_GRAY);
								header.setBorderWidth(2);
								header.setPhrase(new Phrase(key));

								table.addCell(header);
							}
						}

						for (Map.Entry<String, Object> data : item.entrySet()) {
							table.addCell(data.getValue().toString());
						}
					}

					document.add(table);
					document.close();
					break;

				case "xml":
					XMLProduct xmlProduct = (XMLProduct) ExportFactory.create(type, filepath);

					for (Map<String, Object> item : getDataFlow(id)) {
						xmlProduct.addItem(item);
					}

					xmlProduct.close();
					break;

				case "xls":
					ExcelProduct excelProduct = (ExcelProduct) ExportFactory.create(type, filepath);
					excelProduct.createSheet("data", null);

					int i = 0;

					for (Map<String, Object> item : getDataFlow(id)) {
						LinkedList row = new LinkedList(item.values());
						row.addFirst(i++);
						excelProduct.addItem(row);
					}

					excelProduct.close();
					break;

				case "rdf":
					RDFProduct rdfProduct = (RDFProduct) ExportFactory.create(type, filepath);

					for (Map<String, Object> item : getDataFlow(id)) {
						rdfProduct.getModel()
							.createResource()
							.addProperty(VCARD.NAME, item.get("variable").toString())
							.addProperty(VCARD.SOURCE, item.get("month").toString() + "/" + item.get("year").toString())
							.addProperty(VCARD.Country, item.get("country").toString())
							.addProperty(VCARD.SOURCE, item.get("value").toString());
					}

					rdfProduct.close();
					break;

				case "csv":
					CSVProduct csvProduct = (CSVProduct) ExportFactory.create(type, filepath);

					csvProduct.includeHeader(true);
					csvProduct.addList(getDataFlow(id));
					csvProduct.close();
					break;

				case "json":
					path = IOUtils.filePut(filepath, data(id)).toPath();
					break;

				default: throw new HttpException(404);
			}
		}
		catch (HttpException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new HttpException(500, "Failed to export '"+ type +"' file.");
		}

		return HttpUtils.downloadResponse(path, downloadName, true);
	}

	@PostMapping("chart-export/{export_type}")
	ResponseEntity<byte[]> chartExport(
		@PathVariable("export_type") String exportType,
		@RequestParam("args") String jsonArgs
	) throws IOException {
		JsonNode args = (new ObjectMapper()).readTree(jsonArgs);

		byte[] imageData = Base64.getDecoder().decode(args.get("chartdata").textValue());
		String downloadName = args.get("fileName").textValue() +"."+ exportType;
		String filepath = IOUtils.storagePath("tmp/"+ StringUtils.uuid()) + "." + exportType;
		Path path = Paths.get(filepath);

		try {
			switch (exportType) {
				case "pdf":
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream(filepath));
					document.open();

					Image img = Image.getInstance(imageData);
					img.scalePercent(
						((document.getPageSize().getWidth() - document.leftMargin()
						- document.rightMargin()) / img.getWidth()) * 100
					);

					document.add(img);
					document.close();
					break;

				case "xls":
					ExcelProduct excelProduct = (ExcelProduct) ExportFactory.create(exportType, filepath);
					excelProduct.createSheet("chart", null);

					Workbook wb = excelProduct.getWorkbook();
					int pictureIdx = wb.addPicture(imageData, Workbook.PICTURE_TYPE_PNG);
					CreationHelper helper = wb.getCreationHelper();
					Drawing drawing = excelProduct.getSheet().createDrawingPatriarch();

					ClientAnchor anchor = helper.createClientAnchor();
					anchor.setCol1(2);
					anchor.setRow1(1);

					Picture picture = drawing.createPicture(anchor, pictureIdx);
					picture.resize();

					drawing.createPicture(anchor, pictureIdx);
					excelProduct.close();
					break;

				case "png":
					IOUtils.filePut(filepath, imageData).toPath();
					break;

				default: throw new HttpException(404);
			}
		}
		catch (HttpException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new HttpException(500, "Failed to export '"+ exportType +"' file.");
		}

		return HttpUtils.downloadResponse(path, downloadName, true);
	}
}
