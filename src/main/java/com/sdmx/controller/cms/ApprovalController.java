package com.sdmx.controller.cms;

import com.sdmx.data.News;
import com.sdmx.data.NewsApproval;
import com.sdmx.data.Publication;
import com.sdmx.data.PublicationApproval;
import com.sdmx.data.repository.*;
import com.sdmx.support.app.ModelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("admin/approval")
class ApprovalController {

    @Autowired
    private NewsApprovalRepository newsApprovalRepository;

    @Autowired
    private PublicationApprovalRepository publicationApprovalRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Pageable pageable;

    private ModelEntity me;

    /** NEWS */

    @GetMapping({"", "/", "news"})
    @PreAuthorize("hasAuthority('approval.news')")
    public String listNews(Model model) {
        model.addAttribute("type", "news");
        model.addAttribute("data", newsRepository.findAllByStatus('0', pageable));

        return "admin/approval/list";
    }

    @PostMapping("news/approve/{id}")
    @PreAuthorize("hasAuthority('approval.news')")
    public String approveNews(Principal principal, @PathVariable("id") Long id) {
        if (principal == null) {
            throw new AccessDeniedException("User not authenticated!");
        }

        News content = (News) newsRepository.findOne(id);

        newsApprovalRepository.save(new NewsApproval(
            content, accountRepository.findOneByEmail(principal.getName())
        ));

        content.setStatus('1');
        newsRepository.save(content);

        return "redirect:/admin/approval";
    }

    @PostMapping("news/deny/{id}")
    @PreAuthorize("hasAuthority('approval.news')")
    public String denyNews(Principal principal, @PathVariable("id") Long id) {
        News content = (News) newsRepository.findOne(id);
        content.setStatus('2');
        newsRepository.save(content);

        return "redirect:/admin/approval/news";
    }

    /** PUBLICATION */

    @GetMapping("publication")
    @PreAuthorize("hasAuthority('approval.publication')")
    public String listPublication(Model model) {
        model.addAttribute("type", "publication");
        model.addAttribute("data", publicationRepository.findAllByStatus('0', pageable));

        return "admin/approval/list";
    }

    @PostMapping("publication/approve/{id}")
    @PreAuthorize("hasAuthority('approval.publication')")
    public String approvePublication(Principal principal, @PathVariable("id") Long id) {
        if (principal == null) {
            throw new AccessDeniedException("User not authenticated!");
        }

        Publication content = (Publication) publicationRepository.findOne(id);

        publicationApprovalRepository.save(new PublicationApproval(
            content, accountRepository.findOneByEmail(principal.getName())
        ));

        content.setStatus('1');
        publicationRepository.save(content);

        return "redirect:/admin/approval/publication";
    }

    @PostMapping("publication/deny/{id}")
    @PreAuthorize("hasAuthority('approval.publication')")
    public String denyPublication(Principal principal, @PathVariable("id") Long id) {
        Publication content = (Publication) publicationRepository.findOne(id);
        content.setStatus('2');
        publicationRepository.save(content);

        return "redirect:/admin/approval/publication";
    }
}
