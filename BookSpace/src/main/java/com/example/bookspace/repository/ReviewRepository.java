package com.example.bookspace.repository;

import com.example.bookspace.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByBookId(Long bookId);
}
