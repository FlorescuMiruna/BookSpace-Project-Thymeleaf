package com.example.bookspace.service;

import com.example.bookspace.model.Book;
import com.example.bookspace.model.Quote;
import com.example.bookspace.repository.BookRepository;
import com.example.bookspace.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;
//    private final BookService bookService;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    public Quote getQuoteById(Long id) {
        Optional<Quote> optional = quoteRepository.findById(id);
        Quote quote = null;
        if (optional.isPresent())
            quote = optional.get();
        else
            throw new RuntimeException(
                    "Quote not found for id : " + id);
        return quote;
    }

    public void saveQuote(Quote quote) {
        quoteRepository.save(quote);
    }

    public void deleteQuote(long id) {
        quoteRepository.deleteById(id);
    }
}
