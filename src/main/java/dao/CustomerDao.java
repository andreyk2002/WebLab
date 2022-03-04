package dao;

import dao.entity.Customer;

import java.util.Optional;
/**
 * Performs persistence operations on customer entities
 */
public interface CustomerDao {


    void changeMoney(long id, double delta);

    long addCustomer(Customer current);

    Optional<Customer> getById(long id);
}
