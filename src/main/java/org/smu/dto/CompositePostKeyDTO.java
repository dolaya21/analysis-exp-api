package org.smu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompositePostKeyDTO {
    private String username;
    private String socialMedia;
    private LocalDateTime time;
}
