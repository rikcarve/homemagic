package ch.carve.homemagic.model;

import ch.carve.homemagic.UdpSender;

public class WizController implements SwitchController {
    @Override
    public void setPower(LightSwitch lightSwitch) {
        String action = "OFF".equals(lightSwitch.getStatus()) ? "0" : "1";
        String msg = "{\"method\": \"setPilot\", \"id\": 42, \"params\": {\"state\": " + action + " }}";
        UdpSender.sendMessage(msg, lightSwitch.getIp(), lightSwitch.getPort());
    }

    @Override
    public void setBrightness(LightSwitch lightSwitch) {
        String msg = "{\"method\": \"setPilot\", \"id\": 42, \"params\": {\"dimming\": " + lightSwitch.getBrightness() + "}}";
        UdpSender.sendMessage(msg, lightSwitch.getIp(), lightSwitch.getPort());
    }

    @Override
    public void setColorTemperature(LightSwitch lightSwitch) {
        String msg = "{\"method\": \"setPilot\", \"id\": 42, \"params\": {\"temp\": " + lightSwitch.getColorTemperature() + "}}";
        UdpSender.sendMessage(msg, lightSwitch.getIp(), lightSwitch.getPort());
    }

    @Override
    public void getState(LightSwitch lightSwitch) {
        // not implemented yet
    }
}
