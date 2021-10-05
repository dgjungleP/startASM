package bytecode.handler;

import bytecode.type.ClassFile;
import bytecode.type.U4;

import java.nio.ByteBuffer;

public class MagicHandler implements BaseByteCodeHandler {
    @Override
    public int order() {
        return 0;
    }

    @Override
    public void read(ByteBuffer codeBuffer, ClassFile classFile) throws Exception {
        classFile.setMagic(new U4(codeBuffer.get(), codeBuffer.get(), codeBuffer.get(), codeBuffer.get()));
        if (!"0xCAFEBABE".equalsIgnoreCase(classFile.getMagic().toHexString())) {
            throw new Exception("This is not a  class file .");
        }
    }
}
