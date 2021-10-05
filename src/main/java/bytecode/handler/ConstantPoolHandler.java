package bytecode.handler;

import bytecode.type.ClassFile;
import bytecode.type.CpInfo;
import bytecode.type.U1;
import bytecode.type.U2;
import sun.java2d.pipe.SpanIterator;

import javax.swing.plaf.SpinnerUI;
import java.nio.ByteBuffer;

public class ConstantPoolHandler implements BaseByteCodeHandler {
    @Override
    public int order() {
        return 2;
    }

    @Override
    public void read(ByteBuffer codeBuffer, ClassFile classFile) throws Exception {
        U2 cpLen = new U2(codeBuffer.get(), codeBuffer.get());
        classFile.setConstant_pool_count(cpLen);
        int cpInfoLen = cpLen.toInt() - 1;
        classFile.setConstant_pool(new CpInfo[cpInfoLen]);
        for (int i = 0; i < cpInfoLen; i++) {
            U1 tag = new U1(codeBuffer.get());
            CpInfo cpInfo = CpInfo.newCpInfo(tag);
            cpInfo.read(codeBuffer);
//            System.out.println("#" + (i + 1) + ": " + cpInfo);
            classFile.getConstant_pool()[i] = cpInfo;
        }
    }
}
