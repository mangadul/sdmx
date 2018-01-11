package com.sdmx.controller.cms;

import com.sdmx.config.WebMvcConfig;
import com.sdmx.controller.menu.CategoryMenu;
import com.sdmx.controller.menu.MenuContainer;
import com.sdmx.data.News;
import com.sdmx.data.repository.NewsRepository;
import com.sdmx.support.app.ModelEntity;
import com.sdmx.support.app.ModelEntityFactory;
import com.sdmx.support.app.menu.Collection;
import com.sdmx.support.util.IOUtils;
import com.sdmx.support.util.URLUtils;
import com.sdmx.support.web.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("news")
class NewsViewController {

    @Autowired
    private NewsRepository repo;

    @Autowired
    private Pageable pageable;

    private ModelEntity me;

    public NewsViewController(ModelEntityFactory modelEntityFactory) {
        me = modelEntityFactory.create(News.class);
    }

    @GetMapping(path = {"", "/"})
    public String list(Model model, @RequestParam Map<String, Object> param) {
        ContentViewer cv = new ContentViewer(repo, pageable);
        cv.list(model, param);

        return "cms/news/list";
    }

    @GetMapping("{id}")
    public String show(Model model, @PathVariable("id") long id) {
        News entity = (News) me.findOrFail(id);
        model.addAttribute("model", entity);
        model.addAttribute("images", entity.getImagesUrl());

        return "cms/news/show";
    }
}
