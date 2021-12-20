package com.uguraytekin.getircasestudy.book.service;

import com.uguraytekin.getircasestudy.book.models.Book;
import com.uguraytekin.getircasestudy.book.payload.BookCreateDto;
import com.uguraytekin.getircasestudy.book.payload.BookDto;
import com.uguraytekin.getircasestudy.book.payload.BookUpdateStockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class BookServiceFacade {

    private final BookService bookService;
    private final ModelMapper mapper;

    public BookDto createBook(BookCreateDto bookCreateDto) {
        log.info("Creating book started : {}", bookCreateDto.toString());
        Book createdBook = bookService.save(mapper.map(bookCreateDto, Book.class));
        return mapper.map(createdBook, BookDto.class);
    }

    public BookDto stockUpdate(BookUpdateStockDto bookUpdateStockDto) {
        log.info("Updating stocks of a book started wtih book Id : {}, new Stocks : {}", bookUpdateStockDto.getBookId(), bookUpdateStockDto.getNewStock());
        Book book = bookService.stockUpdate(bookUpdateStockDto);
        return mapper.map(book, BookDto.class);
    }

    public Book getById(String bookId) {
        log.info("Get book started with id : {}", bookId);
        return bookService.findById(bookId);
    }

}
