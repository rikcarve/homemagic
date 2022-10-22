package ch.carve.homemagic.model;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.awt.*;

@Slf4j
public class FlairViyuController implements SwitchController {

    @Override
    public void setPower(LightSwitch lightSwitch) {
        String message = "{\"state\":\"" + lightSwitch.getStatus() + "\"}";
        sendMessage(message, lightSwitch.getIp(), lightSwitch.getId());
    }

    @Override
    public void setBrightness(LightSwitch lightSwitch) {
        int brightness = Math.round(lightSwitch.getBrightness() * 2.55f);
        String message = "{\"brightness\":\"" + brightness + "\"}";
        sendMessage(message, lightSwitch.getIp(), lightSwitch.getId());
    }

    @Override
    public void setColorTemperature(LightSwitch lightSwitch) {
        int colorTemp = Math.round((1 - ((lightSwitch.getColorTemperature() - 2700) / 3800f)) * 350 + 150);
        String message = "{\"color_temp\":\"" + colorTemp + "\"}";
        sendMessage(message, lightSwitch.getIp(), lightSwitch.getId());
    }

    @Override
    public void setColorHue(LightSwitch lightSwitch) {
        String color = Integer.toHexString(Color.HSBtoRGB(lightSwitch.getColorHue() / 360f, 1f, 1f));
        String message = "{\"color\":{\"hex\":\"#" + color.substring(2) + "\"}}";
        sendMessage(message, lightSwitch.getIp(), lightSwitch.getId());

    }

    @Override
    public void getState(LightSwitch lightSwitch) {

    }

    private void sendMessage(String message, String uri, String topic) {
        String publisherId = MqttClient.generateClientId();
        log.info(message);
        try {
            IMqttClient mqttClient = new MqttClient(uri, publisherId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            mqttClient.connect(options);
            mqttClient.publish("zigbee2mqtt/" + topic + "/set", new MqttMessage(message.getBytes()));
            mqttClient.disconnect();
            mqttClient.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

    }
}
