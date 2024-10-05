package bookstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
