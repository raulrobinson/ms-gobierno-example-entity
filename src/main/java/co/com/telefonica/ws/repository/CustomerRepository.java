package co.com.telefonica.ws.repository;

import co.com.telefonica.ws.entity.Customer;
import co.com.telefonica.ws.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CLASS REPOSITORY ENTITY REST.
 *
 * @autor: COE-Arquitectura-Telefonica
 * @date: 17-02-2023
 * @version 3.0.0
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {
        Customer findByNumberID(String numberID);
        List<Customer> findByLastName(String lastName);
        List<Customer> findByRegion(Region region);
}
