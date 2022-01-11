package ch.carve.homemagic.model;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class YeelightController implements SwitchController {
    @Override
    public void setPower(LightSwitch lightSwitch) {
        String power = lightSwitch.getStatus().toLowerCase();
        String msg = "{\"id\":1,\"method\":\"set_power\",\"params\":[\"" + power + "\", \"sudden\", 500]}";
        sendMessage(msg, lightSwitch.getIp(), lightSwitch.getPort());
    }

    @Override
    public void setBrightness(LightSwitch lightSwitch) {
        String msg = "{\"id\":1,\"method\":\"set_bright\",\"params\":[" + lightSwitch.getBrightness() +", \"sudden\", 0]}";
        sendMessage(msg, lightSwitch.getIp(), lightSwitch.getPort());
    }

    @Override
    public void setColorTemperature(LightSwitch lightSwitch) {
        String msg = "{\"id\":1,\"method\":\"set_ct_abx\",\"params\":[" + lightSwitch.getColorTemperature() + ", \"sudden\", 500]}";
        sendMessage(msg, lightSwitch.getIp(), lightSwitch.getPort());
    }

    @Override
    public void getState(LightSwitch lightSwitch) {

    }

    private void sendMessage(String message, String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(message);
            String response = in.readLine();
            log.info("Response: " + response);
        } catch (Exception e) {
            log.warn("Could not send msg: {}", message, e);
        }
    }
}
