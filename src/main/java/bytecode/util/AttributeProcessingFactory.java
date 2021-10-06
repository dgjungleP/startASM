package bytecode.util;

import bytecode.type.*;

import java.nio.ByteBuffer;

public class AttributeProcessingFactory {
    public static ConstantValue_attribute processingConstantValue(AttributeInfo attributeInfo) {
        ConstantValue_attribute attribute = new ConstantValue_attribute();
        attribute.setAttribute_name_index(attributeInfo.getAttribute_name_index());
        attribute.setAttribute_length(attributeInfo.getAttribute_length());
        attribute.setConstantValue_index(new U2(attributeInfo.getInfo()[0], attributeInfo.getInfo()[1]));
        return attribute;
    }

    public static CodeAttribute processingCode(AttributeInfo attributeInfo) {
        CodeAttribute codeAttribute = new CodeAttribute();
        codeAttribute.setAttribute_name_index(attributeInfo.getAttribute_name_index());
        codeAttribute.setAttribute_length(attributeInfo.getAttribute_length());
        ByteBuffer buffer = ByteBuffer.wrap(attributeInfo.getInfo());
        codeAttribute.setMax_stack(new U2(buffer.get(), buffer.get()));
        codeAttribute.setMax_locals(new U2(buffer.get(), buffer.get()));
        codeAttribute.setCode_length(new U4(buffer.get(), buffer.get(), buffer.get(), buffer.get()));
        byte[] byteCode = new byte[codeAttribute.getCode_length().toInt()];
        buffer.get(byteCode, 0, byteCode.length);
        codeAttribute.setCode(byteCode);
        return codeAttribute;
    }
}
