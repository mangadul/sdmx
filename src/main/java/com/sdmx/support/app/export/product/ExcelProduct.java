package com.sdmx.support.app.export.product;

import com.sdmx.support.app.export.FileExporter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ramdhan on 09/01/2018.
 */
public class ExcelProduct implements FileExporter {

    private Workbook workbook;

    private String filepath;

    private String type = "xlsx";

    private FileOutputStream out;

    private Map<String, Sheet> sheets = new HashMap<>();

    private Sheet currentSheet;

    private int row_count = 0;

    public ExcelProduct(String filepath, String type) {
        this.filepath = filepath;
        this.type = type;
        init();
    }

    public ExcelProduct(String filepath) {
        this.filepath = filepath;
        init();
    }

    private void init() {
        workbook = type=="xlsx" ? new XSSFWorkbook() : new HSSFWorkbook();
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet createSheet(String name, String sheetName) {
        Sheet sheet;

        if (sheetName == null) {
            sheet = workbook.createSheet();
        }
        else {
            sheet = workbook.createSheet(sheetName);
        }

        sheets.put(name, sheet);
        currentSheet = sheet;

        return sheet;
    }

    public Sheet useSheet(String name) {
        currentSheet = getSheet(name);
        return currentSheet;
    }

    public Row insertRow() {
        return currentSheet.createRow(row_count++);
    }

    public Sheet getSheet(String name) {
        return sheets.get(name);
    }

    public Sheet getSheet() {
        return currentSheet;
    }

    @Override
    public void addItem(Iterable item) throws IOException {
        Row row = insertRow();
        int j = 0;

        for (Object data : item) {
            row.createCell(j++).setCellValue(data.toString());
        }
    }

    @Override
    public void close() throws IOException {
        FileOutputStream out = new FileOutputStream(new File(filepath));
        workbook.write(out);

        out.close();
        workbook.close();
    }
}
