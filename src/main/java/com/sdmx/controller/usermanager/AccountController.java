package com.sdmx.controller.usermanager;

import com.sdmx.data.Account;
import com.sdmx.data.AccountService;
import com.sdmx.data.Role;
import com.sdmx.error.exception.FormValidationException;
import com.sdmx.support.app.ModelEntity;
import com.sdmx.support.app.ModelEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("admin/account")
@PreAuthorize("hasAuthority('account.view')")
public class AccountController {

    private ModelEntity me;

    private ModelEntity role;

    @Autowired
    private AccountService accountService;

    public AccountController(ModelEntityFactory modelEntityFactory) {
        me = modelEntityFactory.create(Account.class);
        role = modelEntityFactory.create(Role.class);
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("data", me.all());
        return "admin/account/list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAuthority('account.create')")
    public String create(Model model) {
        model.addAttribute("title", "Create New Account");
        model.addAttribute("model", new Account());

        // Roles
        Map<Long, String> roles = new LinkedHashMap<>();

        for (Role role : (List<Role>) role.all()) {
            roles.put(role.getId(), role.getName());
        }

        model.addAttribute("roles", roles);

        return "admin/account/form";
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('account.create')")
    public String store(HttpServletRequest req, @RequestParam Map<String, Object> param) {
        if (!param.get("password").equals(param.get("password_confirm"))) {
            throw new FormValidationException("validation.confirmed");
        }

        Account account = (Account) me.fill(new Account(), param);
        EntityManager em = me.createEntityManager();

        // assign roles into account
        for (String role_id : req.getParameterValues("roles")) {
            account.addRole(em.getReference(Role.class, Long.parseLong(role_id)));
        }

        accountService.save(account);

        return "redirect:/admin/account";
    }

    @GetMapping("edit/{id}")
    @PreAuthorize("hasAuthority('account.update')")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("title", "Edit Account");

        // Model
        Account account = (Account) me.findOrFail(id);
        model.addAttribute("model", account);

        // Roles
        Map<Long, String> roles = new LinkedHashMap<>();

        for (Role role : (List<Role>) role.all()) {
            roles.put(role.getId(), role.getName());
        }

        model.addAttribute("roles", roles);

        // Role Values
        List<Long> role_values = new LinkedList<>();

        for (Role role : account.getRoles()) {
            role_values.add(role.getId());
        }

        model.addAttribute("role_values", role_values);

        return "admin/account/form";
    }

    @PostMapping("edit/{id}")
    @PreAuthorize("hasAuthority('account.update')")
    public String update(HttpServletRequest req, @RequestParam Map<String, Object> param, @PathVariable("id") long id) {
        if (!param.get("password").equals(param.get("password_confirm"))) {
            throw new FormValidationException("validation.confirmed");
        }

        if (param.get("password").toString().trim() == "") {
            param.remove("password");
        }

        Account account = (Account) me.fill(me.findOrFail(id), param);
        Set<Role> roles = new HashSet<>();

        EntityManager em = me.createEntityManager();
        em.detach(account);

        // assign roles into account
        for (String role_id : req.getParameterValues("roles")) {
            roles.add(em.getReference(Role.class, new Long(role_id)));
        }

        account.setRoles(roles);
        accountService.save(account);

        return "redirect:/admin/account";
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAuthority('account.delete')")
    public String delete(@PathVariable("id") long id) {
        me.deleteOrFail(id);
        return "redirect:/admin/account";
    }
}
