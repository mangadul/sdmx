package com.sdmx.data.repository;

import com.sdmx.data.News;
import com.sdmx.data.NewsApproval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsApprovalRepository extends JpaRepository<NewsApproval, Long>
{

}