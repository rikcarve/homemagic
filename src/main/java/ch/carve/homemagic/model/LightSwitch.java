package ch.carve.homemagic.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LightSwitch {
    private String id;
    private String name;
    private String status;
    private String ip;
    private int port;
    boolean isDimmable;
    boolean isTemperature;
    private int brightness;
    private int colorTemperature;
    private SwitchController controller;

    public void switchPower() {
        controller.setPower(this);
    }

    public void switchBrightness() {
        controller.setBrightness(this);
    }

    public void switchColorTemperature() {
        controller.setColorTemperature(this);
    }

    public void readState() {
        controller.getState(this);
    }
}
