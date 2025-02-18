package net.CampusConnect.App.Service;

import net.CampusConnect.App.Entity.Project;
import net.CampusConnect.App.Entity.User;
import net.CampusConnect.App.Repository.ProjectRepository;
import net.CampusConnect.App.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    public boolean SaveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
        return true;
    }

    public boolean SaveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN"));
        userRepository.save(user);
        return true;
    }



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean updateGithub(String username, String newGithub) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            String sanitizedGithub = sanitizeInput(newGithub);
            user.setGithub(sanitizedGithub);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean updateLinkedin(String username, String newLinkedin) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            String sanitizedLinkedin = sanitizeInput(newLinkedin);
            user.setLinkedin(sanitizedLinkedin);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean updatePortfolio(String username, String newPortfolio) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            String sanitizedPortfolio = sanitizeInput(newPortfolio);
            user.setPortfolio(sanitizedPortfolio);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean addSkill(String username, String skill) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            String sanitizedSkill = sanitizeInput(skill);

            if (!user.getSkills().contains(sanitizedSkill)) {
                user.getSkills().add(sanitizedSkill);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public boolean removeSkill(String username, String skill) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            String sanitizedSkill = sanitizeInput(skill);

            List<String> updatedSkills = user.getSkills().stream()
                    .map(this::sanitizeInput)
                    .filter(s -> !s.equalsIgnoreCase(sanitizedSkill))
                    .collect(Collectors.toList());

            if (updatedSkills.size() != user.getSkills().size()) {
                user.setSkills(updatedSkills);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private String sanitizeInput(String input) {
        return input == null ? "" : input.trim().replaceAll("[\r\n]+", "");
    }





    public boolean removeUser(String username) {
            // Find the user by username
            User user = userRepository.findByUsername(username);
            if (user != null) {
                // Remove the user from the projects they own and delete those projects
                for (String projectName : user.getProjectNames()) {
                    // Find the project by name
                    Project project = projectRepository.findByPname(projectName);
                    if (project != null) {
                        // Delete the project from the repository
                        projectRepository.delete(project);
                    }
                }

                // Remove the user from joined projects
                for (ObjectId joinedProjectId : user.getJoinedProjects()) {
                    // Remove the user from any joined project
                    projectService.removeUserFromProject(joinedProjectId, username);
                }

                // Delete the user from the repository
                userRepository.delete(user);
                return true;
            }
            return false;
        }





    }





