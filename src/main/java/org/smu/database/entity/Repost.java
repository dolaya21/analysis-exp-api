package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.smu.database.key.RepostId;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Repost")
public class Repost {

    @EmbeddedId
    private RepostId id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Username", referencedColumnName = "Username"),
            @JoinColumn(name = "Social_Media", referencedColumnName = "Social_Media")
    })
    private User userEntity;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Repost_Username", referencedColumnName = "Username"),
            @JoinColumn(name = "Repost_Social_Media", referencedColumnName = "Social_Media")
    })
    private User repostUserEntity;

    @Column(name = "Repost_Time")
    private LocalDateTime repostTime;
}
