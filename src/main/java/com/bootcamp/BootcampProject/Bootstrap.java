package com.bootcamp.BootcampProject;

import com.bootcamp.BootcampProject.entity.user.*;
import com.bootcamp.BootcampProject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() < 3) {
            User user = new User();
            user.setFirstName("Ankit");
            user.setLastName("saini");
            user.setEmail("damineesaini7@gmail.com");
            user.setPassword(bCryptPasswordEncoder.encode("9090"));
            user.setActive(true);
            user.setNotLocked(true);
            user.setNotDeleted(true);
            Address address = new Address();
            address.setAddressLine("hno 52");
            address.setCity("muka");
            address.setState("dhi");
            address.setCountry("Idia");
            address.setZipcode(11041);
            address.setLabel("hme");
            user.addAddresses(address);
            ArrayList<Role> role = new ArrayList<>();
            Role role1 = new Role();
            role1.setAuthority("ROLE_ADMIN");
            role.add(role1);
            user.setRoles(role);
            userRepository.save(user);

            Customer customer = new Customer();
            customer.setContactNo("9711223011");
            Address address1 = new Address();
            User user1 = new User();
            address1.setAddressLine("hno 582");
            address1.setCity("mundka");
            address1.setState("delhi");
            address1.setCountry("India");
            address1.setZipcode(110041);
            address1.setLabel("home");
            user1.addAddresses(address1);
            user1.setFirstName("Daminee");
            user1.setLastName("saini");
            user1.setEmail("damineesaini11@gmail.com");
            user1.setActive(true);
            user1.setNotLocked(true);
            user1.setNotDeleted(true);
            user1.setPassword(bCryptPasswordEncoder.encode("9090@ds#DL"));
            ArrayList<Role> roles = new ArrayList<>();
            Role role2 = new Role();
            role2.setAuthority("ROLE_CUSTOMER");
            roles.add(role2);
            user1.setRoles(roles);
            customer.setUserId(user1);
            customerRepository.save(customer);

            Seller seller = new Seller();
            seller.setCompanyContactNo("9711843254");
            seller.setCompanyName("Indian Enterprises");
            seller.setGst("ADCF12909287");
            Address address2 = new Address();
            User user2 = new User();
            address2.setAddressLine("hno 582");
            address2.setCity("mundka");
            address2.setState("delhi");
            address2.setCountry("India");
            address2.setZipcode(110041);
            address2.setLabel("company");
            user2.addAddresses(address2);
            user2.setFirstName("Deepak");
            user2.setLastName("saini");
            user2.setActive(true);
            user2.setNotLocked(true);
            user2.setNotDeleted(true);
            user2.setEmail("alfaaz.contact@gmail.com");
            user2.setPassword(bCryptPasswordEncoder.encode("9090@ds"));
            ArrayList<Role> roles1 = new ArrayList<>();
            Role role3 = new Role();
            role3.setAuthority("ROLE_SELLER");
            roles1.add(role3);
            user2.setRoles(roles1);
            seller.setUserId(user2);
            sellerRepository.save(seller);
        }
//        if(categoryRepository.count()<2){
//            Category category = new Category();
//            category.setName("Clothes");
//            category.setHasChild(true);
//            category.setActive(true);
//            categoryRepository.save(category);
//
//            Category category1 = new Category();
//            category1.setName("Men Shirt");
//            category1.setActive(true);
//            category1.setHasChild(false);
//            category1.setParentCategoryId(category);
//            categoryRepository.save(category1);
//
//            CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
//            categoryMetadataField.setName("color");
//            categoryMetadataFieldRepository.save(categoryMetadataField);
//
//            CategoryMetadataField categoryMetadataField1 = new CategoryMetadataField();
//            categoryMetadataField1.setName("size");
//            categoryMetadataFieldRepository.save(categoryMetadataField1);
//
//            CategoryMetadataFieldValues categoryMetadataFieldValues = new CategoryMetadataFieldValues();
//            categoryMetadataFieldValues.setValues("l,m,s");
//            categoryMetadataFieldValues.setCategoryId(category1);
//            categoryMetadataFieldValues.setCategoryMetadataFieldId(categoryMetadataField1);
//            categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
//
//            CategoryMetadataFieldValues categoryMetadataFieldValues1 = new CategoryMetadataFieldValues();
//            categoryMetadataFieldValues1.setValues("black,white,blue");
//            categoryMetadataFieldValues1.setCategoryMetadataFieldId(categoryMetadataField);
//            categoryMetadataFieldValues1.setCategoryId(category1);
//            categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues1);
//        }
    }
}
