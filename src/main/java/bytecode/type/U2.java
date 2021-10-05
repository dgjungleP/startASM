package bytecode.type;


import bytecode.util.StringUtil;

public class U2 {
    private byte[] value;

    public U2(byte b1, byte b2) {
        value = new byte[]{b1, b2};
    }

    public Integer toInt() {
        return (value[0] & 0xff) << 8 | (value[1] & 0xff);
    }

    public String toHexString() {
        return StringUtil.getHexString(value);
    }
}
