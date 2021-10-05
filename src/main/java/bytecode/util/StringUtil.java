package bytecode.util;

public class StringUtil {
    public static String getHexString(byte[] value) {
        char[] hexChar = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        StringBuilder builder = new StringBuilder();
        for (int i = value.length - 1; i >= 0; i--) {
            int v = value[i] & 0xff;
            while (v > 0) {
                int c = v % 16;
                v = v >>> 4;
                builder.insert(0, hexChar[c]);
            }
            if ((builder.length() & 0x01) == 1) {
                builder.insert(0, "0");
            }
        }
        return "0x" + (builder.length() == 0 ? "00" : builder.toString());
    }
}
