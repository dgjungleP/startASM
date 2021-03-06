package bytecode.util;

import bytecode.type.U2;

import java.util.HashMap;
import java.util.Map;

public class ClassAccessFlagUtils {
    private static final Map<Integer, String> accessFlagMap = new HashMap<>();

    static {
        accessFlagMap.put(0x0001, "public");
        accessFlagMap.put(0x0010, "final");
        accessFlagMap.put(0x0020, "super");
        accessFlagMap.put(0x0200, "interface");
        accessFlagMap.put(0x0400, "abstract");
        accessFlagMap.put(0x1000, "synthetic");
        accessFlagMap.put(0x2000, "annotation");
        accessFlagMap.put(0x4000, "enum");
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
