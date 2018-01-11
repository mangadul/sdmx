package com.sdmx.data.repository;

import com.sdmx.data.DataFlow;
import com.sdmx.data.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSetRepository extends JpaRepository<DataFlow, Long> {
	/*@Query(nativeQuery = true,
		value = "WITH RECURSIVE rec AS (" +
			"SELECT *, ARRAY[parent_id] AS path FROM category WHERE parent_id IS NULL " +
			"UNION ALL " +
			"SELECT c.*, path || c.parent_id FROM category c " +
			"INNER JOIN rec ON rec.id=c.parent_id" +
		")" +
		"SELECT * FROM rec ORDER BY path, name"
	)
	List<Category> getTree();*/

    /*@Query(nativeQuery = true,
        value = "SELECT ds.* FROM data_flow_item dfi " +
            "INNER JOIN variable v ON v.id=dfi.variable_id " +
            "INNER JOIN data_set ds ON ds.variable_id=v.id " +
            "WHERE dfi.data_flow_id = ?1 " +
            "ORDER BY v.name, ds.year, ds.month"
    )*/

    @Query("SELECT ds, v FROM DataFlow df " +
            "INNER JOIN df.variables v " +
            "INNER JOIN v.dataSets ds " +
            "WHERE df.id = ?1 " +
            "ORDER BY v.name, ds.year, ds.month"
    )
    List<DataSet> findByDataFlow(Long id);
}