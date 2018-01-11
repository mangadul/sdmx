package com.sdmx.data.repository;

import com.sdmx.data.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends ContentRepository<Publication>, JpaRepository<Publication, Long> {

	@Query(
		"SELECT p FROM Publication p LEFT JOIN p.categories c " +
		"WHERE p.status='1' AND c.id IS NULL AND LOWER(title) LIKE LOWER(:title) " +
		"ORDER BY title"
	)
	public Page<Publication> findGeneralByTitle(@Param("title") String title, Pageable pageable);

	@Query(
		"SELECT p FROM Publication p INNER JOIN p.categories c " +
		"WHERE p.status='1' AND c.id IN (:category_ids) AND LOWER(title) LIKE LOWER(:title) " +
		"ORDER BY title"
	)
	public Page<Publication> findByTitle(
		@Param("title") String title,
		@Param("category_ids") List<Long> category_ids,
		Pageable pageable
	);

	@Query(
		"SELECT COUNT(p) FROM Publication p INNER JOIN p.categories c " +
		"WHERE p.status='1' AND c.id IN (:category_ids) AND LOWER(title) LIKE LOWER(:title)"
	)
	public Long countAllByTitle(
		@Param("title") String title,
		@Param("category_ids") List<Long> category_ids
	);

	@Query("SELECT p FROM Publication p WHERE p.status='1' AND lower(title) like lower(:title) ORDER BY title")
	public Page<Publication> findByTitle(@Param("title") String title, Pageable pageable);

	@Query("SELECT count(p) FROM Publication p WHERE p.status='1' AND lower(title) like lower(:title)")
	public Long countAllByTitle(@Param("title") String title);

	public Page<Publication> findAllByStatus(char status, Pageable pageable);
}