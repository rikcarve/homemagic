package ch.carve.homemagic.model;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class FlairViyuController implements SwitchController {

    @Override
    public void setPower(LightSwitch lightSwitch) {
        String message = "{\"state\":\"" + lightSwitch.getStatus() + "\"}";
        sendMessage(message, lightSwitch.getIp(), lightSwitch.getId());
    }

    @Override
    public void setBrightness(LightSwitch lightSwitch) {

    }

    @Override
    public void setColorTemperature(LightSwitch lightSwitch) {

    }

    @Override
    public void getState(LightSwitch lightSwitch) {

    }

    private void sendMessage(String message, String uri, String topic) {
        String publisherId = MqttClient.generateClientId();
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
