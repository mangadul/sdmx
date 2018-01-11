package com.sdmx.controller.cms;

import com.sdmx.data.Account;
import com.sdmx.data.Category;
import com.sdmx.data.Role;
import com.sdmx.data.repository.CategoryRepository;
import com.sdmx.form.OptionUtils;
import com.sdmx.support.app.ModelEntity;
import com.sdmx.support.app.ModelEntityFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Map;

@Controller
@RequestMapping("admin/category")
@PreAuthorize("hasAuthority('category.view')")
class CategoryController {

    @Autowired
    private CategoryRepository repo;

    @Autowired
    private Pageable pageable;

    private ModelEntity me;

    public CategoryController(ModelEntityFactory modelEntityFactory) {
        me = modelEntityFactory.create(Category.class);
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("data", repo.findAll(pageable));

        return "admin/category/list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAuthority('category.create')")
    public String create(Model model) {
        model.addAttribute("title", "Create Category");
        model.addAttribute("model", new Category());
        model.addAttribute("categoryOpt", OptionUtils.getCategoriesOpt());

        return "admin/category/form";
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('category.create')")
    public String store(@RequestParam Map<String, Object> param) {
        Category entity = (Category) me.fill(new Category(), param);
        String parent_id = (String) param.get("parent_id");

        if (parent_id != null) {
            entity.setParent(new Long(parent_id));
        }

        repo.save(entity);

        return "redirect:/admin/category";
    }

    @GetMapping("edit/{id}")
    @PreAuthorize("hasAuthority('category.update')")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("title", "Edit Category");
        model.addAttribute("model", repo.findOne(id));
        model.addAttribute("categoryOpt", OptionUtils.getCategoriesOpt());

        return "admin/category/form";
    }

    @PostMapping("edit/{id}")
    @PreAuthorize("hasAuthority('category.update')")
    public String update(@RequestParam Map<String, Object> param, @PathVariable("id") long id) {
        Category entity = (Category) me.findOrFail(id);
        entity.setName((String) param.get("name"));

        String parent_id = (String) param.get("parent_id");
        EntityManager em = me.createEntityManager();

        if (parent_id != null) {
            entity.setParent(em.getReference(Category.class, new Long(parent_id)));
        }

        em.close();
        repo.save(entity);

        return "redirect:/admin/category";
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAuthority('category.delete')")
    public String delete(@PathVariable("id") long id) {
        me.deleteOrFail(id);

        return "redirect:/admin/category";
    }
}
