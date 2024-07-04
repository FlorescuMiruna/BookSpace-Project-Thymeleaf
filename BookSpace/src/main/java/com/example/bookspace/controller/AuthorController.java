package com.example.bookspace.controller;

import com.example.bookspace.exception.ResourceNotFoundException;
import com.example.bookspace.model.Author;
import com.example.bookspace.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public String viewHomePage(Model model) {
        model.addAttribute("authorslist", authorService.getAllAuthors());
        return "authors";
    }

    @GetMapping("/add-author-form")
    public String addAuthorForm(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "add-author-form";
    }

    @GetMapping("/update-author-form/{id}")
    public String updateAuthorForm(@PathVariable(value = "id") long id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "update-author-form";
    }

    @PostMapping("/save")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author, BindingResult bindingResult) {

        authorService.saveAuthor(author);
        if(bindingResult.hasErrors()){
            System.out.println("testt  if(bindingResult.hasErrors()){)");
            return "redirect:/add-author-form";
        }

        return "redirect:/authors";
    }

    @GetMapping("/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable(value = "id") long id) {
        System.out.println("deleteAuthor");
        authorService.deleteAuthor(id);
        return "redirect:/authors";

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handlerNotFoundException(Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception",exception);
        modelAndView.setViewName("notFoundException");
        return modelAndView;
    }
}