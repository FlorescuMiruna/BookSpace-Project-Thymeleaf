package com.example.bookspace.service;




import com.example.bookspace.model.Author;
import com.example.bookspace.model.Book;
import com.example.bookspace.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        Optional<Book> optional = bookRepository.findById(id);
        Book book = null;
        if (optional.isPresent())
            book = optional.get();
        else
            throw new RuntimeException(
                    "Book not found for id : " + id);
        return book;
    }

    public void saveBook(Book book) {
//        System.out.println("SaveBook in BookService book" +  book);
        bookRepository.save(book);
    }

    public void deleteBook(long id) {
        System.out.println("deleteBook service");
        bookRepository.deleteById(id);
    }
}
