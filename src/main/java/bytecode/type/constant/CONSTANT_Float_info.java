package bytecode.type.constant;

import bytecode.type.CpInfo;
import bytecode.type.U1;
import bytecode.type.U4;

import java.nio.ByteBuffer;

public class CONSTANT_Float_info extends CONSTANT_Integer_info {

    public CONSTANT_Float_info(U1 tag) {
        super(tag);
    }

}
