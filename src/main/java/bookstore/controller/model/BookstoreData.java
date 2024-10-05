package bookstore.controller.model;

import java.util.HashSet;
import java.util.Set;

import bookstore.entity.Bookstore;
import bookstore.entity.Customer;
import bookstore.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookstoreData {


	private Long bookstoreId;
	private String bookstoreName;
	private String bookstoreAddress;
	private String bookstoreCity;
	private String bookstoreState;
	private String bookstoreZip;
	private String bookstorePhone;

	private Set<BookstoreCustomer> customers = new HashSet<>();

	private Set<BookstoreEmployee> employees = new HashSet<>();

	
	public BookstoreData(Bookstore bookstore) {
		bookstoreId = bookstore.getBookstoreId();
		bookstoreName = bookstore.getBookstoreName();
		bookstoreAddress= bookstore.getBookstoreAddress();
		bookstoreCity = bookstore.getBookstoreCity();
		bookstoreState = bookstore.getBookstoreState();
		bookstoreZip = bookstore.getBookstoreZip();
		bookstorePhone = bookstore.getBookstorePhone();
		
		for(Customer customer : bookstore.getCustomers()) {
			customers.add(new BookstoreCustomer(customer));
		}
		for(Employee employee : bookstore.getEmployees()) {
			employees.add(new BookstoreEmployee(employee));
		}
	}
}
