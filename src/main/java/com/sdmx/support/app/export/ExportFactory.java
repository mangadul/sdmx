package com.sdmx.support.app.export;

import com.sdmx.support.app.export.product.CSVProduct;
import com.sdmx.support.app.export.product.ExcelProduct;
import com.sdmx.support.app.export.product.RDFProduct;
import com.sdmx.support.app.export.product.XMLProduct;

/**
 * Created by Ramdhan on 09/01/2018.
 */
public class ExportFactory {

    public final static FileExporter create(String type, String filepath) throws Exception {
        switch (type) {
            case "csv": return new CSVProduct(filepath);
            case "rdf": return new RDFProduct(filepath);
            case "xml": return new XMLProduct(filepath);
            case "xls": case "xlsx": return new ExcelProduct(filepath, type);
        }

        throw new Exception("Cannot find FileExporter: "+ type);
    }
}
