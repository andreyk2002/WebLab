package dao.impl.jpa;

import dao.CustomerDao;
import dao.DaoException;
import dao.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CustomerDaoJpa implements CustomerDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void changeMoney(long id, double delta) {
        Optional<Customer> current = getById(id);
        if(current.isEmpty()){
            throw new DaoException("Cannot find customer with id = " + id);
        }
        Customer customer = current.get();
        double money = customer.getMoney();
        customer.setMoney(money + delta);
        entityManager.flush();
        entityManager.merge(customer);
    }

    @Override
    public long addCustomer(Customer current) {
        entityManager.persist(current);
        entityManager.flush();
        return current.getId();
    }

    @Override
    public Optional<Customer> getById(long id) {
        Customer customer = entityManager.find(Customer.class, id);
        return Optional.ofNullable(customer);
    }
}
