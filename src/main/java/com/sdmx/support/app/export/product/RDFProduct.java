package com.sdmx.support.app.export.product;

import com.sdmx.support.app.export.FileExporter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Ramdhan on 09/01/2018.
 */
public class RDFProduct implements FileExporter {

    private Model model = ModelFactory.createDefaultModel();

    private String filepath;

    public RDFProduct(String filepath) {
        this.filepath = filepath;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public void addItem(Iterable item) throws IOException {

    }

    @Override
    public void close() throws IOException {
        model.write(new FileWriter(filepath));
        model.close();
    }
}
