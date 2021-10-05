package bytecode.type;

import lombok.Data;

@Data
public class AttributeInfo {
    private U2 attribute_name_index;
    private U4 attribute_length;
    private byte[] info;
}
