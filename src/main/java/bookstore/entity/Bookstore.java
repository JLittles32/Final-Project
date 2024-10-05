package bookstore.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Bookstore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookstoreId;
	private String bookstoreName;
	private String bookstoreAddress;
	private String bookstoreCity;
	private String bookstoreState;
	private String bookstoreZip;
	private String bookstorePhone;
	
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "bookstore_customer", joinColumns = @JoinColumn(name = "bookstore_id"),
	inverseJoinColumns = @JoinColumn(name = "customer_id"))
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Customer> customers = new HashSet<>();
	
	@OneToMany(mappedBy = "bookstore", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Employee> employees = new HashSet<>();
}
