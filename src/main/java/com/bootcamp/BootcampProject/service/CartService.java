package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.CartDto;
import com.bootcamp.BootcampProject.dto.response.CartResponseDto;
import com.bootcamp.BootcampProject.entity.order.Cart;
import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.exception.InactiveException;
import com.bootcamp.BootcampProject.exception.UnauthorizedAccessException;
import com.bootcamp.BootcampProject.repository.CartRepository;
import com.bootcamp.BootcampProject.repository.CustomerRepository;
import com.bootcamp.BootcampProject.repository.ProductRepository;
import com.bootcamp.BootcampProject.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public String addToCart(CartDto cartDto, Customer customer, UUID productVarId) throws DoesNotExistException, InactiveException {
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
                        System.out.println("inside is cart quantity less");
                        UUID productId = productVariation1.getProductId().getId();
                        Optional<Product> product = productRepository.findById(productId);
                        if (product.isPresent()) {
                            System.out.println("inside is product present");
                            Product product1 = product.get();
                            if (!product1.isDelete() && product1.isActive()) {
                                if (cart_quantity < productVariation1.getQuantityAvailable()) {
                                    System.out.println("inside is product active and not deleted");
                                    cart.setProductVariationId(productVariation1);
                                    cart.setQuantity(cart_quantity);
                                    cartRepository.save(cart);
                                    return "item added to cart successfully.";
                                }
                                else {
                                    throw new DoesNotExistException("The mentioned quantity is not available right now. THe available quantity is : "+productVariation1.getQuantityAvailable());
                                }
                            }
                            else {
                                throw new InactiveException("The product is either inactive or deleted!!!");
                            }
                        }
                        else {
                            throw new DoesNotExistException("The product you want does not exists!!!");
                        }

                }
                else {
                    throw new InactiveException("The mentioned product variation is not active yet!!!");
                }
            }
            else {
                throw new DoesNotExistException("The mentioned product variation does not exist!!!");
            }
    }
//
//    public MappingJacksonValue viewCartItems(UUID customer_id) throws DoesNotExistException {
//        Optional<Customer> customer = customerRepository.findById(customer_id);
//        Customer customer1 = customer.get();
//        List<Cart> cartList = cartRepository.findAllByCustomerId(customer_id);
//        if (cartList.isEmpty()) {
//            throw new DoesNotExistException("Your cart is empty!!!");
//        } else {
//            SimpleBeanPropertyFilter filterCart = SimpleBeanPropertyFilter.filterOutAllExcept("id","quantity","productVariationId");
//            SimpleBeanPropertyFilter filterCategory = SimpleBeanPropertyFilter.filterOutAllExcept("name");
//            SimpleBeanPropertyFilter filter4 = SimpleBeanPropertyFilter.filterOutAllExcept("name","description","brand","isCancellable","isReturnable","categoryId");
//            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","price","productId","productMetadata","productImage");
//            SimpleBeanPropertyFilter filter2 =SimpleBeanPropertyFilter.filterOutAllExcept("filename","path");
//            FilterProvider filters = new SimpleFilterProvider().addFilter("cartFilter",filterCart).addFilter("categoryFilter",filterCategory).addFilter("productFilter",filter4).addFilter("productVariationFilter",filter).addFilter("imageFilter",filter2);
//            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(cartRepository.findAllByCustomerId(customer1.getId()));
//            mappingJacksonValue.setFilters(filters);
//            return mappingJacksonValue;
//        }
//    }

    public List<CartResponseDto> viewCartItems(UUID customer_id) throws DoesNotExistException {
        Optional<Customer> customer = customerRepository.findById(customer_id);
        Customer customer1 = customer.get();
        List<Cart> cartList = cartRepository.findAllByCustomerId(customer_id);
        if (cartList.isEmpty()) {
            throw new DoesNotExistException("Your cart is empty!!!");
        } else {
            List<CartResponseDto> cartResponseDtos=new ArrayList<>();
            for (Cart cart:cartList) {
                CartResponseDto cartResponseDto = new CartResponseDto();
                cartResponseDto.setId(cart.getProductVariationId().getId());
                cartResponseDto.setProductName(cart.getProductVariationId().getProductId().getName());
                cartResponseDto.setBrand(cart.getProductVariationId().getProductId().getBrand());
                cartResponseDto.setDescription(cart.getProductVariationId().getProductId().getDescription());
                cartResponseDto.setPrice(cart.getProductVariationId().getPrice());
                cartResponseDto.setMetadata(cart.getProductVariationId().getProductMetadata());
                cartResponseDto.setQuantityOrdered(cart.getQuantity());
                cartResponseDto.setCancellable(cart.getProductVariationId().getProductId().isCancellable());
                cartResponseDto.setReturnable(cart.getProductVariationId().getProductId().isReturnable());
                cartResponseDtos.add(cartResponseDto);
            }
            return cartResponseDtos;
        }
    }

//    public Optional<Cart> viewCartItems(UUID id){
//        System.out.println("inside add to cart");
//        return cartRepository.findById(id);
//    }

    @Transactional
    public String removeProductByIdFromCart(UUID id,Customer customer) throws DoesNotExistException, UnauthorizedAccessException {
        if(productVariationRepository.findById(id).isPresent()){
            Optional<Cart> cartItem = cartRepository.findByCustomerIdAndProductVariationId(customer.getId(),id);
                if (cartItem.isPresent()){
                    if (cartItem.get().getCustomerUserId().equals(customer)){
                    System.out.println(cartItem.get().getId());
                    cartRepository.deleteByCustomerIdAndProductVariationId(customer.getId(),id);
                    return "deleted success";
                }
                else {
                        throw new UnauthorizedAccessException("you are trying to remove someone else's cart item.");
                }
            }
            else {
                    throw new DoesNotExistException("the item you mentioned doe not exist in your cart");
                }
        }
        else {
            throw new DoesNotExistException("No such product variation exists!!!");
        }
    }

    @Transactional
    @Modifying
    public String updateExistingItem(CartDto cartDto, UUID customer_id, UUID productVarId) throws UnauthorizedAccessException, DoesNotExistException {
        if(productVariationRepository.findById(productVarId).isPresent()) {
            ProductVariation productVariation = productVariationRepository.findById(productVarId).get();
            Optional<Cart> cartItem = cartRepository.findByCustomerIdAndProductVariationId(customer_id, productVarId);
            if (cartItem.isPresent()) {
                System.out.println(cartItem.get().getId());
                Cart cart = cartItem.get();
                if (cart.getCustomerUserId().getId() == customer_id){
                    System.out.println(cartDto.getQuantity());
                    int quant = cartDto.getQuantity();
                    if (quant!=0 && productVariation.getQuantityAvailable()>=quant){
                        cart.setQuantity(quant);
                        return "updated success";
                    }
                    else {
                        throw new DoesNotExistException("The mentioned quantity is not available right now. THe available quantity is : "+productVariation.getQuantityAvailable());
                    }

                }
                else {
                    throw new UnauthorizedAccessException("you are trying to remove someone else's cart item.");
                }

            }
            else {
                throw new DoesNotExistException("the item you mentioned doe not exist in your cart");
            }
        }
        else {
            throw new DoesNotExistException("No such product variation exists!!!");
        }
    }

    @Transactional
    public String emptyTheCart(Customer customer) throws DoesNotExistException {
        UUID customerId = customer.getId();
            List<Cart> cartList = cartRepository.findAllByCustomerId(customerId);
            if (cartList.isEmpty()){
                throw new DoesNotExistException("Your cart is empty already!!!");
            }
            else {
                for (Cart cart:cartList) {
                    UUID productVarId = cart.getProductVariationId().getId();
                    cartRepository.deleteByCustomerIdAndProductVariationId(customerId,productVarId);
                }
                return "removed all items from the cart successfully";
            }
    }
}
