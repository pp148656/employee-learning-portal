package com.ems.course_service.repository;

import com.ems.course_service.entity.Enrollment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    boolean existsByCourseIdAndEmpId(Integer course_id, Integer employeeId);
    Optional<Enrollment> findByEmpId(Integer empId);
    @Transactional
    @Modifying
    void deleteByEmpId(Integer empId);

    @Transactional
    @Modifying
    void deleteByCourseId(Integer courseId);
}
