package dao.impl;

import dao.AbstractDao;
import dao.CustomerDao;
import dao.RowMapper;
import dao.entity.Customer;
import dao.mapper.CustomerRowMapper;

import java.sql.Connection;

public class CustomerDaoImpl extends AbstractDao<Customer> implements CustomerDao {

    public static final String TABLE_NAME = "customers";
    public static final String CHANGE_MONEY = "UPDATE customers SET money = ? WHERE ID = ?";
    public static final String ADD_CUSTOMER = "INSERT into customers (Name, Surname, Money) VALUES (?, ?, ?)";

    public CustomerDaoImpl(Connection connection) {
        super(connection, new CustomerRowMapper(), TABLE_NAME);
    }

    @Override
    public void changeMoney(long id, double newMoney) {
        updateQuery(CHANGE_MONEY, newMoney, id);
    }

    @Override
    public long addCustomer(Customer customer) {
        return updateQueryAndGetID(ADD_CUSTOMER, customer.getName(), customer.getSurname(), customer.getMoney());
    }
}
