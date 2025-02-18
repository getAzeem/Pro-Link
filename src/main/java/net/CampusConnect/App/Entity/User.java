package net.CampusConnect.App.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;



import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

@Data
@Document
public class User {
    @Id
    private ObjectId id;


    @NonNull
    @Indexed(unique = true)
    private String username;

    @NonNull
    private String password;

    @NonNull
    @Indexed(unique = true)
    private String email;

    private String fullName;

    private String university;
    private String degree;
    private int yearOfStudy;

    private String github;


    private String linkedin;
    private String portfolio;

    private List<String> skills;



    private List<String> projectNames;



    private List<ObjectId> projects;



    private List<ObjectId> joinedProjects;

    private List<String> roles;

    public User() {
        this.skills = new ArrayList<>(); // Initialize the skills list
        this.projectNames = new ArrayList<>(); // Initialize projectNames
        this.projects = new ArrayList<>(); // Initialize projects
        this.joinedProjects = new ArrayList<>(); // Initialize joinedProjects
        this.roles = new ArrayList<>(); // Initialize roles
    }
}
