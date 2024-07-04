package com.example.bookspace.service;

import com.example.bookspace.model.AuthorDetails;
import com.example.bookspace.repository.AuthorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorDetailsService {

    @Autowired
    private AuthorDetailsRepository authorDetailsRepository;

    public AuthorDetails saveAuthorDetails(AuthorDetails authorDetails) {
        return authorDetailsRepository.save(authorDetails);
    }

    public AuthorDetails getAuthorDetailsById(Long id) {
        return authorDetailsRepository.findById(id).orElse(null);
    }
}
