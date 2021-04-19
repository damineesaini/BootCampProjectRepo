package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.CartDto;
import com.bootcamp.BootcampProject.dto.response.CartResponseDto;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.exception.InactiveException;
import com.bootcamp.BootcampProject.exception.UnauthorizedAccessException;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.CartService;
import com.bootcamp.BootcampProject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer/add")
    public String addProductVarToCart(@RequestBody CartDto cartDto, @RequestParam("id") String id) throws UserNotFoundException, DoesNotExistException, InactiveException {
        Customer customer = customerService.getLoggedInCustomer();
        return  cartService.addToCart(cartDto,customer, UUID.fromString(id));
    }

//    @GetMapping("/customer/viewCart")
//    public MappingJacksonValue viewCart() throws UserNotFoundException, DoesNotExistException {
//        Customer customer = customerService.getLoggedInCustomer();
//        return cartService.viewCartItems(customer.getId());
//    }
    @GetMapping("/customer/viewCart")
    public List<CartResponseDto> viewCart() throws UserNotFoundException, DoesNotExistException {
        Customer customer = customerService.getLoggedInCustomer();
        return cartService.viewCartItems(customer.getId());
    }

    @DeleteMapping("/customer/removeProduct/{variationId}")
    public String removeProduct(@PathVariable String variationId) throws UserNotFoundException, DoesNotExistException, UnauthorizedAccessException {
        Customer customer = customerService.getLoggedInCustomer();
        return cartService.removeProductByIdFromCart(UUID.fromString(variationId),customer);
    }

    @PutMapping("/customer/updateItem/{variationId}")
    public String updateItem(@Valid @RequestBody CartDto cartDto, @PathVariable String variationId) throws UserNotFoundException, DoesNotExistException, UnauthorizedAccessException {
        Customer customer = customerService.getLoggedInCustomer();
        return cartService.updateExistingItem(cartDto,customer.getId(),UUID.fromString(variationId));
    }

    @DeleteMapping("/customer/deleteAll")
    public String emptyCart() throws UserNotFoundException, DoesNotExistException {
        Customer customer = customerService.getLoggedInCustomer();
        return cartService.emptyTheCart(customer);
    }
}
