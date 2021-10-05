import bytecode.ClassFileAnalysisMain;
import bytecode.type.ClassFile;
import bytecode.util.ClassFileAnalysiser;
import org.junit.Test;

import java.nio.ByteBuffer;

public class ByteCodeTest {

    @Test
    public void testMagicAndVersionHandler() throws Exception {
        ByteBuffer codeBuf = ClassFileAnalysisMain.readFile("F:\\project\\startASM\\target\\classes\\StackOverFlow.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        System.out.println("classFile.getMagic().toHexString() = " + classFile.getMagic().toHexString());
        System.out.println("classFile.getMinor_version().toHexString() = " + classFile.getMinor_version().toHexString());
        System.out.println("classFile.getMinor_version().toInt() = " + classFile.getMinor_version().toInt());
        System.out.println("classFile.getMajor_version().toHexString() = " + classFile.getMajor_version().toHexString());
        System.out.println("classFile.getMajor_version().toInt() = " + classFile.getMajor_version().toInt());
    }
}
