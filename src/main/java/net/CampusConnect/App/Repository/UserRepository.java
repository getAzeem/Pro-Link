package net.CampusConnect.App.Repository;

import net.CampusConnect.App.Entity.Project;
import net.CampusConnect.App.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    List<User> findByProjectsContaining(Project project);
    List<User> findByProjectNamesContaining(String projectName);

    User findByUsername(String ownerUsername);

}

