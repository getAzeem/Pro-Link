package net.CampusConnect.App.Controller;

import net.CampusConnect.App.Entity.Project;
import net.CampusConnect.App.Entity.User;
import net.CampusConnect.App.Service.ProjectService;
import net.CampusConnect.App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/public")
public class PublicController {


        @Autowired
        private UserService userService;

        @Autowired
        private ProjectService projectService;

        @PostMapping("/create-user")
        public ResponseEntity<String> CreateUser (@RequestBody User user){
        try {
            boolean isCreated = userService.SaveUser(user);
            if (isCreated) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }



    @GetMapping("/get-all-project")
    public ResponseEntity<List<Project>> getAllProject() {  // For Everyone
        List<Project> projects = projectService.getAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @GetMapping("/field/{pfield}")
    public ResponseEntity<List<Project>> findProjectByField(@PathVariable String pfield) {  // For Everyone
        List<Project> projects = projectService.findByPfield(pfield);  // Corrected method call
        if (projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
