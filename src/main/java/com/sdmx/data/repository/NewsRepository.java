package com.sdmx.data.repository;

import com.sdmx.data.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends ContentRepository<News>, JpaRepository<News, Long> {

	@Query(
		"SELECT n FROM News n LEFT JOIN n.categories c " +
		"WHERE n.status='1' AND c.id IS NULL AND LOWER(title) LIKE LOWER(:title) " +
		"ORDER BY title"
	)
	public Page<News> findGeneralByTitle(@Param("title") String title, Pageable pageable);

	@Query(
		"SELECT n FROM News n INNER JOIN n.categories c " +
			"WHERE n.status='1' AND c.id IN (:category_ids) AND LOWER(title) LIKE LOWER(:title) " +
			"ORDER BY title"
	)
	public Page<News> findByTitle(
		@Param("title") String title,
		@Param("category_ids") List<Long> category_ids,
		Pageable pageable
	);

	@Query(
		"SELECT COUNT(n) FROM News n INNER JOIN n.categories c " +
		"WHERE n.status='1' AND c.id IN (:category_ids) AND LOWER(title) LIKE LOWER(:title)"
	)
	public Long countAllByTitle(
		@Param("title") String title,
		@Param("category_ids") List<Long> category_ids
	);

	@Query("SELECT n FROM News n WHERE n.status='1' AND LOWER(title) LIKE LOWER(:title) ORDER BY title")
	public Page<News> findByTitle(@Param("title") String title, Pageable pageable);

	@Query("SELECT COUNT(n) FROM News n WHERE n.status='1' AND LOWER(title) LIKE LOWER(:title)")
	public Long countAllByTitle(@Param("title") String title);

	public Page<News> findAllByStatus(char status, Pageable pageable);
}