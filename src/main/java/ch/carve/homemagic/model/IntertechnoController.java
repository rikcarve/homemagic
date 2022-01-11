package ch.carve.homemagic.model;

import ch.carve.homemagic.Action;
import ch.carve.homemagic.ItGwHelper;
import ch.carve.homemagic.UdpSender;

public class IntertechnoController implements SwitchController {
    @Override
    public void setPower(LightSwitch lightSwitch) {
        String msg = ItGwHelper.createMessage(lightSwitch.getId(), Action.valueOf(lightSwitch.getStatus()));
        UdpSender.sendMessage(msg, lightSwitch.getIp(), lightSwitch.getPort());
    }

    @Override
    public void setBrightness(LightSwitch lightSwitch) {
        throw new UnsupportedOperationException("Not supported on Intertechno gateway");
    }

    @Override
    public void setColorTemperature(LightSwitch lightSwitch) {
        throw new UnsupportedOperationException("Not supported on Intertechno gateway");
    }

    @Override
    public void getState(LightSwitch lightSwitch) {
    }
}
