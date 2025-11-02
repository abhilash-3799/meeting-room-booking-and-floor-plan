package com.ait.mrbfp.repository;

import com.ait.mrbfp.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, String> {
    boolean existsByShiftName(String shiftName);
}
