package com.sdmx.support.app.export.product;

import com.sdmx.support.app.export.FileExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ramdhan on 09/01/2018.
 */
public class CSVProduct implements FileExporter {

    private boolean is_header_included = false;

    private List<String> headers = new ArrayList<>();

    private CSVPrinter printer;

    private String filepath;

    public CSVProduct(String filepath) {
        this.filepath = filepath;
    }

    public CSVProduct includeHeader(boolean is_header_included) {
        this.is_header_included = is_header_included;

        return this;
    }

    public void addList(Iterable<Map<String, Object>> list) throws IOException {
        for (Map<String, Object> item : list) {
            addItem(item);
        }
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    @Override
    public void addItem(Iterable item) throws IOException {
        if (printer == null) {
            CSVFormat csvFormat = CSVFormat.DEFAULT;

            if (headers.size() > 0) {
                csvFormat.withHeader(headers.toArray(new String[0]));
            }

            printer = new CSVPrinter(new FileWriter(filepath), csvFormat);
        }

        printer.printRecord(item);
    }

    public void addItem(Map<String, Object> item) throws IOException {
        if (printer == null && is_header_included) {
            for (Map.Entry<String, Object> map : item.entrySet()) {
                headers.add(map.getKey());
            }
        }

        addItem(item.values());
    }

    public void close() throws IOException {
        printer.close();
    }
}
