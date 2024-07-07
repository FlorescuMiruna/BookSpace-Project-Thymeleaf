package com.example.bookspace.service;

import com.example.bookspace.model.Author;
import com.example.bookspace.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
        Author author = null;
        if (optional.isPresent())
            author = optional.get();
        else
            throw new RuntimeException(
                    "Author not found for id : " + id);
        return author;
    }

    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

     public void deleteAuthor(long id) {
         System.out.println("deleteAuthor service");
        authorRepository.deleteById(id);
    }

}