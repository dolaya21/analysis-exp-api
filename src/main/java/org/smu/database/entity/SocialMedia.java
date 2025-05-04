package org.smu.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Social_Media")
public class SocialMedia {
    @Id
    private String name;
}
