package bytecode;

import bytecode.type.ClassFile;
import bytecode.util.ClassFileAnalysiser;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

public class ClassFileAnalysisMain {
    public static void main(String[] args) throws Exception {
        ByteBuffer codeBuf = readFile("F:\\project\\startASM\\target\\classes\\StackOverFlow.class");
        ClassFile classFile = ClassFileAnalysiser.analysis(codeBuf);
        System.out.println("classFile.getMagic().toHexString() = " + classFile.getMagic().toHexString());
    }

    public static ByteBuffer readFile(String fileName) throws Exception {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new Exception("File not found");
        }
        byte[] byteCodeBuf = new byte[4096];
        int length;
        FileInputStream inputStream = new FileInputStream(file);
        length = inputStream.read(byteCodeBuf);
        if (length < 0) {
            throw new Exception("Not read the byte code.");
        }
        return ByteBuffer.wrap(byteCodeBuf, 0, length).asReadOnlyBuffer();
    }
}
