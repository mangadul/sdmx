package com.sdmx.support.util;

import com.sdmx.error.exception.HttpException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

public final class HttpUtils {

    public final static HttpHeaders fileResponseHeader(HttpHeaders headers, Path filepath) throws IOException {
        headers.add("Cache-Control", "no-cache, must-revalidate");
        headers.add("Content-Description", "File Transfer");
        headers.add("Content-Transfer-Encoding", "binary");
        headers.add("Expires", "0");

        if (!headers.containsKey("Content-Type")) {
//            String contentType = Files.probeContentType(filepath);
            String contentType = URLConnection.guessContentTypeFromName(filepath.getFileName().toString());

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            headers.add("Content-Type", contentType);
        }

        return headers;
    }

    public final static ResponseEntity<byte[]> downloadResponse(
        HttpHeaders headers,
        Path filepath,
        String downloadName,
        boolean deleteAfterDownload
    ) {
        headers.add("Content-Disposition", "attachment; filename="+ downloadName);

        try {
            fileResponseHeader(headers, filepath);
            headers.setContentLength(Files.size(filepath));

            byte[] filebytes = Files.readAllBytes(filepath);

            if (deleteAfterDownload) {
                filepath.toFile().delete();
            }

            return new ResponseEntity<byte[]>(filebytes, headers, HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        throw new HttpException(404, "File not found.");
    }

    public final static ResponseEntity<byte[]> downloadResponse(
        Path filepath,
        String downloadName,
        boolean deleteAfterDownload
    ) {
        return downloadResponse(new HttpHeaders(), filepath, downloadName, deleteAfterDownload);
    }

    public final static ResponseEntity<byte[]> fileResponse(HttpHeaders headers, Path filepath) {
        headers.add("Content-Disposition", "inline; filename="+filepath.getFileName());

        try {
            fileResponseHeader(headers, filepath);
            byte[] filebytes = Files.readAllBytes(filepath);

            return new ResponseEntity<byte[]>(filebytes, headers, HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        throw new HttpException(404, "File not found.");
    }

    public final static ResponseEntity<byte[]> fileResponse(Path filepath) {
        return fileResponse(new HttpHeaders(), filepath);
    }

    public final static boolean isAjax(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");

        return "XMLHttpRequest".equals(requestedWithHeader);
    }
}
