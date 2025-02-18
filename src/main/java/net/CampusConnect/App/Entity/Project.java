package net.CampusConnect.App.Entity;

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

import java.util.List;

@Component
@Document(collection = "Project")
@NoArgsConstructor
@Data
public class Project {
    @Id
    private ObjectId pid;

    @NonNull
    @Indexed(unique = true)
    private String pname;

    @NonNull
    private List<String> skills;

    private String pfield;
    private String p_purpose;
    private int teamSize;
    private List<String> sponsors;


    private ObjectId owner;  // Store ObjectId of the owner instead of DBRef

    private List<ObjectId> contributor;  // Store ObjectIds of contributors instead of DBRef
}
