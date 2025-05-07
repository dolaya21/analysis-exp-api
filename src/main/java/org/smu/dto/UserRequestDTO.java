package org.smu.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;
    private String socialMedia;
    private String firstName;
    private String lastName;
    private String countryOfBirth;
    private String countryOfResidence;
    private Integer age;
    private String gender;
    private Boolean verified;
}
