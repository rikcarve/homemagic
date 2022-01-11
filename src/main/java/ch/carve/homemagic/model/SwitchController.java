package ch.carve.homemagic.model;

public interface SwitchController {
    void setPower(LightSwitch lightSwitch);
    void setBrightness(LightSwitch lightSwitch);
    void setColorTemperature(LightSwitch lightSwitch);
    void getState(LightSwitch lightSwitch);
}
