package com.example.bookspace.service;


import com.example.bookspace.model.Book;
import com.example.bookspace.model.Label;
import com.example.bookspace.repository.BookRepository;
import com.example.bookspace.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    private final LabelRepository labelRepository;

    @Autowired
    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    public void saveLabel(Label label) {
        labelRepository.save(label);
    }

    public void deleteLabel(long id) {
        labelRepository.deleteById(id);
    }
}
