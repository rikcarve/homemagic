package ch.carve.homemagic;

public class ItGwHelper {
    private static final String HEAD = "0,0,12,11125,89,26,0,4,";
    private static final String SEQ_ON = "12,4,4,12,12,4";
    private static final String  SEQ_OFF = "12,4,4,12,4,12";
    private static final String SEQ_LOW = "12,12,4,4,";
    private static final String  SEQ_HIGH = "12,4,12,4,";

    public static String createMessage(String id, Action action) {
        int sc = id.charAt(0) - 65;
        int uc = Integer.valueOf(id.substring(1)) - 1;
        String systemCodeSeq = reverseAndEncode(getBinaryString(sc));
        String unitCodeSeq = reverseAndEncode(getBinaryString(uc));
        String actionSeq = Action.ON == action ? SEQ_ON : SEQ_OFF;
        return HEAD +
                systemCodeSeq +
                unitCodeSeq +
                SEQ_HIGH +
                SEQ_LOW +
                "12," +
                actionSeq +
                ",1,32,;";
    }

    private static String getBinaryString(int i) {
        return String.format("%4s",Integer.toBinaryString(i)).replace(' ', '0');
    }

    private static String reverseAndEncode(String code) {
        String reversed = new StringBuilder(code).reverse().toString();
        return reversed.replace("1", SEQ_LOW).replace("0", SEQ_HIGH);
    }
}
