package com.ait.mrbfp.repository;

import com.ait.mrbfp.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<Queue, String> {

    List<Queue> findByOffice_OfficeId(String officeId);

    boolean existsByQueueName(String queueName);
}
