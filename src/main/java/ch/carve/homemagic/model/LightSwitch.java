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
    private int remoteId;
    boolean isDimmable;
    boolean isTemperature;
    boolean isColor;
    /**
     * Brightness in range of 0..100
     */
    private int brightness;
    /**
     * Color temperature in Kelvin (2700..6500)
     */
    private int colorTemperature;
    /**
     * Color hue value (0..360)
     */
    private int colorHue;
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

    public void switchColorHue() {
        controller.setColorHue(this);
    }

    public void readState() {
        controller.getState(this);
    }
}
