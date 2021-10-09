package myasm;

public interface ClassVisitor {
    void visit(int version, String access, String className);


    FiledVisitor visitField(String access, String name, String description);

    MethodVisitor visitMethod(String access, String name, String description);

}
