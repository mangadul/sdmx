package com.sdmx.data.repository;

import com.sdmx.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CrudRepository<Category, Long> {

	@Query(nativeQuery = true,
		value = "WITH RECURSIVE rec AS ( " +
			"SELECT *, ARRAY[parent_id] AS path FROM category WHERE parent_id IS NULL " +
			"UNION ALL  " +
			"SELECT c.*, path || c.parent_id FROM category c  " +
			"INNER JOIN rec ON rec.id=c.parent_id " +
		") " +
		"SELECT rec.*, df.id AS data_flow_id, array_length(path, 1) as level FROM rec " +
		"LEFT JOIN data_flow df ON df.category_id = rec.id " +
		"ORDER BY rec.path, rec.name"
	)
	List<Category> getTree();

	List<Category> findAllByOrderByName();
}