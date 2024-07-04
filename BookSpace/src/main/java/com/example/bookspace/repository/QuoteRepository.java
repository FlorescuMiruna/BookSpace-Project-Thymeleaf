package com.example.bookspace.repository;

import com.example.bookspace.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote,Long> {
}
