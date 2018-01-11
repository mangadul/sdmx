package com.sdmx.controller;

import com.sdmx.support.util.HttpUtils;
import com.sdmx.support.util.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;

@Controller
class FileController {

	@GetMapping("file")
	public ResponseEntity<byte[]> view(@RequestParam("p") String filepath) {
		Path path = Paths.get(IOUtils.storagePath(filepath));

		return HttpUtils.fileResponse(path);
	}

	@GetMapping(value = "raw"/*, produces = MediaType.IMAGE_JPEG_VALUE*/)
	public @ResponseBody byte[] getImageWithMediaType(@RequestParam("p") String filepath) throws IOException {
		Path path = Paths.get(IOUtils.storagePath(filepath));

		return Files.readAllBytes(path);
	}
}
