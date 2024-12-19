package tr.edu.ogu.lab2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.edu.ogu.lab2.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	Page<Country> findByNameStartingWith(String searchText, Pageable pageable);

}
