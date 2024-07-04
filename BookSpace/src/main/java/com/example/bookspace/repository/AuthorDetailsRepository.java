package com.example.bookspace.repository;

import com.example.bookspace.model.Author;
import com.example.bookspace.model.AuthorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDetailsRepository extends JpaRepository<AuthorDetails,Long> {
}
