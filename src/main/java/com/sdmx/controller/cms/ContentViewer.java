package com.sdmx.controller.cms;

import com.sdmx.config.WebMvcConfig;
import com.sdmx.data.News;
import com.sdmx.data.repository.ContentRepository;
import com.sdmx.support.util.URLUtils;
import com.sdmx.support.web.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Map;

public class ContentViewer {

    private ContentRepository repo;

    private Pageable pageable;

    public ContentViewer(ContentRepository repo, Pageable pageable) {
        this.pageable = pageable;
        this.repo = repo;
    }

    public void list(Model model, Map<String, Object> param) {
        String query = (String) param.getOrDefault("q", "");
        String category = (String) param.getOrDefault("cat", "");

        Page<News> data;
        long dataCount = 0;

        // saerch content by querying title
        if (category.length() == 0) {
            data = repo.findByTitle('%'+query+'%', pageable);
            dataCount = repo.countAllByTitle('%'+query+'%');
        }
        // search general content
        else if (category.equals("general")) {
            data = repo.findGeneralByTitle('%'+query+'%', pageable);
        }
        // search by category_id
        else {
            data = repo.findByTitle(
                '%'+query+'%',
                Arrays.asList(new Long[] {Long.parseLong(category)}),
                pageable
            );
        }

        model.addAttribute("query", query);
        model.addAttribute("category", category);
        model.addAttribute("data", data);
        model.addAttribute("page", new Pagination(dataCount, WebMvcConfig.PAGE_RECORD_SIZE, pageable));

        param.remove("page");
        model.addAttribute("httpQuery", URLUtils.requestParamBuild(param));
    }
}
