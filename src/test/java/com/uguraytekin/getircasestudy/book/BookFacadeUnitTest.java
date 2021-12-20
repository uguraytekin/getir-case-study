package com.uguraytekin.getircasestudy.book;

import com.uguraytekin.getircasestudy.book.models.Book;
import com.uguraytekin.getircasestudy.book.payload.BookCreateDto;
import com.uguraytekin.getircasestudy.book.payload.BookDto;
import com.uguraytekin.getircasestudy.book.payload.BookUpdateStockDto;
import com.uguraytekin.getircasestudy.book.service.BookService;
import com.uguraytekin.getircasestudy.book.service.BookServiceFacade;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @Author: Ugur Aytekin
 * @create: 20.12.2021
 */

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookFacadeUnitTest {

    @InjectMocks
    private BookServiceFacade bookFacade;
    @Mock
    private BookService bookService;
    @Mock
    private ModelMapper mapper;

    private Book testBook() {
        return Book.builder()
                .id("book-id")
                .title("test")
                .author("test")
                .stock(10)
                .price(BigDecimal.valueOf(9.99)).build();
    }
    private BookDto testBookDto() {
        return BookDto.builder()
                .id("book-id")
                .title("test")
                .author("test")
                .stock(20)
                .price(BigDecimal.valueOf(9.99)).build();
    }

    @Test
    @Order(1)
    void saveBook_OK() {

        Book book = testBook();
        BookCreateDto bookCreateDto = BookCreateDto.builder()
                .title("test")
                .author("test")
                .stock(10)
                .price(BigDecimal.valueOf(9.99))
                .build();
        when(bookService.save(book)).thenReturn(book);
        when(mapper.map(bookCreateDto, Book.class)).thenReturn(book);
        when(mapper.map(book, BookDto.class)).thenReturn(testBookDto());

        BookDto bookDto = bookFacade.createBook(bookCreateDto);
        assertEquals(book.getPrice(), bookDto.getPrice());
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(book.getAuthor(), bookDto.getAuthor());
    }

    @Test
    @Order(2)
    void findById_OK() {
        Book book = testBook();
        when(bookService.findById(book.getId())).thenReturn(book);
        Book findBook = bookFacade.getById(book.getId());
        assertEquals(book.getId(), findBook.getId());
    }

    @Test
    @Order(3)
    void stockUpdate_OK() {
        Book book = testBook();
        BookUpdateStockDto dto = BookUpdateStockDto.builder()
                .bookId(book.getId())
                .newStock(20)
                .build();
        when(bookService.stockUpdate(dto)).thenReturn(book);
        when(mapper.map(book, BookDto.class)).thenReturn(testBookDto());

        BookDto bookDto = bookFacade.stockUpdate(dto);

        assertEquals(book.getId(), bookDto.getId());
        assertEquals(dto.getNewStock(), bookDto.getStock());
    }
}
