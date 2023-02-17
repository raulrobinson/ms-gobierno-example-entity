package co.com.telefonica.ws.service.impl;

import co.com.telefonica.ws.dto.RequestDTO;
import co.com.telefonica.ws.dto.ResponseDTO;
import co.com.telefonica.ws.entity.Customer;
import co.com.telefonica.ws.entity.Region;
import co.com.telefonica.ws.repository.CustomerRepository;
import co.com.telefonica.ws.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CLASS SERVICE IMPLEMENTATION OF SERVICIO REST.
 *
 * @autor: COE-Arquitectura-Telefonica
 * @date: 17-02-2023
 * @version 3.0.0
 */
@Slf4j
@Service
public class CustomerServiceImpl  implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public ResponseDTO createCustomer(RequestDTO customer) {

        Customer customerDB = customerRepository.findByNumberID(customer.getNumberID());

        if (customerDB != null){
            return ResponseDTO.builder()
                    .code(400)
                    .message("Customer Exist..!")
                    .build();
        }

        var customerSave = Customer.builder()
                .numberID(customer.getNumberID())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .photoUrl(customer.getPhotoUrl())
                .region(customer.getRegion())
                .state("CREATED")
                .build();

        customerDB = customerRepository.save(customerSave);

        return ResponseDTO.builder()
                .code(200)
                .message("Customer created successfully..!")
                .build();
    }

    @Override
    public Customer updateCustomer(RequestDTO customer) {
        Customer customerDB = getCustomer(customer.getId());
        if (customerDB == null){
            return  null;
        }
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setPhotoUrl(customer.getPhotoUrl());
        customerDB.setRegion(customer.getRegion());
        customerDB.setState(customer.getState());

        return customerRepository.save(customerDB);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if (customerDB ==null){
            return  null;
        }
        customer.setState("DELETED");
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return  customerRepository.findById(id).orElse(null);
    }
}
