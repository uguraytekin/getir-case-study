package com.uguraytekin.getircasestudy.book.controller;

import com.uguraytekin.getircasestudy.book.payload.BookCreateDto;
import com.uguraytekin.getircasestudy.book.payload.BookDto;
import com.uguraytekin.getircasestudy.book.payload.BookUpdateStockDto;
import com.uguraytekin.getircasestudy.book.payload.request.BookCreateRequest;
import com.uguraytekin.getircasestudy.book.payload.request.BookUpdateStockRequest;
import com.uguraytekin.getircasestudy.book.payload.response.BookResponse;
import com.uguraytekin.getircasestudy.book.service.BookServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {


    private final BookServiceFacade bookServiceFacade;

    private final ModelMapper mapper;

    @Operation(summary = "Create a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created a Book",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)))
    })
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookCreateRequest bookCreateRequest) {
        BookDto createdBook = bookServiceFacade.createBook(mapper.map(bookCreateRequest, BookCreateDto.class));
        return ResponseEntity.ok().body(mapper.map(createdBook, BookResponse.class));
    }

    @Operation(summary = "Updates stock of a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock updated of a Book",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)))
    })
    @PutMapping("/updateStock")
    public ResponseEntity<BookResponse> stockUpdate(@RequestBody @Valid BookUpdateStockRequest bookUpdateStockRequest) {
        BookDto stockUpdatedBook = bookServiceFacade.stockUpdate(mapper.map(bookUpdateStockRequest, BookUpdateStockDto.class));
        return ResponseEntity.ok().body(mapper.map(stockUpdatedBook, BookResponse.class));
    }
}
