package bytecode.util;

import bytecode.type.CpInfo;
import bytecode.type.U2;
import bytecode.type.constant.CONSTANT_Class_info;

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

    public static String getClassName(U2 classTYpe, CpInfo[] constantPool) {
        CONSTANT_Class_info thisClassInfo = (CONSTANT_Class_info) constantPool[classTYpe.toInt() - 1];
        CpInfo thisClassName = constantPool[thisClassInfo.getName_index().toInt() - 1];
        return thisClassName.toString();
    }

    public static String getFieldName(U2 nameIndex, CpInfo[] constantPool) {
        CpInfo info = constantPool[nameIndex.toInt() - 1];
        return info.toString();
    }
}
