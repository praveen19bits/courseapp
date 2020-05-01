package com.myapps.courseapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    @NotBlank(message="Provide course name.")
    private String name;
    @NotBlank(message="Provide description name.")
    private String description;
    @NotBlank(message="Provide instructor name.")
    private String instructorName;
    private int price;

    public Course updateCourse(final Course newCourse){
        this.setName(newCourse.getName());
        this.setDescription(newCourse.getDescription());
        this.setInstructorName(newCourse.getInstructorName());
        this.setPrice(newCourse.getPrice());
        return this;
    }
}
