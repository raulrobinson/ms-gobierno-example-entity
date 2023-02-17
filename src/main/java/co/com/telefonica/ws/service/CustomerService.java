package co.com.telefonica.ws.service;

import co.com.telefonica.ws.dto.RequestDTO;
import co.com.telefonica.ws.dto.ResponseDTO;
import co.com.telefonica.ws.entity.Customer;
import co.com.telefonica.ws.entity.Region;

import java.util.List;

/**
 * CLASS INTERFACE SERVICE REST.
 *
 * @autor: COE-Arquitectura-Telefonica
 * @date: 17-02-2023
 * @version 3.0.0
 */
public interface CustomerService {
    List<Customer> findCustomerAll();
    List<Customer> findCustomersByRegion(Region region);
    ResponseDTO createCustomer(RequestDTO customer);
    Customer updateCustomer(RequestDTO customer);
    Customer deleteCustomer(Customer customer);
    Customer getCustomer(Long id);
}
