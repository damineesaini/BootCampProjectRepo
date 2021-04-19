package com.bootcamp.BootcampProject;

import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.user.*;
import com.bootcamp.BootcampProject.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootTest
class BootcampProjectApplicationTests {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryMetadataFieldRepository categoryMetadataFieldRepository;

	@Autowired
	CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderProductRepository orderProductRepository;

	@Autowired
	OrderStatusRepository orderStatusRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductReviewRepository productReviewRepository;

	@Autowired
	ProductVariationRepository productVariationRepository;

	@Autowired
	SellerRepository sellerRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void createUserAdmin(){
		User user = new User();
		user.setFirstName("Ankit");
		user.setLastName("saini");
		user.setEmail("damineesaini7@gmail.com");
		user.setPassword(bCryptPasswordEncoder.encode("9090"));
		user.setActive(true);
		ArrayList<Role> role = new ArrayList<>();
		Role role1 = new Role();
		role1.setAuthority("ROLE_ADMIN");
		role.add(role1);
		user.setRoles(role);
		userRepository.save(user);
	}

	@Test
	void createUserCustomer(){
		Customer customer = new Customer();
		customer.setContactNo("9711223011");
		Address address = new Address();
		User user = new User();
		address.setAddressLine("hno 582");
		address.setCity("mundka");
		address.setState("delhi");
		address.setCountry("India");
		address.setZipcode(110041);
		address.setLabel("home");
		user.addAddresses(address);
		user.setFirstName("Daminee");
		user.setLastName("saini");
		user.setEmail("damineesaini11@gmail.com");
		user.setPassword(bCryptPasswordEncoder.encode("9090@ds#DL"));
		ArrayList<Role> role = new ArrayList<>();
		Role role1 = new Role();
		role1.setAuthority("ROLE_CUSTOMER");
		role.add(role1);
		user.setRoles(role);
		customer.setUserId(user);
		customerRepository.save(customer);
	}

	@Test
	void createUserSeller(){
		Seller seller = new Seller();
		seller.setCompanyContactNo("9711843254");
		seller.setCompanyName("Indian Enterprises");
		seller.setGst("ADCF12909287");
		Address address = new Address();
		User user = new User();
		address.setAddressLine("hno 582");
		address.setCity("mundka");
		address.setState("delhi");
		address.setCountry("India");
		address.setZipcode(110041);
		address.setLabel("company");
		user.addAddresses(address);
		user.setFirstName("Deepak");
		user.setLastName("saini");
		user.setEmail("alfaaz.contact@gmail.com");
		user.setPassword(bCryptPasswordEncoder.encode("9090@ds"));
		ArrayList<Role> role = new ArrayList<>();
		Role role1 = new Role();
		role1.setAuthority("ROLE_SELLER");
		role.add(role1);
		user.setRoles(role);
		seller.setUserId(user);
		sellerRepository.save(seller);
	}

	@Test
	void createCategory(){
		Category category = new Category();
		category.setActive(true);
		category.setHasChild(true);
		category.setName("Clothes");
		categoryRepository.save(category);

		Category category1 = new Category();
		category1.setParentCategoryId(category);
		category1.setActive(true);
		category1.setHasChild(false);
		category1.setName("Shirt");
		categoryRepository.save(category1);

		Category category2 = new Category();
		category2.setParentCategoryId(category);
		category2.setActive(true);
		category2.setHasChild(false);
		category2.setName("Jeans");
		categoryRepository.save(category2);
	}

	@Test
	void createProduct(){
		Product product = new Product();

	}

}