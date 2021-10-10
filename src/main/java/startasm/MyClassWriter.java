package startasm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MyClassWriter extends ClassVisitor {
    private ClassWriter classWriter;

    public MyClassWriter(ClassWriter classWriter) {
        super(Opcodes.ASM6, classWriter);
        this.classWriter = classWriter;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if ("main".equalsIgnoreCase(name)) {
            return new MainMethodWriter(methodVisitor);
        }
        return methodVisitor;
    }

    public byte[] toByteArray() {
        return classWriter.toByteArray();
    }
}
