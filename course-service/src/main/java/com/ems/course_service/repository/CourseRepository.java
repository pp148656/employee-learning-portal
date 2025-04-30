package com.ems.course_service.repository;

import com.ems.course_service.entity.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    Optional<Course> findByTitle(String title);
    List<Course> findAll();
    @Transactional
    @Modifying
    void deleteByTitle(String title);
}
