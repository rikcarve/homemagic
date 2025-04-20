package ch.carve.homemagic;

import ch.carve.homemagic.model.LightSwitch;
import ch.carve.homemagic.model.PaulmannRemoteAction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.io.IOException;

@Slf4j
@ApplicationScoped
public class RemoteMessaging {

    @Inject
    LightService lightService;

    @Incoming("remote")
    public void process(byte[] message) throws IOException {
        //{"action":"off","action_group":1,"linkquality":42}
        log.info(new String(message));
        PaulmannRemoteAction action = JsonbBuilder.create().fromJson(new String(message), PaulmannRemoteAction.class);
        LightSwitch lightSwitch = lightService.getList().stream()
                .filter(s -> s.getRemoteId() == action.group())
                .findFirst()
                .get();

        // if action is on/off
        switch (action.name()) {
            case "on", "off" :
                lightSwitch.setStatus(action.name().toUpperCase());
                lightSwitch.switchPower();
                break;
            case "brightness_move_down":
                lightSwitch.setBrightness(lightSwitch.getBrightness() - 10);
                lightSwitch.switchBrightness();
                break;
            case "brightness_move_up":
                lightSwitch.setBrightness(lightSwitch.getBrightness() + 10);
                lightSwitch.switchBrightness();
                break;
        }
    }
}
