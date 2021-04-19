package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.dto.request.OrderDto;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.exception.UserNotFoundException;
import com.bootcamp.BootcampProject.service.CustomerService;
import com.bootcamp.BootcampProject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @PostMapping("/customer/orderAllItems")
    public String orderAllItems(@RequestBody OrderDto orderDto) throws UserNotFoundException, DoesNotExistException {
        Customer customer = customerService.getLoggedInCustomer();
        return orderService.orderCart(customer,orderDto);
    }
}
