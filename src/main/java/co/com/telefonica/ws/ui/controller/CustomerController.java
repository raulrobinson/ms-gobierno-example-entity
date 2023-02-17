package co.com.telefonica.ws.ui.controller;

import co.com.telefonica.ws.dto.RequestDTO;
import co.com.telefonica.ws.dto.ResponseDTO;
import co.com.telefonica.ws.entity.Customer;
import co.com.telefonica.ws.entity.Region;
import co.com.telefonica.ws.exception.ErrorException;
import co.com.telefonica.ws.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CONTROLLER CLASS FOR REST.
 *
 * @autor: COE-Arquitectura-Telefonica
 * @date: 17-02-2023
 * @version 3.0.0
 */
@Slf4j
@RestController
@RequestMapping(path = "${controller.properties.base-path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * RETRIEVE ALL CUSTOMERS
     * @param regionId Long
     * @return List<Customer>
     */
    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers(
            @RequestParam(name = "regionId" , required = false) Long regionId ) {
        List<Customer> customers =  new ArrayList<>();
        if (null ==  regionId) {
            customers = customerService.findCustomerAll();
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }else{
            Region Region= new Region();
            Region.setId(regionId);
            customers = customerService.findCustomersByRegion(Region);
            if ( null == customers ) {
                log.error("Customers with Region id {} not found.", regionId);
                return  ResponseEntity.notFound().build();
            }
        }

        return  ResponseEntity.ok(customers);
    }

    /**
     * RETRIEVE SINGLE CUSTOMER
     * @param id long
     * @return Customer
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(
            @PathVariable("id") long id) {
        log.info("Fetching Customer with id {}", id);
        Customer customer = customerService.getCustomer(id);
        if (  null == customer) {
            log.error("Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(customer);
    }

    /**
     * CREATE A CUSTOMER.
     * @param customer RequestDTO
     * @param result ?
     * @return ?
     */
    @PostMapping
    public ResponseEntity<?> createCustomer(
            @Valid @RequestBody RequestDTO customer, BindingResult result) {
        log.info("Creating Customer : {}", customer);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        ResponseDTO customerDB = customerService.createCustomer(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
    }

    /**
     * UPDATE A CUSTOMER.
     * @param id long
     * @param customer RequestDTO
     * @return ?
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable("id") long id, @RequestBody RequestDTO customer) {
        log.info("Updating Customer with id {}", id);
        Customer currentCustomer = customerService.getCustomer(id);
        if ( null == currentCustomer ) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        customer.setId(id);
        currentCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.ok(currentCustomer);
    }

    /**
     * DELETE A CUSTOMER.
     * @param id long
     * @return Customer
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(
            @PathVariable("id") long id) {
        log.info("Fetching & Deleting Customer with id {}", id);

        Customer customer = customerService.getCustomer(id);
        if ( null == customer ) {
            log.error("Unable to delete. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        customer = customerService.deleteCustomer(customer);
        return  ResponseEntity.ok(customer);
    }

    /**
     * CLASS FORMAT
     * @param result BindingResult
     * @return String
     */
    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());

        ErrorException errorException = ErrorException.builder()
                .code("01")
                .messages(errors)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";

        try {
            jsonString = mapper.writeValueAsString(errorException);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
