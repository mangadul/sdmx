package com.sdmx.support.app.export.product;

import com.sdmx.support.app.export.FileExporter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ramdhan on 09/01/2018.
 */
public class XMLProduct implements FileExporter {

    private String filepath;

    private Document document;

    private Element root;

    private Element parent;

    public XMLProduct(String filepath) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        document = docBuilder.newDocument();
        this.filepath = filepath;
    }


    protected void initDefault() {
        setParent("data");
    }

    protected void setRoot(Element element) {
        root = element;

        if (parent != null) {
            parent = element;
        }
    }

    public void setParent(String elementName) {
        Element element = document.createElement(elementName);

        if (parent == null) {
            document.appendChild(element);
        }
        else {
            parent.appendChild(element);
        }

        setParent(element);
    }

    public void setParent(Element element) {
        parent = element;

        if (root == null) {
            setRoot(element);
        }
    }

    public Element getParent() {
        return parent;
    }

    public void addItem(Map<String, Object> item) {
        for (Map.Entry<String, Object> map : item.entrySet()) {
            Element element = document.createElement(map.getKey());
            element.appendChild(document.createTextNode(map.getValue().toString()));

            append(element);
        }
    }

    @Override
    public void addItem(Iterable item) throws IOException {
        if (parent == null) {
            initDefault();
        }

        String tagName = parent.getTagName();
        Node parentNode = parent.getParentNode();

        for (Object data : item) {
            Element element = document.createElement(tagName);
            element.appendChild(document.createTextNode(data.toString()));

            parentNode.appendChild(element);
        }
    }

    public void append(Element element) {
        if (parent == null) {
            initDefault();
        }

        parent.appendChild(element);
    }

    @Override
    public void close() throws IOException {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(new File(filepath));

            transformer.transform(new DOMSource(document), result);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to write into file");
        }
    }
}
