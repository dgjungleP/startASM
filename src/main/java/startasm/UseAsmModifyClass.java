package startasm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import startasm.util.ByteCodeUtils;

import java.io.IOException;
import java.net.CacheRequest;

public class UseAsmModifyClass {
    public static void main(String[] args) throws IOException {
        String className = UseAsmModifyClass.class.getName();
        ClassReader classReader = new ClassReader(className);
        ClassWriter classWriter = new ClassWriter(0);
        classReader.accept(new MyClassWriter(classWriter), 0);
        UserAsmCreateClass.generateField(classWriter);
        byte[] byteCode = classWriter.toByteArray();
        ByteCodeUtils.savaToFile(className, byteCode);
    }
}
