package bytecode.handler;

import bytecode.type.AttributeInfo;
import bytecode.type.ClassFile;
import bytecode.type.U2;
import bytecode.type.U4;

import java.nio.ByteBuffer;

public class AttributesHandler extends AbstractAttributeHandler {
    @Override
    public int order() {
        return 8;
    }

    @Override
    public void read(ByteBuffer codeBuffer, ClassFile classFile) throws Exception {
        classFile.setAttributes_count(new U2(codeBuffer.get(), codeBuffer.get()));
        Integer length = classFile.getAttributes_count().toInt();
        if (length == 0) {
            return;
        }
        AttributeInfo[] attributeInfos = readAttribute(codeBuffer, length);
        classFile.setAttributes(attributeInfos);
    }


}
