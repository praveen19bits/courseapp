package com.myapps.courseapp.repositary;

import com.myapps.courseapp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);

    List<Course> findByInstructorInstId(Long instructorId);

    Optional<Object> findByCourseIdAndInstructorInstId(Long courseId, Long instructorId);
}
