package bytecode.type;

import bytecode.util.StringUtil;

public class U1 {
    private byte[] value;

    public U1(byte b1) {
        value = new byte[]{b1};
    }

    public Integer toInt() {
        return value[0] & 0xff;
    }

    public String toHexString() {
        return StringUtil.getHexString(value);
    }
}
