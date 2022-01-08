package ch.carve.homemagic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Disabled
public class WizTest {
    @Test
    void testOnOff() throws IOException {
//        String msg = "{\"method\": \"setPilot\", \"id\": 24, \"params\": {\"state\": 1}}";
//        String msg = "{\"method\": \"setPilot\", \"id\": 24, \"params\": {\"dimming\": 20}}";
        String msg = "{\"method\": \"setPilot\", \"id\": 24, \"params\": {\"temp\": 3000}}";
        byte[] buffer = msg.getBytes();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket udpPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("192.168.178.34"), 38899);
        socket.send(udpPacket);
    }
}
