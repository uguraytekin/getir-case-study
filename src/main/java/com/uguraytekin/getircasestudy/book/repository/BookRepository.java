package com.uguraytekin.getircasestudy.book.repository;

import com.uguraytekin.getircasestudy.book.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByIdAndStockGreaterThanEqual(String id, Integer stock);
}

