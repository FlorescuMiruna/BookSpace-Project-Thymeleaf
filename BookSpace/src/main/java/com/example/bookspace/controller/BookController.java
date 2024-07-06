package com.example.bookspace.controller;


import com.example.bookspace.model.Author;
import com.example.bookspace.model.Book;

import com.example.bookspace.model.Review;
import com.example.bookspace.service.AuthorService;
import com.example.bookspace.service.BookService;
import com.example.bookspace.service.ReviewService;
import jakarta.validation.Valid;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final ReviewService reviewService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, ReviewService reviewService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.reviewService = reviewService;
    }

    @GetMapping("")
    public String viewHomePage(Model model) {
        model.addAttribute("booksList", bookService.getAllBooks());
        return "books";
    }

//    @GetMapping("/page/{id}")
//    public String viewBookPage(@PathVariable(value = "id") long id, Model model) {
//        Book book = bookService.getBookById(id);
//
//        model.addAttribute("book", book);
//        List<Author> authorsAll = authorService.getAllAuthors();
//        model.addAttribute("authorsAll", authorsAll );
//
//        List<Review> reviewsAll = reviewService.getAllReviewsByBookId(id);
//        model.addAttribute("reviewsAll", reviewsAll);
//        return "book-page";
//    }

    @GetMapping("/add-book-form")
    public String addBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);

        List<Author> authorsAll = authorService.getAllAuthors();
        model.addAttribute("authorsAll", authorsAll );


        return "add-book-form";
    }

    @GetMapping("/update-book-form/{id}")
    public String updateBookForm(@PathVariable(value = "id") long id, Model model) {
        Book book = bookService.getBookById(id);

        model.addAttribute("book", book);
        List<Author> authorsAll = authorService.getAllAuthors();
        model.addAttribute("authorsAll", authorsAll );

        return "update-book-form";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        bookService.saveBook(book);

        if(bindingResult.hasErrors()){
            return "redirect:/add-book-form";
        }

        return "redirect:/books";
    }

    @PostMapping("/page/saveReview")
    public String saveReview(@Valid @ModelAttribute("review") Review review, BindingResult bindingResult) {

        reviewService.saveReview(review);


        return "redirect:/book/page";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable(value = "id") long id) {
        System.out.println("deleteBook");
        bookService.deleteBook(id);
        return "redirect:/books";

    }

    @GetMapping("/page/{id}")
    public String getBookPage(@PathVariable(value = "id") long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book-info.html";
    }

    @PutMapping("/page/{id}")
    public String addLabelOnBook(@PathVariable(value = "id") long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book-info.html";
    }

//    @PostMapping("/watch-list/user/{userId}")
//    public Movie addMovieToWatchList(@RequestBody Movie movie,@PathVariable Long userId){
//
//        movieService.addMovieToWatchList(movie,userId);
//        return movie;
//    }

}
