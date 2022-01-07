package ch.carve.homemagic.model;

public interface MessageCreator {
    String createStatusMessage(LightSwitch lightSwitch);
    String createDimmingMessage(LightSwitch lightSwitch);
    String createTemperatureMessage(LightSwitch lightSwitch);
}
