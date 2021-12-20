package com.uguraytekin.getircasestudy.book.service;

import com.uguraytekin.getircasestudy.book.models.Book;
import com.uguraytekin.getircasestudy.book.payload.BookUpdateStockDto;
import com.uguraytekin.getircasestudy.book.repository.BookRepository;
import com.uguraytekin.getircasestudy.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        log.info("Trying to save book: {}", book.toString());
        bookRepository.save(book);
        log.info("book saved. {}", book.toString());
        return book;
    }

    @Override
    @Transactional
    public Book stockUpdate(BookUpdateStockDto bookUpdateStockDto) {
        String bookId = bookUpdateStockDto.getBookId();
        log.info("Trying to update stocks of book with id: {}, newStocks : {}", bookId, bookUpdateStockDto.getNewStock());

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("This book does not exists!"));
        log.info("Book found : {}", book.toString());
        book.setStock(bookUpdateStockDto.getNewStock());

        bookRepository.save(book);
        log.info("Book saved with new stocks : {}", book.toString());
        return book;
    }

    @Override
    public Book findById(String bookId) {
        log.info("Trying to find book with id: {}", bookId);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book doesnt exists!"));
        log.info("book found : {}", book.toString());
        return book;
    }
}
