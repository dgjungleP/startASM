package bytecode.util;

import bytecode.handler.BaseByteCodeHandler;
import bytecode.handler.MagicHandler;
import bytecode.handler.VersionHandler;
import bytecode.type.ClassFile;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClassFileAnalysiser {
    public static final List<BaseByteCodeHandler> HANDLERS = new ArrayList<>();

    static {
        HANDLERS.add(new MagicHandler());
        HANDLERS.add(new VersionHandler());
        HANDLERS.sort(Comparator.comparing(BaseByteCodeHandler::order));
    }

    public static ClassFile analysis(ByteBuffer codeBuf) throws Exception {
        codeBuf.position(0);
        ClassFile classFile = new ClassFile();
        for (BaseByteCodeHandler handler : HANDLERS) {
            handler.read(codeBuf, classFile);
        }
        return classFile;
    }
}
