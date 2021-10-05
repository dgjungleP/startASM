package bytecode.util;

import bytecode.handler.*;
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
        HANDLERS.add(new ConstantPoolHandler());
        HANDLERS.add(new AccessFlagsHandler());
        HANDLERS.add(new ThisAndSuperClassHandler());
        HANDLERS.add(new InterfacesHandler());
        HANDLERS.add(new FieldHandler());
        HANDLERS.add(new MethodsHandler());
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
