package startasm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import startasm.interfaces.SayHelloInterface;
import startasm.util.ByteCodeUtils;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class UseAsmOverrideMethod {
    public static void main(String[] args) throws IOException {
        String className = BaseClass.class.getSimpleName().replaceAll("Base", "Sub");
        ClassWriter classWriter = new ClassWriter(0);
        classWriter.visit(V1_8, ACC_PUBLIC,
                className.replaceAll("\\.", "/"),
                null,
                Type.getInternalName(BaseClass.class),
                null);
        MethodVisitor sayHello = classWriter.visitMethod(ACC_PUBLIC, "sayHello", "()V", null, null);
        sayHello.visitFieldInsn(Opcodes.GETSTATIC, Type.getInternalName(System.class), "out", Type.getDescriptor(System.out.getClass()));
        sayHello.visitLdcInsn("Sub say:hello world!");
        sayHello.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(System.out.getClass())
                , "println",
                "(Ljava/lang/String;)V", false);
        sayHello.visitInsn(RETURN);
        sayHello.visitMaxs(2, 1);
        byte[] codeByte = classWriter.toByteArray();
        ByteCodeUtils.savaToFile(className, codeByte);
    }
}
