package bytecode.handler;

import bytecode.type.ClassFile;
import bytecode.type.U2;

import java.nio.ByteBuffer;

public class InterfacesHandler implements BaseByteCodeHandler {
    @Override
    public int order() {
        return 5;
    }

    @Override
    public void read(ByteBuffer codeBuffer, ClassFile classFile) throws Exception {
        classFile.setInterfaces_count(new U2(codeBuffer.get(), codeBuffer.get()));
        Integer interfaces_count = classFile.getInterfaces_count().toInt();
        U2[] interfaces = new U2[interfaces_count];
        classFile.setInterfaces(interfaces);
        for (int i = 0; i < interfaces_count; i++) {
            interfaces[i] = new U2(codeBuffer.get(), codeBuffer.get());
        }
    }
}
