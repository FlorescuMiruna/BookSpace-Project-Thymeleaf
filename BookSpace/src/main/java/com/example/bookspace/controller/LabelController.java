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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/update-label-form/{id}")
    public String updateLabelForm(@PathVariable(value = "id") long id, Model model) {
        Label label = labelService.getLabelById(id);
        model.addAttribute("label", label);
        return "update-label-form";
    }
    @PostMapping("/save")
    public String saveLabel(@Valid @ModelAttribute("label") Label label, BindingResult bindingResult) {
        labelService.saveLabel(label);

        if(bindingResult.hasErrors()){
            return "redirect:/add-label-form";
        }

        return "redirect:/labels";
    }

    @GetMapping("/deleteLabel/{id}")
    public String deleteLabel(@PathVariable(value = "id") long id) {
//        System.out.println("deleteAuthor");
        labelService.deleteLabel(id);
        return "redirect:/labels";

    }
}
