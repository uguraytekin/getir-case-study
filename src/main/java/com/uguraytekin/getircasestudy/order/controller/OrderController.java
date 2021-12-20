package com.uguraytekin.getircasestudy.order.controller;

import com.uguraytekin.getircasestudy.common.services.CustomerDetailsImpl;
import com.uguraytekin.getircasestudy.order.payload.OrderDto;
import com.uguraytekin.getircasestudy.order.payload.request.CreateOrderRequest;
import com.uguraytekin.getircasestudy.order.payload.request.GetOrderFilterRequest;
import com.uguraytekin.getircasestudy.order.payload.response.OrderResponse;
import com.uguraytekin.getircasestudy.order.service.OrderFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderFacade orderFacade;
    private final ModelMapper mapper;

    @Operation(summary = "Create an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created an Order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class)))
    })
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        CustomerDetailsImpl customer = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderDto createdOrder = orderFacade.createOrder(customer.getId(), createOrderRequest.getOrderDetails());
        return ResponseEntity.ok().body(mapper.map(createdOrder, OrderResponse.class));
    }

    @Operation(summary = "Gets an order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order by Requested Id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class)))
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") String orderId) {
        CustomerDetailsImpl customer = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderDto requestedOrder = orderFacade.getOrderById(customer.getId(), orderId);
        return ResponseEntity.ok(mapper.map(requestedOrder, OrderResponse.class));
    }

    @Operation(summary = "Gets orders between given dates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders between given dates",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)))
    })
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getOrders(@Valid GetOrderFilterRequest getOrderFilterRequest, Pageable pageable) {
        CustomerDetailsImpl customer = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<OrderDto> requestedOrders = orderFacade.getOrdersByDateInterval(customer.getId(), getOrderFilterRequest.getStartDate(), getOrderFilterRequest.getEndDate(), pageable);
        return ResponseEntity.ok(requestedOrders.map(x -> mapper.map(x, OrderResponse.class)));
    }
}
