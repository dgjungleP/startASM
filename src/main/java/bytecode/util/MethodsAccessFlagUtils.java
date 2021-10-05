package bytecode.util;

import bytecode.type.U2;

import java.util.HashMap;
import java.util.Map;

public class MethodsAccessFlagUtils {
    private static final Map<Integer, String> accessFlagMap = new HashMap<>();

    static {
        accessFlagMap.put(0x0001, "public");
        accessFlagMap.put(0x0002, "private");
        accessFlagMap.put(0x0004, "protected");
        accessFlagMap.put(0x0008, "static");
        accessFlagMap.put(0x0010, "final");
        accessFlagMap.put(0x0020, "synchronized");
        accessFlagMap.put(0x0040, "volatile");
        accessFlagMap.put(0x0080, "transient");
        accessFlagMap.put(0x0100, "native");
        accessFlagMap.put(0x0400, "abstract");
        accessFlagMap.put(0x0800, "strict");
        accessFlagMap.put(0x1000, "synthetic");
    }


    public static String toAccessFlagsString(U2 flag) {
        Integer flagValue = flag.toInt();
        StringBuilder flagBuilder = new StringBuilder();
        accessFlagMap.keySet()
                .forEach(key -> {
                    if ((flagValue & key) == key) {
                        flagBuilder.append(accessFlagMap.get(key)).append(",");
                    }
                });
        return (flagBuilder.length() > 0 && flagBuilder.charAt(flagBuilder.length() - 1) == ',') ?
                flagBuilder.substring(0, flagBuilder.length() - 1) : flagBuilder.toString();

    }
}
