package com.example.bookspace.service;

import com.example.bookspace.exception.ResourceNotFoundException;
import com.example.bookspace.model.Author;
import com.example.bookspace.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService
{
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        Optional<Author> optional = authorRepository.findById(id);
        return optional.orElseThrow(() -> new ResourceNotFoundException("Author not found for id: " + id));
    }

    public Author saveAuthor(Author author) {
        authorRepository.save(author);
        return author;
    }

     public void deleteAuthor(long id) {
         System.out.println("deleteAuthor service");
        authorRepository.deleteById(id);
    }

}