package com.example.bookspace.controller;


import com.example.bookspace.model.Author;
import com.example.bookspace.model.Book;
import com.example.bookspace.model.Quote;

import com.example.bookspace.service.BookService;
import com.example.bookspace.service.QuoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quotes")
public class QuoteController {

    public final QuoteService quoteService;
    public final BookService bookService;

    @Autowired
    public QuoteController(QuoteService quoteService, BookService bookService) {
        this.quoteService = quoteService;
        this.bookService = bookService;
    }

    @GetMapping("")
    public String viewHomePage(Model model) {
        model.addAttribute("quotesList", quoteService.getAllQuotes());
        return "quotes";
    }

    @GetMapping("/add-quote-form")
    public String addQuoteForm(Model model) {
        Quote quote = new Quote();
        model.addAttribute("quote", quote);

        List<Book> booksAll = bookService.getAllBooks();
        model.addAttribute("booksAll", booksAll );

        return "add-quote-form";
    }

    @GetMapping("/update-quote-form/{id}")
    public String updateQuoteForm(@PathVariable(value = "id") long id, Model model) {
        Quote quote = quoteService.getQuoteById(id);
        model.addAttribute("quote", quote);

        List<Book> booksAll = bookService.getAllBooks();
        model.addAttribute("booksAll", booksAll );

        return "update-quote-form";
    }

    @PostMapping("/save")
    public String saveQuote(@Valid @ModelAttribute("quote") Quote quote, BindingResult bindingResult) {

        quoteService.saveQuote(quote);
        if(bindingResult.hasErrors()){
            return "redirect:/add-quote-form";
        }

        return "redirect:/quotes";
    }

    @GetMapping("/deleteQuote/{id}")
    public String deleteQuote(@PathVariable(value = "id") long id) {

        quoteService.deleteQuote(id);
        return "redirect:/quotes";

    }
}
