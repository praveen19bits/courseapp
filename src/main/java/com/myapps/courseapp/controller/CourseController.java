package com.myapps.courseapp.controller;

import com.myapps.courseapp.exception.CourseNotFoundException;
import com.myapps.courseapp.exception.InstructorNotFoundException;
import com.myapps.courseapp.model.Course;
import com.myapps.courseapp.model.Instructor;
import com.myapps.courseapp.repositary.CourseRepo;
import com.myapps.courseapp.repositary.InstructorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseController {

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    InstructorRepo instructorRepo;

    //CRUD operations with instrcutors id.
    @GetMapping("/instructors/{instructorId}/courses")
    public List<Course> getCoursesByInstructor(@PathVariable Long instructorId) {
        return courseRepo.findByInstructorInstId(instructorId)
                .stream()
                .map(course -> {
                    return course.addInstructorLink();
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/instructors/{instructorId}/courses/{courseId}")
    public Course getCoursesByInstructor(@PathVariable Long instructorId, @PathVariable Long courseId) {
        Course course = (Course) courseRepo.findByCourseIdAndInstructorInstId(instructorId, courseId).get();
        course.addInstructorLink();
        return course;
    }

    @PostMapping("/instructors/{instructorId}/courses")
    public Course createCourse(@PathVariable Long instructorId,
                               @Valid @RequestBody Course course) {
        Instructor inst = instructorRepo.findById(instructorId).get();
        course.setInstructor(inst);
        return courseRepo.save(course);
    }

    @PostMapping("/instructors/{instructorId}/courses/{courseId}")
    public Course createCourse(@PathVariable Long instructorId, @PathVariable Long courseId) {
        Course course = courseRepo.findById(courseId).get();
        course.setInstructor(instructorRepo.findById(instructorId).get());
        return courseRepo.save(course);
    }

    @PutMapping("/instructors/{instructorId}/courses/{courseId}")
    public Course updateCourse(@PathVariable Long instructorId,
                               @PathVariable Long courseId, @Valid @RequestBody Course newCourse) {
        if (!instructorRepo.existsById(instructorId)) {
            throw new InstructorNotFoundException("instructorId not found");
        }

        return courseRepo.findById(courseId).map(course -> {
            course.updateCourse(newCourse);
            return courseRepo.save(course);
        }).orElseThrow(() -> new CourseNotFoundException("Course not found:" + courseId));
    }

    /*@DeleteMapping("/instructors/{instructorId}/courses/{courseId}")
    public void deleteCourse(@PathVariable Long instructorId, @PathVariable Long courseId) {
        Course co = (Course)courseRepo.findByCourseIdAndInstructorInstId(courseId, instructorId).get();
        courseRepo.deleteById(co.getCourseId());
    }*/

    //CRUD operations without instrcutors id.
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseRepo.findAll()
                .stream()
                .map(Course::addInstructorLink)
                .collect(Collectors.toList());
    }

    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable Long id) {
        Course course = courseRepo.findById(id).orElseThrow(() -> new CourseNotFoundException("Course not found for Id:" + id));
        course.addInstructorLink();
        return course;
    }

    @PostMapping("/courses")
    public Course addCourse(@Valid @RequestBody Course course) {
        return courseRepo.save(course);
    }

    @PutMapping("/courses/{id}")
    public Course updateById(@RequestBody Course newCourse, @PathVariable Long id) {
        return courseRepo.findById(id)
                .map(updated -> courseRepo.save(updated.updateCourse(newCourse)))
                .orElseThrow(() -> new CourseNotFoundException("Course not found for Id:" + id));
    }

    @DeleteMapping("/courses/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        courseRepo.deleteById(id);
    }
}
