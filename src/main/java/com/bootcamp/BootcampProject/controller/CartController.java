package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.CartDto;
import com.bootcamp.BootcampProject.entity.order.Cart;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.CartService;
import com.bootcamp.BootcampProject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String addProductVarToCart(@RequestBody CartDto cartDto, @RequestParam("id") String id) throws UserNotFoundException {
        Customer customer = customerService.getLoggedInCustomer();
        cartService.addToCart(cartDto,customer, UUID.fromString(id));
        return "hi";
    }

    @GetMapping("/customer/viewCart")
    public List<Cart> viewCart() throws UserNotFoundException {
        Customer customer = customerService.getLoggedInCustomer();
        List<Cart> cart = cartService.viewCartItems(customer.getId());
        return cart;
    }

    @DeleteMapping("/customer/removeProduct/{variationId}")
    public String removeProduct(@PathVariable String variationId) throws UserNotFoundException {
        Customer customer = customerService.getLoggedInCustomer();
        return cartService.removeProductByIdFromCart(UUID.fromString(variationId),customer);
    }

    @PutMapping("/customer/updateItem/{variationId}")
    public String updateItem(@RequestBody CartDto cartDto,@PathVariable String variationId) throws UserNotFoundException {
        Customer customer = customerService.getLoggedInCustomer();
        return cartService.updateExistingItem(cartDto,customer.getId(),UUID.fromString(variationId));
    }
}
