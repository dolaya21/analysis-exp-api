package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    private String username;
    private String firstName;
    private String lastName;
    private String countryOfBirth;
    private String countryOfResidence;
    private Integer age;
    private String gender;
    private Boolean verified;
}