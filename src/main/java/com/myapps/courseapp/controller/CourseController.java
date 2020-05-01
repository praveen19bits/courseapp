package com.myapps.courseapp.controller;

import com.myapps.courseapp.exception.CourseNotFoundException;
import com.myapps.courseapp.model.Course;
import com.myapps.courseapp.repositary.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseRepo courseRepo;

    @PostMapping("/add")
    public Course addCourse(@Valid @RequestBody Course course){
        return courseRepo.save(course);
    }

    @GetMapping("/get/{id}")
    public Course getCourseById(@PathVariable Long id){
        return courseRepo.findById(id).orElseThrow(() -> new CourseNotFoundException("Course not found for Id:" + id));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Course>> getAllCourses(){
        return new ResponseEntity<>(courseRepo.findAll(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public Course updateById(@RequestBody Course newCourse, @PathVariable Long id){
        return courseRepo.findById(id)
                .map(updated -> {
                            return courseRepo.save(updated.updateCourse(newCourse));
                            })
                        .orElseThrow(() -> new CourseNotFoundException("Course not found for Id:" + id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        courseRepo.deleteById(id);
    }

}
