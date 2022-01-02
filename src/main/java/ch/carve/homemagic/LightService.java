package ch.carve.homemagic;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

@ApplicationScoped
public class LightService {

    private static final String IGTW = "192.168.178.32";
    private static final int IGTW_PORT = 49880;
    private String currentStatus = "OFF";

    @Traced
    @Retry(maxRetries = 1)
    public List<Switch> getList() {
        return List.of(
                new Switch("B3", "Schreibtisch", currentStatus),
                new Switch("A2", "Esszimmer", currentStatus)
        );
    }

    @Traced
    @Retry(maxRetries = 1)
    public void toggle(String id, String status) {
        byte[] buffer = ItGwHelper.createMessage(id, Action.valueOf(status)).getBytes();
        try {
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket udpPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(IGTW), IGTW_PORT);
            socket.send(udpPacket);
            currentStatus = status;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
