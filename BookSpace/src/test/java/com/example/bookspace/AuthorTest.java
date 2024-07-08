package com.example.bookspace;

import com.example.bookspace.exception.ResourceNotFoundException;
import com.example.bookspace.model.Author;
import com.example.bookspace.repository.AuthorRepository;
import com.example.bookspace.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Get all authors from db")
    void test_getAllAuthors() {
        // Arrange
        List<Author> authors = Arrays.asList(buildAuthor(), buildAuthor());
        when(authorRepository.findAll()).thenReturn(authors);

        // Act
        List<Author> actualAuthors = authorService.getAllAuthors();

        // Assert
        assertEquals(authors.size(), actualAuthors.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Get author from db")
    void test_getAuthor() {
        // Arrange
        Long id = 1L;
        Author author = buildAuthor();
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        // Act
        Author actualAuthor = authorService.getAuthorById(id);

        // Assert
        assertEquals(id, actualAuthor.getId());
        verify(authorRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Save author")
    void test_saveAuthor() {
        // Arrange
        Author author = buildAuthor();
        when(authorRepository.save(author)).thenReturn(author);

        // Act
        Author savedAuthor = authorService.saveAuthor(author);

        // Assert
        verify(authorRepository).save(author);
        assertEquals(author, savedAuthor);
    }

    @Test
    @DisplayName("Delete author by id")
    void test_deleteAuthor() {
        // Arrange
        Long id = 1L;
        doNothing().when(authorRepository).deleteById(id);

        // Act
        authorService.deleteAuthor(id);

        // Assert
        verify(authorRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Get author by id throws ResourceNotFoundException")
    void test_getAuthor_throwsException() {
        // Arrange
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            authorService.getAuthorById(id);
        });
        assertEquals("Author not found for id: " + id, exception.getMessage());
        verify(authorRepository, times(1)).findById(id);
    }

    private Author buildAuthor() {
        Author author = new Author();
        author.setId(1L);
        author.setFirstName("First Name");
        author.setLastName("Last Name");
        author.setBirthYear(1980);
        return author;
    }
}
