package jdkproxy;

import org.objectweb.asm.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.objectweb.asm.Opcodes.*;

public class MyProxyFactory {
    public static byte[] createProxyClass(String className, Class<?>[] interfaces) {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classWriter.visit(V1_8, ACC_PUBLIC, className, null, Type.getInternalName(MyProxy.class), getInternalNames(interfaces));
        createInitMethod(classWriter);
        for (Class<?> interfaceClass : interfaces) {
            implInterFaceMethod(classWriter, className, interfaceClass);
        }
        addStaticBlock(classWriter, className, interfaces);
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }

    private static void addStaticBlock(ClassWriter classWriter, String className, Class<?>[] interfaces) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        methodVisitor.visitCode();
        for (Class<?> interfaceClass : interfaces) {
            Method[] methods = interfaceClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                String fieldName = getMethodName(interfaceClass, i);
                methodVisitor.visitLdcInsn(interfaceClass.getName());
                methodVisitor.visitMethodInsn(INVOKESTATIC, Type.getInternalName(Class.class), "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
                methodVisitor.visitLdcInsn(method.getName());

                Class<?>[] parameterTypes = method.getParameterTypes();
                int parameterLength = parameterTypes.length;
                if (parameterLength == 0) {
                    methodVisitor.visitInsn(ACONST_NULL);
                } else {
                    switch (parameterLength) {
                        case 1:
                            methodVisitor.visitInsn(ICONST_1);
                            break;
                        case 2:
                            methodVisitor.visitInsn(ICONST_2);
                            break;
                        case 3:
                            methodVisitor.visitInsn(ICONST_3);
                            break;
                        default:
                            methodVisitor.visitVarInsn(BIPUSH, parameterLength);
                    }
                }
                methodVisitor.visitTypeInsn(ANEWARRAY, Type.getInternalName(Class.class));
                for (int index = 0; index < parameterLength; index++) {
                    methodVisitor.visitInsn(DUP);
                    switch (index) {
                        case 0:
                            methodVisitor.visitInsn(ICONST_0);
                            break;
                        case 1:
                            methodVisitor.visitInsn(ICONST_1);
                            break;
                        case 2:
                            methodVisitor.visitInsn(ICONST_2);
                            break;
                        case 3:
                            methodVisitor.visitInsn(ICONST_3);
                            break;
                        default:
                            methodVisitor.visitVarInsn(BIPUSH, index);
                    }
                    methodVisitor.visitLdcInsn(parameterTypes[index].getName());
                    methodVisitor.visitMethodInsn(INVOKESTATIC, Type.getInternalName(Class.class), "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
                    methodVisitor.visitInsn(AASTORE);
                }
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Class.class), "getMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
                methodVisitor.visitFieldInsn(PUTSTATIC, className, fieldName, Type.getDescriptor(Method.class));
            }
        }
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    private static void implInterFaceMethod(ClassWriter classWriter, String className, Class<?> interfaceClass) {
        Method[] methods = interfaceClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String name = getMethodName(interfaceClass, i);
            classWriter.visitField(ACC_PRIVATE | ACC_STATIC, name, Type.getDescriptor(method.getClass()), null, null);
            MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, method.getName(), Type.getMethodDescriptor(method), null, new String[]{Type.getInternalName(Exception.class)});
            methodVisitor.visitCode();

            Label from = new Label();
            Label to = new Label();
            Label target = new Label();

            methodVisitor.visitLabel(from);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, Type.getInternalName(MyProxy.class), "h", Type.getDescriptor(InvocationHandler.class));

            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETSTATIC, className, name, Type.getDescriptor(Method.class));
            int parameterCount = method.getParameterCount();
            if (parameterCount == 0) {
                methodVisitor.visitInsn(ACONST_NULL);
            } else {
                switch (parameterCount) {
                    case 1:
                        methodVisitor.visitInsn(ICONST_1);
                        break;
                    case 2:
                        methodVisitor.visitInsn(ICONST_2);
                        break;
                    case 3:
                        methodVisitor.visitInsn(ICONST_3);
                        break;
                    default:
                        methodVisitor.visitVarInsn(BIPUSH, parameterCount);
                }
            }
            methodVisitor.visitTypeInsn(ANEWARRAY, Type.getInternalName(Object.class));
            for (int index = 0; index < parameterCount; index++) {
                methodVisitor.visitInsn(DUP);
                switch (index) {
                    case 0:
                        methodVisitor.visitInsn(ICONST_0);
                        break;
                    case 1:
                        methodVisitor.visitInsn(ICONST_1);
                        break;
                    case 2:
                        methodVisitor.visitInsn(ICONST_2);
                        break;
                    case 3:
                        methodVisitor.visitInsn(ICONST_3);
                        break;
                    default:
                        methodVisitor.visitVarInsn(BIPUSH, index);
                }
                methodVisitor.visitVarInsn(ALOAD, index + 1);
                methodVisitor.visitInsn(AASTORE);
            }
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, Type.getInternalName(InvocationHandler.class), "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", true);
            addReturn(methodVisitor, method.getReturnType());

            methodVisitor.visitLabel(to);
            methodVisitor.visitLabel(target);
            methodVisitor.visitInsn(ATHROW);
            methodVisitor.visitTryCatchBlock(from, to, target, Type.getInternalName(Exception.class));
            methodVisitor.visitFrame(F_FULL, 0, null, 0, null);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
    }

    private static String getMethodName(Class<?> interfaceClass, int i) {
        return "_" + interfaceClass.getSimpleName() + "_" + i;
    }

    private static void addReturn(MethodVisitor methodVisitor, Class<?> returnType) {
        if (returnType == Void.class) {
            methodVisitor.visitInsn(RETURN);
        } else {
            methodVisitor.visitTypeInsn(CHECKCAST, Type.getInternalName(returnType));
            methodVisitor.visitInsn(ARETURN);
        }
    }

    private static void createInitMethod(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/reflect/InvocationHandler;)V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(MyProxy.class), "<init>", "(Ljava/lang/reflect/InvocationHandler;)V", false);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();
    }

    private static String[] getInternalNames(Class<?>[] interfaces) {
        return Arrays.stream(interfaces).map(Type::getInternalName).toArray(String[]::new);
    }
}
