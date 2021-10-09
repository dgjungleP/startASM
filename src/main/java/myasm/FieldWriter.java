package myasm;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FieldWriter implements FiledVisitor {
    private String access;
    private String name;
    private String description;
    private List<String> annotation;

    public FieldWriter(String access, String name, String description) {
        this.access = access;
        this.name = name;
        this.description = description;
        this.annotation = new ArrayList<>();
    }

    @Override
    public void visitAnnotation(String annotation, boolean runtime) {
        this.annotation.add("Annotation:" + annotation + ", " + runtime);
    }
}
