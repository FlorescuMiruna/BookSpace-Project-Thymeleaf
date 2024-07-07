package com.example.bookspace.controller;

import com.example.bookspace.exception.ResourceNotFoundException;
import com.example.bookspace.model.Author;
import com.example.bookspace.model.AuthorDetails;
import com.example.bookspace.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger log = Logger.getLogger(LabelController.class.getName());

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public String viewAllAuthorsPage(Model model) {
        model.addAttribute("authorslist", authorService.getAllAuthors());

        log.info("Successfully loaded Authors page");

        return "authors";
    }

    @GetMapping("/page/{id}")
    public String getAuthorInfoPage(@PathVariable(value = "id") long id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);

        log.info("Successfully loaded the Author Info Page");

        return "author-info.html";
    }

    @GetMapping("/add-author-form")
    public String addAuthorForm(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);

        log.info("Successfully loaded the Add Author Form");

        return "add-author-form";
    }

    @GetMapping("/update-author-form/{id}")
    public String updateAuthorForm(@PathVariable(value = "id") long id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);

        log.info("Successfully loaded the Update Author Form");

        return "update-author-form";
    }

    @PostMapping("/save")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author, @ModelAttribute AuthorDetails authorDetails,BindingResult bindingResult) {
        authorService.saveAuthor(author);

        log.info("Successfully saved author with ID: " + author.getId());

        if(bindingResult.hasErrors()){
            return "redirect:/add-author-form";
        }

        return "redirect:/authors";
    }

    @GetMapping("/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable(value = "id") long id) {
        authorService.deleteAuthor(id);

        log.info("Author with ID: " + id + " deleted successfully.");

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

    @GetMapping("/sortAuthors")
    public String sortAuthors(Model model, @RequestParam(name = "sort", required = false) String sortCriteria) {
        System.out.println(" ** sortAuthors");
        List<Author> authorsList = authorService.getAllAuthors();

        if (sortCriteria != null) {
            if (sortCriteria.equals("alphabetic")) {
                authorsList = authorsList.stream()
                        .sorted((a1, a2) -> (a1.getFirstName() + a1.getLastName())
                                .compareTo(a2.getFirstName() + a2.getLastName()))
                        .collect(Collectors.toList());
            } else if (sortCriteria.equals("birth-year")) {
                authorsList = authorsList.stream()
                        .sorted((a1, a2) -> Integer.compare(a1.getBirthYear(), a2.getBirthYear()))
                        .collect(Collectors.toList());
            }
        }

        model.addAttribute("authorslist", authorsList);
        for(Author author : authorsList){
            System.out.println(author);
        }
        return "authors";
    }

}