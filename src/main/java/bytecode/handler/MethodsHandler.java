package bytecode.handler;

import bytecode.type.*;

import java.nio.ByteBuffer;

public class MethodsHandler implements BaseByteCodeHandler {
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
            if (info.getAttribute_count().toInt() == 0) {
                continue;
            }
            AttributeInfo[] attributeInfos = new AttributeInfo[info.getAttribute_count().toInt()];
            info.setAttributes(attributeInfos);
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
        }

    }
}
