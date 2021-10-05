package bytecode.util;

import bytecode.type.U2;

import java.util.HashMap;
import java.util.Map;

public class ClassAccessFlagUtils {
    private static final Map<Integer, String> classAccessFlagMap = new HashMap<>();

    static {
        classAccessFlagMap.put(0x0001, "public");
        classAccessFlagMap.put(0x0010, "final");
        classAccessFlagMap.put(0x0020, "super");
        classAccessFlagMap.put(0x0200, "interface");
        classAccessFlagMap.put(0x0400, "abstract");
        classAccessFlagMap.put(0x1000, "synthetic");
        classAccessFlagMap.put(0x2000, "annotation");
        classAccessFlagMap.put(0x4000, "enum");
    }


    public static String toClassAccessFlagsString(U2 flag) {
        Integer flagValue = flag.toInt();
        StringBuilder flagBuilder = new StringBuilder();
        classAccessFlagMap.keySet()
                .forEach(key -> {
                    if ((flagValue & key) == key) {
                        flagBuilder.append(classAccessFlagMap.get(key)).append(",");
                    }
                });
        return (flagBuilder.length() > 0 && flagBuilder.charAt(flagBuilder.length() - 1) == ',') ?
                flagBuilder.substring(0, flagBuilder.length() - 1) : flagBuilder.toString();

    }
}
