package ch.carve.homemagic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Switch {
    private String id;
    private String name;
    private String status;
}
