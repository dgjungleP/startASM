package startasm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MainMethodWriter extends MethodVisitor {
    private MethodVisitor methodVisitor;

    public MainMethodWriter(MethodVisitor methodVisitor) {
        super(Opcodes.ASM6, methodVisitor);
        this.methodVisitor = methodVisitor;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        addSout();
    }

    private void addSout() {
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, Type.getInternalName(System.class), "out", Type.getDescriptor(System.out.getClass()));
        methodVisitor.visitLdcInsn("hello world!");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(System.out.getClass())
                , "println",
                "(Ljava/lang/String;)V", false);
    }

    @Override
    public void visitInsn(int opcode) {
        if (opcode == Opcodes.RETURN) {
            addSout();
        }
        super.visitInsn(opcode);
    }
}
