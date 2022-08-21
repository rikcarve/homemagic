package ch.carve.homemagic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class Temperature {
    private Instant time;
    private double temperature;
    //private double humidity;
}
