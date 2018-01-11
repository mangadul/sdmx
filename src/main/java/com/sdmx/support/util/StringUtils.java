package com.sdmx.support.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public final class StringUtils {

    public final static String uuid() {
        return UUID.randomUUID().toString();
    }

    public final static String timeId() {
        return new SimpleDateFormat("yyMMddHHmmss").format(new Date());
    }
}
