package ch.carve.homemagic.model;

public class WizMessageCreator implements MessageCreator {
    @Override
    public String createStatusMessage(LightSwitch lightSwitch) {
        String action = "OFF".equals(lightSwitch.getStatus()) ? "0" : "1";
        return "{\"method\": \"setPilot\", \"id\": 24, \"params\": {\"state\": " + action + " }}";
    }

    @Override
    public String createDimmingMessage(LightSwitch lightSwitch) {
        return "{\"method\": \"setPilot\", \"id\": 24, \"params\": {\"dimming\": " + lightSwitch.getBrightness() + "}}";
    }
}
