package bytecode.handler;

import bytecode.type.AttributeInfo;
import bytecode.type.U2;
import bytecode.type.U4;

import java.nio.ByteBuffer;

public abstract class AbstractAttributeHandler implements BaseByteCodeHandler {
    public AttributeInfo[] readAttribute(ByteBuffer codeBuffer, Integer length) {
        AttributeInfo[] attributeInfos = new AttributeInfo[length];
        for (int j = 0; j < attributeInfos.length; j++) {
            AttributeInfo attributeInfo = new AttributeInfo();
            attributeInfos[j] = attributeInfo;
            attributeInfo.setAttribute_name_index(new U2(codeBuffer.get(), codeBuffer.get()));
            attributeInfo.setAttribute_length(new U4(codeBuffer.get(), codeBuffer.get(), codeBuffer.get(), codeBuffer.get()));
            Integer attributeLength = attributeInfo.getAttribute_length().toInt();
            byte[] baseInfo = new byte[attributeLength];
            codeBuffer.get(baseInfo, 0, attributeLength);
            attributeInfo.setInfo(baseInfo);
        }
        return attributeInfos;
    }
}
