package myasm;

import lombok.Getter;

import java.util.List;

@Getter
public class MethodWriter implements MethodVisitor {
    private String access;
    private String name;
    private String description;
    private int maxStackSize;
    private int maxLocalSize;

    public MethodWriter(String access, String name, String description) {
        this.access = access;
        this.name = name;
        this.description = description;
    }

    @Override
    public void visitMaxs(int maxStack, int makLocalSize) {
        this.maxLocalSize = makLocalSize;
        this.maxStackSize = maxStack;
    }
}
