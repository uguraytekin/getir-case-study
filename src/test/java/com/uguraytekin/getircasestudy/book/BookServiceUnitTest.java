package com.uguraytekin.getircasestudy.book;

import com.uguraytekin.getircasestudy.book.models.Book;
import com.uguraytekin.getircasestudy.book.payload.BookUpdateStockDto;
import com.uguraytekin.getircasestudy.book.repository.BookRepository;
import com.uguraytekin.getircasestudy.book.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @Author: Ugur Aytekin
 * @create: 20.12.2021
 */

@ExtendWith(MockitoExtension.class)
class BookServiceUnitTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;


    private Book testBook() {
        return Book.builder()
                .id("test-id")
                .title("test")
                .author("test")
                .stock(10).build();
    }

    @Test
    void saveBook_OK() {
        Book book = testBook();
        when(bookRepository.save(book)).thenReturn(book);
        Book savedBook = bookService.save(book);

        assertEquals(savedBook, book);
        assertEquals(savedBook.getTitle(), book.getTitle());
        assertEquals(savedBook.getAuthor(), book.getAuthor());
        assertEquals(savedBook.getStock(), book.getStock());
    }

    @Test
    void findById_OK() {
        Book book = testBook();
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Book findedBook = bookService.findById(book.getId());

        assertEquals(findedBook, book);
    }

    @Test
    void stockUpdate_OK() {
        Book book = testBook();
        book.setStock(20);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));


        BookUpdateStockDto dto = new BookUpdateStockDto(book.getId(), 20);
        book.setStock(dto.getNewStock());
        Book savedBook = bookService.stockUpdate(dto);

        assertEquals(savedBook, book);
    }


}
