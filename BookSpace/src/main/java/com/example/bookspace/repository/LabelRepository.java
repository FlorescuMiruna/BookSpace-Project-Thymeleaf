package com.example.bookspace.repository;

import com.example.bookspace.model.Book;
import com.example.bookspace.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label,Long> {
}
