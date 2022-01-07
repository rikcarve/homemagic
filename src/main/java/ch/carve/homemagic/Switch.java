package ch.carve.homemagic;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Switch {
    private String id;
    private String name;
    private String status;
}
