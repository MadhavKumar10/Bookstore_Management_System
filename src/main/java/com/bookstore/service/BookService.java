package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


//import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> getAllBooks() {
        return bookRepository.findAll(); // or any other logic to fetch books
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Transactional
    public Book addBook(Book book) {
        if (book.getId() != null) {
            throw new IllegalArgumentException("New book should not have an ID assigned.");
        }
        
        return bookRepository.save(book);
    }

    @Transactional
    public List<Book> saveAll(List<Book> books) {
        books.forEach(book -> System.out.println("Book ID: " + book.getId()));
        return books.stream()
            .map(book -> {
                if (book.getId() != null) {
                    Book existingBook = bookRepository.findById(book.getId()).orElse(null);
                    if (existingBook != null) {
                        existingBook.setTitle(book.getTitle());
                        existingBook.setAuthor(book.getAuthor());
                        existingBook.setPrice(book.getPrice());
                        return bookRepository.save(existingBook);
                    }
                }
                return bookRepository.save(book);
            })
            .collect(Collectors.toList());
    }


    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setGenre(updatedBook.getGenre());
        book.setPrice(updatedBook.getPrice());
        book.setDescription(updatedBook.getDescription());
        book.setStockQuantity(updatedBook.getStockQuantity());
        BeanUtils.copyProperties(updatedBook, book, "id");
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}
 