package ch.carve.homemagic.model;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
            PrintWriter out = new PrintWriter(socket.getOutputStream(), false, StandardCharsets.UTF_8);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.print(message + "\r\n");
            out.flush();
            String response = in.readLine();
            log.info("Response: " + response);
            socket.close();
        } catch (Exception e) {
            log.warn("Could not send msg: {}", message, e);
        }
    }
}
