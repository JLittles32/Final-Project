package bookstore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bookstore.controller.model.BookstoreCustomer;
import bookstore.controller.model.BookstoreData;
import bookstore.controller.model.BookstoreEmployee;
import bookstore.service.BookstoreService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bookstore")
@Slf4j

public class BookstoreController {
	@Autowired
	private BookstoreService bookstoreService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookstoreData insertBookstore(@RequestBody BookstoreData bookstoreData) {
		log.info("Creating bookstore {}", bookstoreData);
		return bookstoreService.saveBookstore(bookstoreData);
	}
	
	
	@PutMapping("/{bookstoreId}")
	public BookstoreData updateBookstore(@RequestBody BookstoreData bookstoreData, @PathVariable Long bookstoreId) {
		bookstoreData.setBookstoreId(bookstoreId);
		log.info("Updating bookstore {}", bookstoreId);
		return bookstoreService.saveBookstore(bookstoreData);
	}
	
	
	@PostMapping("{bookstoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookstoreEmployee addEmployeeToBookstore(@PathVariable Long bookstoreId,
			@RequestBody BookstoreEmployee bookstoreEmployee) {
		log.info("Adding employee {} to bookstore with ID={}", bookstoreEmployee, bookstoreId);
		return bookstoreService.saveEmployee(bookstoreId, bookstoreEmployee);
	}
	
	
	@PostMapping("/{bookstoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookstoreCustomer addCustomerToBookstore(@PathVariable Long bookstoreId,
			@RequestBody BookstoreCustomer bookstoreCustomer) {
		log.info("Adding customer {} to bookstore with ID=", bookstoreCustomer, bookstoreId);
		return bookstoreService.saveCustomer(bookstoreId, bookstoreCustomer);
	}
	
	
	@GetMapping
	public List<BookstoreData> retrieveAllBookstores() {
		log.info("Retrieving all bookstores");
		return bookstoreService.retrieveAllBookstores();
	}
	
	
	@GetMapping("/{bookstoreId}")
	public BookstoreData retrieveBookstoreById(@PathVariable Long bookstoreId) {
		log.info("Retrieving bookstore with ID={}", bookstoreId);
		return bookstoreService.retrieveBookstoreById(bookstoreId);
	}
	
	
	@DeleteMapping("/{bookstoreId}")
	public Map<String, String> deleteBookstoreById(@PathVariable Long bookstoreId) {
		log.info("Deleting bookstore with ID={}", bookstoreId);
		bookstoreService.deleteBookstoreById(bookstoreId);
		return Map.of("message", "Bookstore with ID=" + bookstoreId + " deleted.");
	}
}
