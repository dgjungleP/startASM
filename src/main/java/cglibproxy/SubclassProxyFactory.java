package cglibproxy;

import jdkproxy.MyProxy;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import startasm.MyClassWriter;
import sun.awt.HKSCS;
import sun.net.www.protocol.http.HttpURLConnection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class SubclassProxyFactory {
    private static final List<String> EXCLUDE_METHOD = new ArrayList<>();

    static {
        EXCLUDE_METHOD.add("wait");
        EXCLUDE_METHOD.add("equals");
        EXCLUDE_METHOD.add("toString");
        EXCLUDE_METHOD.add("hashCode");
        EXCLUDE_METHOD.add("getClass");
        EXCLUDE_METHOD.add("notify");
        EXCLUDE_METHOD.add("notifyAll");
    }

    public static byte[] CreateProxyClass(String subClassName, Class<?> superClass) {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classWriter.visit(V1_8, ACC_PUBLIC, subClassName, null, Type.getInternalName(superClass), null);
        createInitMethod(classWriter, subClassName, superClass);

        Method[] methods = getProxyMethod(superClass);
        if (methods.length > 0) {
            addStaticBlock(classWriter, subClassName, superClass, methods);
            overrideMethods(classWriter, subClassName, superClass, methods);
        }
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }

    private static void overrideMethods(ClassWriter classWriter, String subClassName, Class<?> superClass, Method[] methods) {
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String name = method.getName();
            MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, name, Type.getMethodDescriptor(method), null, new String[]{Type.getInternalName(Exception.class)});
            methodVisitor.visitCode();

            Label from = new Label();
            Label to = new Label();
            Label target = new Label();

            methodVisitor.visitLabel(from);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, subClassName, "h", Type.getDescriptor(MyMethodInterceptor.class));

            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitTypeInsn(CHECKCAST, Type.getInternalName(superClass));
            methodVisitor.visitFieldInsn(GETSTATIC, subClassName, getMethodName(method, i), Type.getDescriptor(Method.class));
            methodVisitor.visitLdcInsn(method.getName());


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


            methodVisitor.visitMethodInsn(INVOKEINTERFACE, Type.getInternalName(MyMethodInterceptor.class), "intercept", "(Ljava/lang/Object;Ljava/lang/reflect/Method;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", true);

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

    private static void addStaticBlock(ClassWriter classWriter, String subClassName, Class<?> superClass, Method[] methods) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        methodVisitor.visitCode();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String fieldName = getMethodName(method, i);
            classWriter.visitField(ACC_PRIVATE | ACC_STATIC, fieldName, Type.getDescriptor(Method.class), null, null);
            addCallSuperClassMethod(classWriter, superClass, getMethodName(method, i), method);
            methodVisitor.visitLdcInsn(subClassName.replaceAll("/", "."));

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
            methodVisitor.visitFieldInsn(PUTSTATIC, subClassName, fieldName, Type.getDescriptor(Method.class));
        }
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    private static void addCallSuperClassMethod(ClassWriter classWriter, Class<?> superClass, String methodName, Method method) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_FINAL, methodName, Type.getMethodDescriptor(method), null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0) {
            for (int i = 0; i < parameterTypes.length; i++) {
                methodVisitor.visitVarInsn(ALOAD, i + 1);
            }
        }
        methodVisitor.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(superClass), method.getName(), Type.getMethodDescriptor(method), false);

        addReturn(methodVisitor, method.getReturnType());
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    private static void addReturn(MethodVisitor methodVisitor, Class<?> returnType) {
        if (returnType == Void.class) {
            methodVisitor.visitInsn(RETURN);
        } else {
            methodVisitor.visitTypeInsn(CHECKCAST, Type.getInternalName(returnType));
            methodVisitor.visitInsn(ARETURN);
        }
    }

    private static String getMethodName(Method method, int i) {
        return "_" + method.getName() + "_" + i;
    }

    private static Method[] getProxyMethod(Class<?> superClass) {
        Method[] methods = superClass.getMethods();
        List<Object> methodList = new ArrayList<>();

        for (Method method : methods) {
            if (EXCLUDE_METHOD.contains(method.getName())) {
                continue;
            }
            if (((method.getModifiers() & Modifier.FINAL) == Modifier.FINAL)) {
                continue;
            }
            methodList.add(method);
        }
        return methodList.toArray(new Method[0]);
    }

    private static void createInitMethod(ClassWriter classWriter, String subClassName, Class<?> superClass) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "(" + Type.getDescriptor(MyMethodInterceptor.class) + ")V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(superClass), "<init>", "()V", false);
        classWriter.visitField(ACC_PRIVATE, "h", Type.getDescriptor(MyMethodInterceptor.class), null, null);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitFieldInsn(PUTFIELD, subClassName, "h", Type.getDescriptor(MyMethodInterceptor.class));
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();
    }
}
