package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    private String username;
    @Column(name = "First_Name")
    private String firstName;
    @Column(name = "Last_Name")
    private String lastName;
    @Column(name = "Country_of_Birth")
    private String countryOfBirth;
    @Column(name = "Country_of_Residence")
    private String countryOfResidence;
    private Integer age;
    private String gender;
    private Boolean verified;
}