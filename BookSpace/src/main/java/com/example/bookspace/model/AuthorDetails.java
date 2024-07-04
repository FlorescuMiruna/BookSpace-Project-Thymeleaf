package com.example.bookspace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;

//    @OneToOne
//    Author author;


    @Override
    public String toString() {
        return "AuthorDetails{" +
                "id=" + id +
//                ", description='" + description + '\'' +
                '}';
    }
}
