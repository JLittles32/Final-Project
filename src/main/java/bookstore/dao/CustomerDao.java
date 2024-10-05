package bookstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
