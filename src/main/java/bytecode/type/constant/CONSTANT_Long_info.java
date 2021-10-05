package bytecode.type.constant;

import bytecode.type.CpInfo;
import bytecode.type.U1;
import bytecode.type.U4;

import java.nio.ByteBuffer;

public class CONSTANT_Long_info extends CpInfo {
    private U4 high_bytes;
    private U4 low_bytes;

    public CONSTANT_Long_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        this.high_bytes = new U4(codeBuf.get(), codeBuf.get(), codeBuf.get(), codeBuf.get());
        this.low_bytes = new U4(codeBuf.get(), codeBuf.get(), codeBuf.get(), codeBuf.get());
    }
}
