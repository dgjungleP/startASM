package startasm;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import startasm.util.ByteCodeUtils;

import java.io.IOException;
import java.lang.reflect.Proxy;

public class UserAsmCreateClass {
    public static void main(String[] args) throws IOException {
        createClassFile();

    }

    private static void createClassFile() throws IOException {
        String className = "AsmCreateClass";
        String clazzName = className.replaceAll("\\.", "/");
        String signature = "L" + clazzName + ";";
        ClassWriter writer = new ClassWriter(0);
        writer.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, clazzName, signature, Object.class.getName().replaceAll("\\.", "/"), null);
        MethodVisitor methodVisitor = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
        writer.visitEnd();
        byte[] byteCode = writer.toByteArray();
        try {
            Class loadClass = ByteCodeUtils.loadClass(className, byteCode);
            Object instance = loadClass.newInstance();
            ByteCodeUtils.savaToFile(className, byteCode);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
