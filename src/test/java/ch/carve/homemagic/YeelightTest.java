package ch.carve.homemagic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Disabled
public class YeelightTest {
    @Test
    void testOnOff() throws IOException {
//        String msg = "{\"id\":1,\"method\":\"set_power\",\"params\":[\"on\", \"smooth\", 500]}";
        String msg = "{\"id\":1,\"method\":\"set_bright\",\"params\":[90, \"sudden\", 0]}";
//        String msg = "{\"id\":1,\"method\":\"set_ct_abx\",\"params\":[3500, \"sudden\", 500]}";
        byte[] buffer = msg.getBytes();
        Socket socket = new Socket("192.168.178.33", 55443);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println(msg);
        System.out.println(in.readLine());
    }
}
