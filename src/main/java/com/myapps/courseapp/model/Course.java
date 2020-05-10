package com.myapps.courseapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.myapps.courseapp.controller.InstructorController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Entity
@Table(name = "course")
@Setter
@Getter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "courseId")
public class Course extends RepresentationModel<Course> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    @NotBlank(message = "Provide course name.")
    private String name;
    @NotBlank(message = "Provide description name.")
    private String description;
    private int price;

    @ManyToOne
    @JoinColumn(name = "inst_id")
    @JsonBackReference
    private Instructor instructor;

    public Course() {

    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Course updateCourse(final Course newCourse) {
        this.setName(newCourse.getName());
        this.setDescription(newCourse.getDescription());
        this.setPrice(newCourse.getPrice());
        return this;
    }

    public Course addInstructorLink() {
        if (getInstructor() != null) {
            Link instLink = linkTo(methodOn(InstructorController.class)
                    .getInstructorById(getInstructor().getInstId())).withRel("instructors");
            add(instLink);
        }
        return this;
    }
}
