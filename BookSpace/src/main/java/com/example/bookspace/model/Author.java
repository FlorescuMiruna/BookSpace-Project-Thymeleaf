package com.example.bookspace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Only letters are allowed")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Only letters are allowed")
    private String lastName;

//    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
//    private AuthorDetails authorDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private AuthorDetails authorDetails;

    @Digits(integer = 4, fraction = 0, message = "Please enter a valid birth year")
    private Integer birthYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + birthYear +
//                ", books=" + books +
                '}';
    }

}
