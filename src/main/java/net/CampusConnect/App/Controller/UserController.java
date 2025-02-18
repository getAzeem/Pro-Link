package net.CampusConnect.App.Controller;

import net.CampusConnect.App.Entity.User;
import net.CampusConnect.App.Service.ProjectService;
import net.CampusConnect.App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    ProjectService projectService;

    @GetMapping("/get-user-by-username")
    public ResponseEntity<User> GetUserByUsername () {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/github")
    public ResponseEntity<String> updateGithub( @RequestBody String newGithub) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

            boolean updated = userService.updateGithub(username, newGithub);

            if (updated) {
                return ResponseEntity.ok("GitHub link updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }


    }

    @PutMapping("linkedin")
    public ResponseEntity<String> updateLinkedin( @RequestBody String newLinkedin) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean updated = userService.updateLinkedin(username, newLinkedin);
        if (updated) {
            return ResponseEntity.ok("LinkedIn link updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("portfolio")
    public ResponseEntity<String> updatePortfolio( @RequestBody String newPortfolio) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean updated = userService.updatePortfolio(username, newPortfolio);
        if (updated) {
            return ResponseEntity.ok("Portfolio link updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("addSkill")
    public ResponseEntity<String> addSkill( @RequestBody String skill) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean updated = userService.addSkill(username, skill);
        if (updated) {
            return ResponseEntity.ok("Skill added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or skill already exists");
        }
    }

    @PutMapping("removeSkill")
    public ResponseEntity<String> removeSkill( @RequestBody String skill) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean updated = userService.removeSkill(username, skill);
        if (updated) {
            return ResponseEntity.ok("Skill removed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or skill not found");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isRemoved = userService.removeUser(username);
        if (isRemoved) {
            return ResponseEntity.status(HttpStatus.OK).body("User removed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }


}