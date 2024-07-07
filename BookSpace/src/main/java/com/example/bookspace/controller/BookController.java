package com.example.bookspace.controller;


import com.example.bookspace.model.*;

import com.example.bookspace.service.AuthorService;
import com.example.bookspace.service.BookService;
import com.example.bookspace.service.LabelService;
import com.example.bookspace.service.ReviewService;
import jakarta.validation.Valid;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BookController {

    private static final Logger log = Logger.getLogger(LabelController.class.getName());

    private final BookService bookService;
    private final AuthorService authorService;
    private final ReviewService reviewService;
    private final LabelService labelService;


    @Autowired
    public BookController(BookService bookService, AuthorService authorService, ReviewService reviewService, LabelService labelService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.reviewService = reviewService;
        this.labelService = labelService;
    }

    @GetMapping("")
    public String viewAllBooksPage(Model model) {
        model.addAttribute("booksList", bookService.getAllBooks());
        log.info("Successfully loaded Books page");
        return "books";
    }

    @GetMapping("/page/{id}")
    public String getBookInfoPage(@PathVariable(value = "id") long id, Model model) {
        Book book = bookService.getBookById(id);
        List<Label> allLabels = labelService.getAllLabels();

        model.addAttribute("book", book);
        model.addAttribute("labels", allLabels);

        LabelBookDTO labelBookDTO = new LabelBookDTO();
        labelBookDTO.setBookId(book.getId());
        model.addAttribute("labelBookDTO", labelBookDTO);

        log.info("Successfully loaded the Book Info Page");

        return "book-info.html";
    }

    @GetMapping("/add-book-form")
    public String addBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);

        List<Author> authorsAll = authorService.getAllAuthors();
        model.addAttribute("authorsAll", authorsAll );

        log.info("Successfully loaded the Add Book Form");

        return "add-book-form";
    }

    @GetMapping("/update-book-form/{id}")
    public String updateBookForm(@PathVariable(value = "id") long id, Model model) {
        Book book = bookService.getBookById(id);

        model.addAttribute("book", book);
        List<Author> authorsAll = authorService.getAllAuthors();
        model.addAttribute("authorsAll", authorsAll );

        log.info("Successfully loaded the Update Book Form");

        return "update-book-form";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        bookService.saveBook(book);

        if(bindingResult.hasErrors()){
            return "redirect:/add-book-form";
        }
        log.info("Successfully saved book with ID: " + book.getId());

        return "redirect:/books";
    }

//    @PostMapping("/page/saveReview")
//    public String saveReview(@Valid @ModelAttribute("review") Review review, BindingResult bindingResult) {
//        reviewService.saveReview(review);
//
//        return "redirect:/book/page";
//    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable(value = "id") long id) {

        bookService.deleteBook(id);

        log.info("Book with ID: " + id + " deleted successfully.");

        return "redirect:/books";

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

//    @PostMapping("/page/saveLabel")
//    public String saveLabel(@RequestParam("labelId") Long labelId, @RequestParam("bookId") Long bookId) {
//
//        Book book = bookService.getBookById(bookId);
//        Label label = labelService.getLabelById(labelId);
//
//        System.out.println("Label ID: " + labelId + "Label: " + label);
//        System.out.println("Book ID: " + bookId + "Book: " + book);
//
//        if (book != null && label != null) {
//            book.getLabels().add(label);
//            bookService.saveBook(book);
//        }
//
//        return "redirect:/books/page/" + bookId;
//    }

    @PostMapping("/page/saveLabel")
    public String saveLabel(@Valid @ModelAttribute("labelBookDTO") LabelBookDTO labelBookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/books/page/" + labelBookDTO.getBookId();
        }

        Long labelId = labelBookDTO.getLabelId();
        Long bookId = labelBookDTO.getBookId();

        Book book = bookService.getBookById(bookId);
        Label label = labelService.getLabelById(labelId);

        if (book != null && label != null) {
            book.getLabels().add(label);
            bookService.saveBook(book);
            log.info("Label added to book and saved successfully");
        }

        return "redirect:/books/page/" + bookId;
    }

}
