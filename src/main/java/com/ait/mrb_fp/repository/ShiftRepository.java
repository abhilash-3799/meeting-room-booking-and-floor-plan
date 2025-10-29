package com.ait.mrb_fp.repository;

import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, String> {
    List<Shift> findByIsActiveTrue();
}

