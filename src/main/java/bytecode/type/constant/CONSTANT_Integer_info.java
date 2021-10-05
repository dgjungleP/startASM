package bytecode.type.constant;

import bytecode.type.CpInfo;
import bytecode.type.U1;
import bytecode.type.U2;
import bytecode.type.U4;

import java.nio.ByteBuffer;

public class CONSTANT_Integer_info extends CpInfo {
    private U4 bytes;

    public CONSTANT_Integer_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        this.bytes = new U4(codeBuf.get(), codeBuf.get(), codeBuf.get(), codeBuf.get());
    }
}
