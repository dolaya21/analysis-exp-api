package org.smu.database.key;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Embeddable
public class RepostId implements Serializable {
    private String socialMedia;
    private String user;
    private LocalDateTime time;
}
