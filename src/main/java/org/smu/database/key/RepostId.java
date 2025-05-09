package org.smu.database.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Embeddable
public class RepostId implements Serializable {
    @Column(name = "Social_Media")
    private String socialMedia;

    @Column(name = "Username")
    private String username;

    @Column(name = "Time")
    private LocalDateTime time;
}
