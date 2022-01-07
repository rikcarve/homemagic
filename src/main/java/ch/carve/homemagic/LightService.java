package ch.carve.homemagic;

import ch.carve.homemagic.model.IntertechnoMessageCreator;
import ch.carve.homemagic.model.LightSwitch;
import ch.carve.homemagic.model.WizMessageCreator;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.opentracing.Traced;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class LightService {

    private static final String ITGW = "192.168.178.32";
    private static final int ITGW_PORT = 49880;
    private String currentStatus = "OFF";

    private Map<String, LightSwitch> lights = new HashMap<>();

    @PostConstruct
    public void init() {
        lights.put("B3", LightSwitch.builder()
                .id("B3")
                .name("Schreibtisch")
                .status("OFF")
                .ip(ITGW)
                .port(ITGW_PORT)
                .messageCreator(new IntertechnoMessageCreator())
                .build());
        lights.put("W1", LightSwitch.builder()
                .id("W1")
                .name("Schlafzimmer")
                .status("OFF")
                .ip("192.168.178.34")
                .port(38899)
                .brightness(100)
                .colorTemperature(3000)
                .isDimmable(true)
                .isTemperature(true)
                .messageCreator(new WizMessageCreator())
                .build());
    }

    public LightSwitch get(String id) {
        return lights.get(id);
    }

    @Traced
    @Retry(maxRetries = 1)
    public Collection<LightSwitch> getList() {
        return lights.values();
    }

    @Traced
    @Retry(maxRetries = 1)
    public void toggle(String id, String status) {
        LightSwitch lightSwitch = get(id);
        lightSwitch.setStatus(status);
        String message = lightSwitch.getMessageCreator().createStatusMessage(lightSwitch);
        UdpSender.sendMessage(message, lightSwitch.getIp(), lightSwitch.getPort());
    }

}
