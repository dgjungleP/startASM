import bytecode.ClassFileAnalysisMain;
import bytecode.type.ClassFile;
import bytecode.type.CpInfo;
import bytecode.type.U2;
import bytecode.type.constant.CONSTANT_Class_info;
import bytecode.util.ClassAccessFlagUtils;
import bytecode.util.ClassFileAnalysiser;
import bytecode.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ByteCodeTest {
    ByteBuffer codeBuf;
    ClassFile classFile;

    @Before
    public void prepare() throws Exception {
        codeBuf = ClassFileAnalysisMain.readFile("F:\\project\\startASM\\target\\classes\\bytecode\\handler\\InterfacesHandler.class");
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
        System.out.println("ClassAccessFlagUtils.toClassAccessFlagsString(accessFlags) = " + ClassAccessFlagUtils.toClassAccessFlagsString(accessFlags));
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


}
