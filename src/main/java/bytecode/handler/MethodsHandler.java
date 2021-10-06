package bytecode.handler;

import bytecode.type.*;

import java.nio.ByteBuffer;

public class MethodsHandler extends AbstractAttributeHandler {
    @Override
    public int order() {
        return 7;
    }

    @Override
    public void read(ByteBuffer codeBuffer, ClassFile classFile) throws Exception {
        U2 method_count = new U2(codeBuffer.get(), codeBuffer.get());
        classFile.setMethods_count(method_count);
        if (method_count.toInt() == 0) {
            return;
        }
        MethodInfo[] methodInfos = new MethodInfo[method_count.toInt()];
        classFile.setMethods(methodInfos);
        for (int i = 0; i < method_count.toInt(); i++) {
            MethodInfo info = new MethodInfo();
            methodInfos[i] = info;
            info.setAccess_flags(new U2(codeBuffer.get(), codeBuffer.get()));
            info.setName_index(new U2(codeBuffer.get(), codeBuffer.get()));
            info.setDescription_index(new U2(codeBuffer.get(), codeBuffer.get()));
            info.setAttribute_count(new U2(codeBuffer.get(), codeBuffer.get()));
            Integer length = info.getAttribute_count().toInt();
            if (length == 0) {
                continue;
            }
            AttributeInfo[] attributeInfos = readAttribute(codeBuffer, length);
            info.setAttributes(attributeInfos);
        }

    }
}
