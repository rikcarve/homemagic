package ch.carve.homemagic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItGwHelperTest {
    private static final String MSG_ON = "0,0,12,11125,89,26,0,4,12,4,12,4,12,4,12,4,12,4,12,4,12,4,12,4,12,12,4,4,12,4,12,4,12,4,12,4,12,4,12,4,12,4,12,4,12,12,4,4,12,12,4,4,12,12,4,1,32,;";
    private static final String MSG_OFF = "0,0,12,11125,89,26,0,4,12,4,12,4,12,4,12,4,12,4,12,4,12,4,12,4,12,12,4,4,12,4,12,4,12,4,12,4,12,4,12,4,12,4,12,4,12,12,4,4,12,12,4,4,12,4,12,1,32,;";

    @Test
    void testCreateMessage_on() {
        assertEquals(MSG_ON, ItGwHelper.createMessage("A2", Action.ON));
        assertEquals(MSG_OFF, ItGwHelper.createMessage("A2", Action.OFF));
    }
}