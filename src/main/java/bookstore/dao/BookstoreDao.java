package bookstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.entity.Bookstore;

public interface BookstoreDao extends JpaRepository<Bookstore, Long> {

}
