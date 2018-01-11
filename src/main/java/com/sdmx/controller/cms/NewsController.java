package com.sdmx.controller.cms;

import com.google.common.base.Joiner;
import com.sdmx.data.Category;
import com.sdmx.data.News;
import com.sdmx.data.repository.AccountRepository;
import com.sdmx.data.repository.CategoryRepository;
import com.sdmx.data.repository.NewsRepository;
import com.sdmx.support.app.ModelEntity;
import com.sdmx.support.app.ModelEntityFactory;
import com.sdmx.support.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/news")
@PreAuthorize("hasAuthority('news.view')")
class NewsController {

    @Autowired
    private NewsRepository repo;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Pageable pageable;

    private ModelEntity me;

    public NewsController(ModelEntityFactory modelEntityFactory) {
        me = modelEntityFactory.create(News.class);
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("data", repo.findAll(pageable));

        return "admin/news/list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAuthority('news.create')")
    public String create(Model model) {
        model.addAttribute("title", "Create News");
        model.addAttribute("model", new News());
        model.addAttribute("categoryOpt", getCategoriesOpt());

        return "admin/news/form";
    }

    @PostMapping(path = "create", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('news.create')")
    public String store(@RequestParam Map<String, Object> param,
                        @RequestParam("attachments") MultipartFile[] attachments,
                        @RequestParam("categories") Long[] categories,
                        Principal principal) {
        if (principal != null) {
            param.put("account", accountRepository.findOneByEmail(principal.getName()));
            News entity = (News) me.fill(new News(), param);
            EntityManager em = me.createEntityManager();

            for (Long category_id : categories) {
                entity.addCategory(em.getReference(Category.class, category_id));
            }

            em.close();
            repo.save(entity);

            if (entity != null) {
                entity.upload(attachments);
            }
        }
        else {
            System.out.println("User Not Authorized");
        }

        return "redirect:/admin/news";
    }

    @GetMapping("edit/{id}")
    @PreAuthorize("hasAuthority('news.update')")
    public String edit(Model model, @PathVariable("id") long id) {
        News entity = (News) me.findOrFail(id);

        model.addAttribute("title", "Edit News");
        model.addAttribute("model", entity);
        model.addAttribute("categoryOpt", getCategoriesOpt());
        model.addAttribute("categoryOpt", getCategoriesOpt());

        ArrayList<String> categorySelOpt = new ArrayList<>();

        for (Category item : entity.getCategories()) {
            categorySelOpt.add(item.getId().toString());
        }

        model.addAttribute("categorySelOpt", Joiner.on(",").join(categorySelOpt));

        return "admin/news/form";
    }

    @PostMapping(path = "edit/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('news.update')")
    public String update(@RequestParam Map<String, Object> param,
                         @RequestParam("images") MultipartFile[] attachments,
                         @RequestParam(value = "categories", required = false) Long[] categories,
                         @PathVariable("id") long id) {
        News entity = (News) me.findOrFail(id);
        entity.emptyCategory();
        me.fill(entity, param);

        EntityManager em = me.createEntityManager();

        for (Long category_id : categories) {
            entity.addCategory(em.getReference(Category.class, category_id));
        }

        em.close();
        repo.save(entity);

        if (entity != null) {
            entity.upload(attachments);
        }

        return "redirect:/admin/news";
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAuthority('news.delete')")
    public String delete(@PathVariable("id") long id) {
        News entity = (News) me.findOrFail(id);
        entity.deleteFile(null);
        me.delete(entity);

        return "redirect:/admin/news";
    }

    @ResponseBody
    @PostMapping("delete-file/{id}")
    @PreAuthorize("hasAuthority('news.edit') or hasAuthority('news.delete')")
    public String deleteFile(@PathVariable("id") long id,
                             @RequestParam("key") String imgName,
                             HttpServletRequest req,
                             HttpServletResponse res
    ) throws IOException {
        News entity = (News) me.findOrFail(id);
        entity.deleteFile(imgName);

        if (!HttpUtils.isAjax(req)) {
            res.sendRedirect("/admin/news");
            return null;
        }
        else return "{}";
    }

    private Map<Long, String> getCategoriesOpt() {
        List<Category> data = categoryRepository.findAllByOrderByName();
        Map<Long, String> options = new LinkedHashMap<>();

        for (Category item : data) {
            options.put(item.getId(), item.getName());
        }

        return options;
    }
}
