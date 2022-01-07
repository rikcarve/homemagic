package ch.carve.homemagic.model;

import ch.carve.homemagic.Action;
import ch.carve.homemagic.ItGwHelper;

public class IntertechnoMessageCreator implements MessageCreator {
    @Override
    public String createStatusMessage(LightSwitch lightSwitch) {
        return ItGwHelper.createMessage(lightSwitch.getId(), Action.valueOf(lightSwitch.getStatus()));
    }

    @Override
    public String createDimmingMessage(LightSwitch lightSwitch) {
        return null;
    }
}
