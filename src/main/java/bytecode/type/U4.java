package bytecode.type;

import bytecode.util.StringUtil;

public class U4 {
    private byte[] value;

    public U4(byte b1, byte b2, byte b3, byte b4) {
        value = new byte[]{b1, b2, b3, b4};
    }

    public Integer toInt() {
        return (value[0] & 0xff) << 24 | (value[1] & 0xff) << 16 | (value[2] & 0xff) << 8 | (value[3] & 0xff);
    }

    public String toHexString() {
        return StringUtil.getHexString(value);
    }


}
