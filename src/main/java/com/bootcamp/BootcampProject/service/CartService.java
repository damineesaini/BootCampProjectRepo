package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.CartDto;
import com.bootcamp.BootcampProject.entity.order.Cart;
import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.repository.CartRepository;
import com.bootcamp.BootcampProject.repository.CustomerRepository;
import com.bootcamp.BootcampProject.repository.ProductRepository;
import com.bootcamp.BootcampProject.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Modifying
    public String addToCart(CartDto cartDto, Customer customer, UUID productVarId) {
        Cart cart = new Cart();
        System.out.println("inside add to cart");
            cart.setCustomerUserId(customer);
            System.out.println("inside is customer present");
            Optional<ProductVariation> productVariation = productVariationRepository.findById(productVarId);
            if (productVariation.isPresent()) {
                System.out.println("inside is variation present");
                ProductVariation productVariation1 = productVariation.get();
                if (productVariation1.isActive()) {
                    System.out.println("inside is variation active");
                    int cart_quantity = cartDto.getQuantity();
                    if (cart_quantity < productVariation1.getQuantityAvailable()) {
                        System.out.println("inside is cart quantity less");
                        UUID productId = productVariation1.getProductId().getId();
                        Optional<Product> product = productRepository.findById(productId);
                        if (product.isPresent()) {
                            System.out.println("inside is product present");
                            Product product1 = product.get();
                            if (!product1.isDelete() && product1.isActive()) {
                                System.out.println("inside is product active and not deleted");
                                cart.setProductVariationId(productVariation1);
                                cart.setQuantity(cart_quantity);
                                cartRepository.save(cart);
                            }
                            else {
                                return "inside product active and delete";
                            }
                        }
                        else {
                            return "inside product is present";
                        }
                    }
                    else {
                        return "inside cart quantity";
                    }

                }
                else {
                    return "inside product var present";
                }
            }
        return "hi";
    }

    public List<Cart> viewCartItems(UUID customer_id){
        System.out.println("inside add to cart");
        Optional<Customer> customer = customerRepository.findById(customer_id);
        System.out.println("inside add to cart" + customer.get().getUserId().getEmail());
            Customer customer1 = customer.get();
            return cartRepository.findAllByCustomerId(customer1.getId());
    }

//    public Optional<Cart> viewCartItems(UUID id){
//        System.out.println("inside add to cart");
//        return cartRepository.findById(id);
//    }

    @Transactional
    public String removeProductByIdFromCart(UUID id,Customer customer) {
        if(productVariationRepository.findById(id).isPresent()){
            Optional<Cart> cartItem = cartRepository.findByCustomerIdAndProductVariationId(customer.getId(),id);
            if (cartItem.isPresent()){
                System.out.println(cartItem.get().getId());
                cartRepository.deleteByCustomerIdAndProductVariationId(customer.getId(),id);
                return "deleted success";
            }
            else {
                return "there is no such item in your cart";
            }
        }
        else {
            return  "there is no such product variation";
        }
    }

    @Transactional
    @Modifying
    public String updateExistingItem(CartDto cartDto, UUID customer_id, UUID productVarId){
        if(productVariationRepository.findById(productVarId).isPresent()) {
            Optional<Cart> cartItem = cartRepository.findByCustomerIdAndProductVariationId(customer_id, productVarId);
            if (cartItem.isPresent()) {
                System.out.println(cartItem.get().getId());
                Cart cart = cartItem.get();
                System.out.println(cartDto.getQuantity());
                int quant = cartDto.getQuantity();
                cart.setQuantity(quant);
                cartRepository.save(cart);
                return "updated success";
            }
            else {
                return "there is no such item in your cart";
            }
        }
        else {
            return  "there is no such product variation";
        }
    }
}
