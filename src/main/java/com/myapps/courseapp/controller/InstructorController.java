package com.myapps.courseapp.controller;

import com.myapps.courseapp.exception.InstructorNotFoundException;
import com.myapps.courseapp.model.Instructor;
import com.myapps.courseapp.repositary.InstructorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class InstructorController {
    @Autowired
    InstructorRepo instructRepo;

    @GetMapping("/instructors")
    public List<Instructor> getAllInstructor() {
        return instructRepo.findAll();
    }

    @GetMapping("/instructors/{id}")
    public Instructor getInstructorById(@PathVariable Long id) {
        return instructRepo.findById(id).orElseThrow(() -> new InstructorNotFoundException("Instructor not found for Id:" + id));
    }

    @PostMapping("/instructors")
    public Instructor addInstructor(@Valid @RequestBody Instructor inst) {
        return instructRepo.save(inst);
    }

    @PutMapping("/instructors/{id}")
    public Instructor updateById(@RequestBody Instructor newInstructor, @PathVariable Long id) {
        return instructRepo.findById(id)
                .map(updated -> instructRepo.save(updated.updateInstructor(newInstructor)))
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found for Id:" + id));
    }

    @DeleteMapping("/instructors/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        instructRepo.deleteById(id);
    }
}
