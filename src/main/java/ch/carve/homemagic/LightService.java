package ch.carve.homemagic;

import ch.carve.homemagic.model.*;
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
                .controller(new IntertechnoController())
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
                .controller(new WizController())
                .build());
        lights.put("Y1", LightSwitch.builder()
                .id("Y1")
                .name("Wohnzimmer")
                .status("OFF")
                .ip("192.168.178.33")
                .port(55443)
                .brightness(100)
                .colorTemperature(3000)
                .isDimmable(true)
                .isTemperature(true)
                .controller(new YeelightController())
                .build());
        lights.put("workplaceRGB", LightSwitch.builder()
                .id("workplaceRGB")
                .name("Arbeitsplatz")
                .ip("tcp://berry2:1883")
                .status("OFF")
                .brightness(100)
                .colorTemperature(3000)
                .isDimmable(false)
                .isTemperature(false)
                .controller(new FlairViyuController())
                .build());
    }

    public LightSwitch get(String id) {
        return lights.get(id);
    }

    public Collection<LightSwitch> getList() {
        return lights.values();
    }

    @Traced
    public void toggle(String id, String status) {
        LightSwitch lightSwitch = get(id);
        lightSwitch.setStatus(status);
        lightSwitch.switchPower();
    }

}
