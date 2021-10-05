package bytecode.handler;

import bytecode.type.ClassFile;
import bytecode.type.U2;

import java.nio.ByteBuffer;

public class VersionHandler implements BaseByteCodeHandler {
    @Override
    public int order() {
        return 1;
    }

    @Override
    public void read(ByteBuffer codeBuffer, ClassFile classFile) throws Exception {
        classFile.setMinor_version(new U2(codeBuffer.get(), codeBuffer.get()));
        classFile.setMajor_version(new U2(codeBuffer.get(), codeBuffer.get()));
    }
}
