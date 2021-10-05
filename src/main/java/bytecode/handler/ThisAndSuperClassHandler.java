package bytecode.handler;

import bytecode.type.ClassFile;
import bytecode.type.U2;

import java.nio.ByteBuffer;

public class ThisAndSuperClassHandler implements BaseByteCodeHandler {
    @Override
    public int order() {
        return 4;
    }

    @Override
    public void read(ByteBuffer codeBuffer, ClassFile classFile) throws Exception {
        classFile.setThis_class(new U2(codeBuffer.get(), codeBuffer.get()));
        classFile.setSuper_class(new U2(codeBuffer.get(), codeBuffer.get()));
    }
}
