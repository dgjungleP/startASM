package bytecode.type;

import lombok.Data;

@Data
public class ConstantValue_attribute {
    private U2 attribute_name_index;
    private U4 attribute_length;
    private U2 constantValue_index;

}
