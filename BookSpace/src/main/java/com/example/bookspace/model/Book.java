package com.example.bookspace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Genre required")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Only letters are allowed")
    private String genre;

    @Digits(integer = 4, fraction = 0, message = "Please enter a valid year")
    private Integer year;

//    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Details details;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "book_label",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<Label> labels = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Quote> quotes;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
//                ", details=" + details +
                ", author=" + author +
                ", quotes=" + quotes +
                '}';
    }
}
