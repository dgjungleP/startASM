package bytecode.type.constant;

import bytecode.type.CpInfo;
import bytecode.type.U1;
import bytecode.type.U2;

import java.nio.ByteBuffer;

public class CONSTANT_Methodref_info extends CONSTANT_Fieldref_info {

    public CONSTANT_Methodref_info(U1 tag) {
        super(tag);
    }

}
