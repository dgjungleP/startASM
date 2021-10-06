import bytecode.ClassFileAnalysisMain;
import bytecode.type.*;
import bytecode.type.constant.*;
import bytecode.util.*;
import com.sun.org.apache.bcel.internal.classfile.ConstantDouble;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

public class ByteCodeTest {
    ByteBuffer codeBuf;
    ClassFile classFile;

    @Before
    public void prepare() throws Exception {
        codeBuf = ClassFileAnalysisMain.readFile("F:\\project\\startASM\\target\\classes\\bytecode\\util\\StringUtil.class");
        classFile = ClassFileAnalysiser.analysis(codeBuf);
    }

    @Test
    public void testMagicAndVersionHandler() throws Exception {
        System.out.println("classFile.getMagic().toHexString() = " + classFile.getMagic().toHexString());
        System.out.println("classFile.getMinor_version().toHexString() = " + classFile.getMinor_version().toHexString());
        System.out.println("classFile.getMinor_version().toInt() = " + classFile.getMinor_version().toInt());
        System.out.println("classFile.getMajor_version().toHexString() = " + classFile.getMajor_version().toHexString());
        System.out.println("classFile.getMajor_version().toInt() = " + classFile.getMajor_version().toInt());
    }

    @Test
    public void testConstantPoolHandler() throws Exception {
        Integer cp_pool_count = classFile.getConstant_pool_count().toInt();
        System.out.println("Now constant pool has count： " + cp_pool_count);
        CpInfo[] cpInfos = classFile.getConstant_pool();
        for (int i = 0; i < cpInfos.length; i++) {
            System.out.println("#" + (i + 1) + ": " + cpInfos[i].toString());
        }
    }

    @Test
    public void testAccessFlagsHandler() throws Exception {
        U2 accessFlags = classFile.getAccess_flags();
        System.out.println("ClassAccessFlagUtils.toClassAccessFlagsString(accessFlags) = " + ClassAccessFlagUtils.toAccessFlagsString(accessFlags));
    }

    @Test
    public void testThisAndSuperClassHandler() throws Exception {
        CpInfo[] constantPool = classFile.getConstant_pool();
        String thisClassName = StringUtil.getClassName(classFile.getThis_class(), constantPool);
        String superClassName = StringUtil.getClassName(classFile.getSuper_class(), constantPool);
        System.out.println("thisClassName = " + thisClassName);
        System.out.println("superClassName = " + superClassName);

    }

    @Test
    public void testInterfacesHandler() throws Exception {
        CpInfo[] constantPool = classFile.getConstant_pool();
        Integer interfacesCount = classFile.getInterfaces_count().toInt();
        System.out.println("The interfaces total number is:" + interfacesCount);
        if (interfacesCount == 0) {
            return;
        }
        for (U2 anInterface : classFile.getInterfaces()) {
            String interfacesName = StringUtil.getClassName(anInterface, constantPool);
            System.out.println("interfacesName = " + interfacesName);
        }

    }

    @Test
    public void testFieldsHandler() throws Exception {
        CpInfo[] constantPool = classFile.getConstant_pool();
        Integer fieldsCount = classFile.getFields_count().toInt();
        System.out.println("There have fields  count: " + fieldsCount);
        System.out.println();
        if (fieldsCount == 0) {
            return;
        }
        FieldsInfo[] fields = classFile.getFields();
        for (FieldsInfo field : fields) {
            System.out.println("Access : " + FieldAccessFlagUtils.toAccessFlagsString(field.getAccess_flags()));
            System.out.println("Attribute name: " + StringUtil.getFieldName(field.getName_index(), constantPool));
            System.out.println("Description: " + StringUtil.getFieldName(field.getDescription_index(), constantPool));
            System.out.println("Attribute count: " + field.getAttribute_count().toInt());
            System.out.println();
        }

    }

    @Test
    public void testMethodHandler() throws Exception {
        CpInfo[] constantPool = classFile.getConstant_pool();
        Integer count = classFile.getMethods_count().toInt();
        System.out.println("There have method  count: " + count);
        System.out.println();
        if (count == 0) {
            return;
        }
        MethodInfo[] methodInfos = classFile.getMethods();
        for (MethodInfo field : methodInfos) {
            System.out.println("Access : " + MethodsAccessFlagUtils.toAccessFlagsString(field.getAccess_flags()));
            System.out.println("Method name: " + StringUtil.getFieldName(field.getName_index(), constantPool));
            System.out.println("Method Description: " + StringUtil.getFieldName(field.getDescription_index(), constantPool));
            System.out.println("Attribute count: " + field.getAttribute_count().toInt());
            System.out.println();
        }

    }

    @Test
    public void testAttributesHandler() throws Exception {


    }

    @Test
    public void testConstantValue() throws Exception {
        CpInfo[] constantPool = classFile.getConstant_pool();
        FieldsInfo[] fields = classFile.getFields();
        if (fields == null) {
            return;
        }
        for (FieldsInfo field : fields) {
            AttributeInfo[] attributes = field.getAttributes();
            if (field.getAttribute_count().toInt() == 0) {
                continue;
            }

            System.out.println("The Field:" + constantPool[field.getName_index().toInt() - 1]);
            for (AttributeInfo attribute : attributes) {
                CONSTANT_Utf8_info nameInfo = (CONSTANT_Utf8_info) constantPool[attribute.getAttribute_name_index().toInt() - 1];
                String name = new String(nameInfo.getBytes());
                if ("ConstantValue".equalsIgnoreCase(name)) {
                    ConstantValue_attribute constantValueAttribute = AttributeProcessingFactory.processingConstantValue(attribute);
                    CpInfo cv = constantPool[constantValueAttribute.getConstantValue_index().toInt() - 1];
                    if (cv instanceof CONSTANT_Utf8_info) {
                        System.out.println("ConstantValue:" + cv);
                    } else if (cv instanceof CONSTANT_Float_info) {
                    } else if (cv instanceof CONSTANT_Double_info) {
                    } else if (cv instanceof CONSTANT_Integer_info) {
                        System.out.println("ConstantValue:" + ((CONSTANT_Integer_info) cv).getBytes().toInt());
                    } else if (cv instanceof CONSTANT_Long_info) {


                    }
                }

            }
        }
    }

    @Test
    public void testCodeAttribute() throws Exception {
        CpInfo[] constantPool = classFile.getConstant_pool();
        MethodInfo[] methods = classFile.getMethods();
        if (methods == null) {
            return;
        }
        for (MethodInfo methodInfo : methods) {
            AttributeInfo[] attributes = methodInfo.getAttributes();
            if (methodInfo.getAttribute_count().toInt() == 0) {
                continue;
            }

            System.out.println("The Method:" + constantPool[methodInfo.getName_index().toInt() - 1]);
            for (AttributeInfo attribute : attributes) {
                CONSTANT_Utf8_info nameInfo = (CONSTANT_Utf8_info) constantPool[attribute.getAttribute_name_index().toInt() - 1];
                String name = new String(nameInfo.getBytes());
                if ("Code".equalsIgnoreCase(name)) {
                    CodeAttribute codeAttribute = AttributeProcessingFactory.processingCode(attribute);
                    System.out.println("Stack size: " + codeAttribute.getMax_stack().toInt());
                    System.out.println("Local fields size: " + codeAttribute.getMax_locals().toInt());
                    System.out.println("Byte size: " + codeAttribute.getCode_length().toInt());
                    System.out.println("Bytes: ");
                    System.out.println(StringUtil.toFormatHexString(codeAttribute.getCode()));
                    for (byte code : codeAttribute.getCode()) {
                        System.out.print((code & 0xff) + " ");
                    }
                    System.out.println("\n");
                }
            }
        }
    }


}
