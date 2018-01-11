package com.sdmx.data.repository;

import com.sdmx.data.Category;
import com.sdmx.data.DataFlow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DataFlowRepository extends ContentRepository<DataFlow>, JpaRepository<DataFlow, Long> {

    @Query(
        "SELECT df FROM DataFlow df LEFT JOIN df.categories c " +
        "WHERE c.id IS NULL AND LOWER(title) LIKE LOWER(:title) " +
        "ORDER BY title"
    )
    public Page<DataFlow> findGeneralByTitle(@Param("title") String title, Pageable pageable);

    @Query(
        "SELECT df FROM DataFlow df INNER JOIN df.categories c " +
        "WHERE c.id IN (:category_ids) AND LOWER(title) LIKE LOWER(:title) " +
        "ORDER BY title"
    )
    public Page<DataFlow> findByTitle(
        @Param("title") String title,
        @Param("category_ids") List<Long> category_ids,
        Pageable pageable
    );

    @Query(
        "SELECT COUNT(df) FROM DataFlow df INNER JOIN df.categories c " +
        "WHERE c.id IN (:category_ids) AND LOWER(title) LIKE LOWER(:title)"
    )
    public Long countAllByTitle(
        @Param("title") String title,
        @Param("category_ids") List<Long> category_ids
    );

    @Query("SELECT df FROM DataFlow df WHERE lower(title) like lower(:title) ORDER BY title")
    public Page<DataFlow> findByTitle(@Param("title") String title, Pageable pageable);

    @Query("SELECT count(df) FROM DataFlow df WHERE lower(title) like lower(:title)")
    public Long countAllByTitle(@Param("title") String title);

	/*@Query(nativeQuery = true,
		value = "WITH RECURSIVE rec AS (" +
			"SELECT *, ARRAY[parent_id] AS path FROM category WHERE parent_id IS NULL " +
			"UNION ALL " +
			"SELECT c.*, path || c.parent_id FROM category c " +
			"INNER JOIN rec ON rec.id=c.parent_id" +
		")" +
		"SELECT * FROM rec ORDER BY path, title"
	)
	List<Category> getTree();*/

    /*@Query(
        "SELECT v.title, ds.year, ds.month, ds.attributes, ds.value FROM data_flow_item dfi " +
        "INNER JOIN Variable v ON v.id=dfi.variable_id " +
        "INNER JOIN data_set ds ON ds.variable_id=v.id " +
        "WHERE dfi.data_flow_id = ?1 " +
        "ORDER BY v.title, ds.year, ds.month"
    )
    List<Object[]> findDataSet(Long id);*/
}