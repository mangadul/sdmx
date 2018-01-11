package com.sdmx.support.app.export;

import java.io.IOException;

/**
 * Created by Ramdhan on 09/01/2018.
 */
public interface FileExporter {

    public void addItem(Iterable item) throws IOException;

    public void close() throws IOException;
}
