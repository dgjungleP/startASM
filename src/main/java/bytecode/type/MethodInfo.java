package bytecode.type;

import lombok.Data;

@Data
public class MethodInfo {
    private U2 access_flags;
    private U2 name_index;
    private U2 description_index;
    private U2 attribute_count;
    private AttributeInfo[] attributes;
}
