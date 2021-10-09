package myasm;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClassWriter implements ClassVisitor {
    private int version;
    private String className;
    private String access;
    private List<FieldWriter> filedVisitors = new ArrayList<>();
    private List<MethodWriter> methodVisitors = new ArrayList<>();


    @Override
    public void visit(int version, String access, String className) {
        this.version = version;
        this.access = access;
        this.className = className;
    }

    @Override
    public FiledVisitor visitField(String access, String name, String description) {
        FieldWriter writer = new FieldWriter(access, name, description);
        filedVisitors.add(writer);
        return writer;
    }

    @Override
    public MethodVisitor visitMethod(String access, String name, String description) {
        MethodWriter writer = new MethodWriter(access, name, description);
        methodVisitors.add(writer);
        return writer;
    }

    public void showClass() {
        System.out.println("Version: " + getVersion());
        System.out.println("Access: " + getAccess());
        System.out.println("Class Name: " + getClassName());
        for (FieldWriter fieldWriter : filedVisitors) {
            System.out.println(fieldWriter.getAccess() + " " +
                    fieldWriter.getDescription() + " " +
                    fieldWriter.getName() + " ");
            for (String annotation : fieldWriter.getAnnotation()) {
                System.out.println(annotation + " ");
            }
        }
        for (MethodWriter methodWriter : methodVisitors) {
            System.out.println(methodWriter.getAccess() + " " +
                    methodWriter.getName() + " " +
                    methodWriter.getDescription() +
                    " Max Stack Size: " + methodWriter.getMaxStackSize() +
                    " Max Local Size :" + methodWriter.getMaxLocalSize());

        }
    }

    public static void main(String[] args) {
        ClassWriter writer = new ClassWriter();
        writer.visit(52, "public", "myasm.User");
        FiledVisitor filedVisitor = writer.visitField("private", "name", "Ljava/lang/String;");
        filedVisitor.visitAnnotation("@Getter", true);
        MethodVisitor methodVisitor = writer.visitMethod("public", "setName", "(Ljava/lang/String)V");
        methodVisitor.visitMaxs(1, 1);
        writer.showClass();

    }
}
