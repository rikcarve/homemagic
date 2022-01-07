package ch.carve.homemagic;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
public class UdpSender {
    public static void sendMessage(String message, String host, int port) {
        byte[] buffer = message.getBytes();
        try {
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket udpPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(host), port);
            socket.send(udpPacket);
        } catch (IOException e) {
            log.warn("Send failed, msg {}", message, e);
        }
    }
}
