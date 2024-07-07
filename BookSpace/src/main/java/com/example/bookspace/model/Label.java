package com.example.bookspace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String colour;

    @ManyToMany(mappedBy = "labels")
    private Set<Book> books = new HashSet<>();

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", colour='" + colour + '\'' +
                ", books=" + books +
                '}';
    }
}
