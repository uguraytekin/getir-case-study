package com.uguraytekin.getircasestudy.customer.controller;

import com.uguraytekin.getircasestudy.common.security.jwt.JwtUtils;
import com.uguraytekin.getircasestudy.common.services.CustomerDetailsImpl;
import com.uguraytekin.getircasestudy.customer.payload.dto.CreateCustomerDto;
import com.uguraytekin.getircasestudy.customer.payload.request.LoginRequest;
import com.uguraytekin.getircasestudy.customer.payload.request.SignupRequest;
import com.uguraytekin.getircasestudy.customer.payload.response.JwtResponse;
import com.uguraytekin.getircasestudy.customer.payload.response.MessageResponse;
import com.uguraytekin.getircasestudy.customer.service.CustomerService;
import com.uguraytekin.getircasestudy.order.payload.OrderDto;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Ugur Aytekin
 * @create: 18.12.2021
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/customer")
public class CustomerController {
    private final AuthenticationManager authenticationManager;

    private final CustomerService customerService;

    private final ModelMapper mapper;

    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateCustomer(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) authentication.getPrincipal();
        List<String> roles = customerDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new JwtResponse(jwt,
                customerDetails.getId(),
                customerDetails.getUsername(),
                customerDetails.getEmail(),
                roles));
    }

    @Operation(summary = "Create a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created a Customer",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerCustomer(@Valid @RequestBody SignupRequest signUpRequest) {

        customerService.save(mapper.map(signUpRequest, CreateCustomerDto.class));

        return ResponseEntity.ok(new MessageResponse("Customer registered successfully!"));
    }

    @Operation(summary = "Gets orders of customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders of requested customer",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDto>> getOrdersOfCustomer(Pageable pageable) {

        CustomerDetailsImpl customer = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<OrderDto> pageResult = customerService.getCustomerOrders(customer.getId(), pageable);
        return ResponseEntity.ok(pageResult);
    }
}
