package com.myapps.courseapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(name = "instructor")
@Setter
@Getter
@ToString
//
public class Instructor extends RepresentationModel<Instructor> {
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Course> courseList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instId;
    @NotBlank(message = "Provide instructor Name.")
    private String name;
    @NotBlank(message = "Provide instructor details.")
    private String about;

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public Instructor updateInstructor(final Instructor newInst) {
        this.setName(newInst.getName());
        this.setAbout(newInst.getAbout());
        return this;
    }
}
