package com.sdmx.controller.cms;

import com.sdmx.data.Publication;
import com.sdmx.data.repository.PublicationRepository;
import com.sdmx.support.app.ModelEntity;
import com.sdmx.support.app.ModelEntityFactory;
import com.sdmx.support.util.IOUtils;
import com.sdmx.support.util.URLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("publication")
class PublicationViewController {

    @Autowired
    private PublicationRepository repo;

    @Autowired
    private Pageable pageable;

    private ModelEntity me;

    public PublicationViewController(ModelEntityFactory modelEntityFactory) {
        me = modelEntityFactory.create(Publication.class);
    }

    @GetMapping(path = {"", "/"})
    public String list(Model model, @RequestParam Map<String, Object> param) {
        ContentViewer cv = new ContentViewer(repo, pageable);
        cv.list(model, param);
        /*String query = (String) param.getOrDefault("q", "");

        model.addAttribute("query", query);
        model.addAttribute("data", repo.findByTitle('%'+query+'%', pageable));
        model.addAttribute(
            "page",
            new Pagination(
                repo.countAllByTitle('%'+query+'%'),
                WebMvcConfig.PAGE_RECORD_SIZE,
                pageable
            )
        );

        param.remove("page");
        model.addAttribute("httpQuery", URLUtils.requestParamBuild(param));*/

        return "cms/publication/list";
    }

    @GetMapping("{id}")
    public String show(Model model, @PathVariable("id") long id) {
        Publication entity = (Publication) me.findOrFail(id);
        model.addAttribute("model", entity);

        List<String> files = new ArrayList<>();

        for (String filepath : entity.getFiles()) {
            files.add(URLUtils.getFileUrl(filepath));
        }

        model.addAttribute("files", files);

        return "cms/publication/show";
    }
}
