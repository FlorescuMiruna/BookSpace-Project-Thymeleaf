package com.example.bookspace.controller;

import com.example.bookspace.model.Author;
import com.example.bookspace.model.Book;
import com.example.bookspace.model.Label;
import com.example.bookspace.service.LabelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/labels")
public class LabelController {

    private LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("")
    public String viewLabels(Model model) {
        model.addAttribute("labelList", labelService.getAllLabels());
        return "labels";
    }

    @GetMapping("/add-label-form")
    public String addLabelForm(Model model) {
        Label label = new Label();

        model.addAttribute("label", label);
        return "add-label-form";
    }
//    @GetMapping("/add-author-form")
//    public String addAuthorForm(Model model) {
//        Author author = new Author();
////        AuthorDetails authorDetails = new AuthorDetails("test");
////        author.setAuthorDetails(authorDetails);
//        model.addAttribute("author", author);
//        return "add-author-form";
//    }

    @PostMapping("/save")
    public String saveLabel(@Valid @ModelAttribute("label") Label label, BindingResult bindingResult) {
        labelService.saveLabel(label);

        if(bindingResult.hasErrors()){
            return "redirect:/add-label-form";
        }

        return "redirect:/labels";
    }
}
