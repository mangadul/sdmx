package com.sdmx.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository<T> {

	public T findById(Long id);

	public List<T> findTop5ByOrderByCreatedDesc();

	public List<T> findTop10ByOrderByCreatedDesc();

	public Page<T> findGeneralByTitle(@Param("title") String title, Pageable pageable);

	public Page<T> findByTitle(
        @Param("title") String title,
        @Param("category_ids") List<Long> category_ids,
        Pageable pageable
    );

	public Long countAllByTitle(@Param("title") String title, @Param("category_ids") List<Long> category_ids);

	public Page<T> findByTitle(@Param("title") String title, Pageable pageable);

	public Long countAllByTitle(@Param("title") String title);
}