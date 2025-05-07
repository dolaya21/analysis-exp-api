package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.smu.database.key.UserId;

@Entity
@Table(name = "User")
@Data
public class User {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "username", column = @Column(name = "Username")),
            @AttributeOverride(name = "socialMedia", column = @Column(name = "Social_Media"))
    })
    private UserId id;

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

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean verified = false;

    @ManyToOne
    @JoinColumn(name = "Social_Media", insertable = false, updatable = false)
    private SocialMedia socialMediaEntity;
}
