package bookstore.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookstore.controller.model.BookstoreCustomer;
import bookstore.controller.model.BookstoreData;
import bookstore.controller.model.BookstoreEmployee;
import bookstore.dao.BookstoreDao;
import bookstore.dao.CustomerDao;
import bookstore.dao.EmployeeDao;
import bookstore.entity.Bookstore;
import bookstore.entity.Customer;
import bookstore.entity.Employee;

@Service
public class BookstoreService {

	@Autowired
	private BookstoreDao bookstoreDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	private void copyBookstoreFields(Bookstore bookstore, BookstoreData bookstoreData) {
		bookstore.setBookstoreId(bookstoreData.getBookstoreId());
		bookstore.setBookstoreAddress(bookstoreData.getBookstoreAddress());
		bookstore.setBookstoreCity(bookstoreData.getBookstoreCity());
		bookstore.setBookstoreName(bookstoreData.getBookstoreName());
		bookstore.setBookstorePhone(bookstoreData.getBookstorePhone());
		bookstore.setBookstoreState(bookstoreData.getBookstoreState());
		bookstore.setBookstoreZip(bookstoreData.getBookstoreZip());
	}
	
	private void copyEmployeeFields(Employee employee, BookstoreEmployee bookstoreEmployee) {
		employee.setEmployeeFirstName(bookstoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(bookstoreEmployee.getEmployeeLastName());
		employee.setEmployeeId(bookstoreEmployee.getEmployeeId());
		employee.setEmployeeJobTitle(bookstoreEmployee.getEmployeeJobTitle());
		employee.setEmployeePhone(bookstoreEmployee.getEmployeePhone());
	}
	
	private void copyCustomerFields (Customer customer, BookstoreCustomer bookstoreCustomer) {
		customer.setCustomerFirstName(bookstoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(bookstoreCustomer.getCustomerLastName());
		customer.setCustomerId(bookstoreCustomer.getCustomerId());
		customer.setCustomerEmail(bookstoreCustomer.getCustomerEmail());
	}
	
	
	
	@Transactional(readOnly = false)
	public BookstoreData saveBookstore(BookstoreData bookstoreData) {
		Long bookstoreId = bookstoreData.getBookstoreId();
		Bookstore bookstore = findOrCreateBookstore(bookstoreId);
		copyBookstoreFields(bookstore, bookstoreData);
		return new BookstoreData(bookstoreDao.save(bookstore));
	}
	
	
	
	@Transactional(readOnly = false)
	public BookstoreEmployee saveEmployee(Long bookstoreId, BookstoreEmployee bookstoreEmployee) {
		Bookstore bookstore = findBookstoreById(bookstoreId);
		Long employeeId = bookstoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(bookstoreId, employeeId);
		
		copyEmployeeFields(employee, bookstoreEmployee);
		
		employee.setBookstore(bookstore);
		bookstore.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new BookstoreEmployee(dbEmployee);
	}

	
	@Transactional
	public BookstoreCustomer saveCustomer(Long bookstoreId, BookstoreCustomer bookstoreCustomer) {

		Bookstore bookstore = findBookstoreById(bookstoreId);
		Long customerId = bookstoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(bookstoreId, customerId);
		
		copyCustomerFields(customer, bookstoreCustomer);
		
		customer.getBookstore().add(bookstore);
		bookstore.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new BookstoreCustomer(dbCustomer);
	}

	
	
	private Bookstore findOrCreateBookstore(Long bookstoreId) {
		if(Objects.isNull(bookstoreId)) {
			return new Bookstore();
		} else {
			return findBookstoreById(bookstoreId);
		}
	}		

	
	private Employee findOrCreateEmployee(Long bookstoreId, Long employeeId) {
		if(Objects.isNull(employeeId)) {
			return new Employee();
		}
		return findEmployeeById(bookstoreId, employeeId);
	}

	
	private Customer findOrCreateCustomer(Long bookstoreId, Long customerId) {
		if(Objects.isNull(customerId)) {
			return new Customer();
		}
		return findCustomerById(bookstoreId, customerId);
	}
	
	
	
	private Employee findEmployeeById(Long bookstoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId).orElseThrow(() 
				-> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));
		
		if(employee.getBookstore().getBookstoreId() != bookstoreId) {
			throw new IllegalArgumentException("The employee with ID=" + employeeId
					+ " is not employee by the bookstore with ID=" + bookstoreId + ".");
		} return employee;
	}

	
	
	private Customer findCustomerById(Long bookstoreId, Long customerId) {
		Customer customer = customerDao.findById(customerId).orElseThrow(() 
				-> new NoSuchElementException("Customer with ID=" + customerId + " was not found."));
		
		boolean found = false;
		
		for(Bookstore bookstore : customer.getBookstore()) {
			if(bookstore.getBookstoreId() == bookstoreId) {
				found = true;
				break;
			}
		} if(!found) {
			throw new IllegalArgumentException("The customer with ID=" + customerId +
					" is not a member of the bookstore with ID=" + bookstoreId);
		} return customer;
	}
	
	
	
	private Bookstore findBookstoreById(Long bookstoreId) {
		return bookstoreDao.findById(bookstoreId).orElseThrow(()
				-> new NoSuchElementException("The bookstore with ID=" + bookstoreId + " was not found."));
	}


	
	@Transactional(readOnly = true)
	public List<BookstoreData> retrieveAllBookstores() {
		List<Bookstore> bookstores = bookstoreDao.findAll();
		
		List<BookstoreData> result = new LinkedList<>();
		
		for(Bookstore bookstore : bookstores) {
			BookstoreData bsd = new BookstoreData(bookstore);
		
		bsd.getCustomers().clear();
		bsd.getEmployees().clear();
		
		result.add(bsd);
		}
		return result;
	}

	
	@Transactional(readOnly = true)
	public BookstoreData retrieveBookstoreById(Long bookstoreId) {
		return new BookstoreData(findBookstoreById(bookstoreId));
	}

	
	@Transactional(readOnly = false)
	public void deleteBookstoreById(Long bookstoreId) {
		Bookstore bookstore = findBookstoreById(bookstoreId);
		bookstoreDao.delete(bookstore);		
	}
}
