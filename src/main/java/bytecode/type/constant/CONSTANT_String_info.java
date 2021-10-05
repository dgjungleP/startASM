package bytecode.type.constant;

import bytecode.type.CpInfo;
import bytecode.type.U1;
import bytecode.type.U2;

import java.nio.ByteBuffer;

public class CONSTANT_String_info extends CpInfo {
    private U2 string_index;

    public CONSTANT_String_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        this.string_index = new U2(codeBuf.get(), codeBuf.get());
    }
}
