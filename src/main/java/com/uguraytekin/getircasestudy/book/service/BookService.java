package com.uguraytekin.getircasestudy.book.service;


import com.uguraytekin.getircasestudy.book.models.Book;
import com.uguraytekin.getircasestudy.book.payload.BookUpdateStockDto;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
public interface BookService {
    Book save(Book book);

    Book stockUpdate(BookUpdateStockDto bookUpdateStockDto);

    Book findById(String bookId);
}
