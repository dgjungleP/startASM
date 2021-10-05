package bytecode.handler;

import bytecode.type.ClassFile;

import java.nio.ByteBuffer;

public interface BaseByteCodeHandler {

    int order();

    void read(ByteBuffer codeBuffer, ClassFile classFile) throws Exception;
}
