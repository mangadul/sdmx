package com.sdmx.controller.cms;

import com.sdmx.data.News;
import com.sdmx.data.Publication;
import com.sdmx.data.repository.ContentRepository;
import com.sdmx.data.repository.DataFlowRepository;
import com.sdmx.data.repository.NewsRepository;
import com.sdmx.data.repository.PublicationRepository;
import com.sdmx.support.util.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("search")
class SearchController {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private DataFlowRepository dataflowRepository;

    @GetMapping(path = {"", "/"})
    public String list(Model model, @RequestParam Map<String, Object> param) {
        // pageable
        Sort.Order orders[] = {new Sort.Order(Sort.Direction.DESC, "created")};
        Pageable pageable = new PageRequest(0, 5, new Sort(orders));

        model.addAttribute("query", param.getOrDefault("q", ""));
        model.addAttribute("category", param.getOrDefault("cat", ""));

        // data filter
        model.addAttribute("publication", (Page<Publication>) filter(publicationRepository, param, pageable));
        model.addAttribute("news", (Page<Publication>) filter(newsRepository, param, pageable));
        model.addAttribute("dataFlow", (Page<Publication>) filter(dataflowRepository, param, pageable));

        return "home/search";
    }

    private Page filter(ContentRepository repo, Map<String, Object> param, Pageable pageable) {
        String query = (String) param.getOrDefault("q", "");
        String category = (String) param.getOrDefault("cat", "");
        Page<Object> data;

        if (category.length() == 0) {
            data = repo.findByTitle('%'+query+'%', pageable);
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

        return data;
    }
}
