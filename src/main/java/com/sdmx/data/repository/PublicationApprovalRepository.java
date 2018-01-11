package com.sdmx.data.repository;

import com.sdmx.data.PublicationApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationApprovalRepository extends JpaRepository<PublicationApproval, Long>
{

}