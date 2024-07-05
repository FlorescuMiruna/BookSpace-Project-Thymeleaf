package com.example.bookspace.service;


import com.example.bookspace.model.Author;
import com.example.bookspace.model.Book;
import com.example.bookspace.model.Label;
import com.example.bookspace.repository.BookRepository;
import com.example.bookspace.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Label getLabelById(Long id) {
        Optional<Label> optional = labelRepository.findById(id);
        Label label = null;
        if (optional.isPresent())
            label = optional.get();
        else
            throw new RuntimeException(
                    "Label not found for id : " + id);
        return label;
    }
}
