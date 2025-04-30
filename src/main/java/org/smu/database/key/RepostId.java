package org.smu.database.key;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RepostId implements Serializable {
    private String socialMedia;
    private String user;
    private LocalDateTime time;
}
