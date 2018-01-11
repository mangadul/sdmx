package com.sdmx.controller.cms;

import com.google.common.base.Joiner;
import com.sdmx.data.Category;
import com.sdmx.data.News;
import com.sdmx.data.Publication;
import com.sdmx.data.repository.AccountRepository;
import com.sdmx.data.repository.CategoryRepository;
import com.sdmx.data.repository.PublicationRepository;
import com.sdmx.support.util.HttpUtils;
import com.sdmx.support.util.IOUtils;
import com.sdmx.support.app.ModelEntity;
import com.sdmx.support.app.ModelEntityFactory;
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
@RequestMapping("admin/publication")
@PreAuthorize("hasAuthority('publication.view')")
class PublicationController {

    @Autowired
    private PublicationRepository repo;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Pageable pageable;

    private ModelEntity me;

    public PublicationController(ModelEntityFactory modelEntityFactory) {
        me = modelEntityFactory.create(Publication.class);
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("data", repo.findAll(pageable));

        return "admin/publication/list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAuthority('publication.create')")
    public String create(Model model) {
        model.addAttribute("title", "Create New Publication");
        model.addAttribute("model", new Publication());
        model.addAttribute("categoryOpt", getCategoriesOpt());

        return "admin/publication/form";
    }

    @PostMapping(path = "create", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('publication.create')")
    public String store(@RequestParam Map<String, Object> param,
                        @RequestParam(value = "cover", required = true) MultipartFile cover,
                        @RequestParam("attachments") MultipartFile[] attachments,
                        @RequestParam("categories") Long[] categories,
                        Principal principal) {
        if (principal != null) {
            param.put("account", accountRepository.findOneByEmail(principal.getName()));
            Publication entity = (Publication) me.fill(new Publication(), param);
            EntityManager em = me.createEntityManager();

            for (Long category_id : categories) {
                entity.addCategory(em.getReference(Category.class, category_id));
            }

            em.close();
            repo.save(entity);

            if (entity != null) {
                entity.uploadCover(cover);
                entity.uploadAttachments(attachments);
            }
        }
        else {
            System.out.println("User Not Authorized");
        }

        return "redirect:/admin/publication";
    }

    @GetMapping("edit/{id}")
    @PreAuthorize("hasAuthority('publication.update')")
    public String edit(Model model, @PathVariable("id") long id) {
        Publication entity = (Publication) me.findOrFail(id);

        model.addAttribute("title", "Edit Publication");
        model.addAttribute("model", entity);
        model.addAttribute("categoryOpt", getCategoriesOpt());

        ArrayList<String> categorySelOpt = new ArrayList<>();

        for (Category item : entity.getCategories()) {
            categorySelOpt.add(item.getId().toString());
        }

        model.addAttribute("categorySelOpt", Joiner.on(",").join(categorySelOpt));

        return "admin/publication/form";
    }

    @PostMapping(path = "edit/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('publication.update')")
    public String update(@RequestParam Map<String, Object> param,
                         @RequestParam(value = "cover", required = true) MultipartFile cover,
                         @RequestParam("attachments") MultipartFile[] attachments,
                         @RequestParam(value = "categories", required = false) Long[] categories,
                         @PathVariable("id") long id) {
        Publication entity = (Publication) me.findOrFail(id);
        entity.emptyCategory();
        me.fill(entity, param);

        EntityManager em = me.createEntityManager();

        for (Long category_id : categories) {
            entity.addCategory(em.getReference(Category.class, category_id));
        }

        em.close();
        repo.save(entity);

        if (entity != null) {
            entity.uploadCover(cover);
            entity.uploadAttachments(attachments);
        }

        return "redirect:/admin/publication";
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAuthority('publication.delete')")
    public String delete(@PathVariable("id") long id) {
        me.deleteOrFail(id);

        return "redirect:/admin/publication";
    }

    @ResponseBody
    @PostMapping("delete-file/{id}")
    @PreAuthorize("hasAuthority('publication.edit') or hasAuthority('publication.delete')")
    public String deleteFile(@PathVariable("id") long id,
                             @RequestParam("key") String imgName,
                             HttpServletRequest req,
                             HttpServletResponse res
    ) throws IOException {
        Publication entity = (Publication) me.findOrFail(id);
        entity.deleteFile(imgName);

        if (!HttpUtils.isAjax(req)) {
            res.sendRedirect("/admin/publication");
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
