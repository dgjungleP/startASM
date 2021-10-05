package bytecode.type.constant;

import bytecode.type.CpInfo;
import bytecode.type.U1;
import bytecode.type.U2;

import java.nio.ByteBuffer;

public class CONSTANT_MethodHandle_info extends CpInfo {
    private U1 reference_kind;
    private U2 reference_index;

    public CONSTANT_MethodHandle_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        this.reference_kind = new U1(codeBuf.get());
        this.reference_index = new U2(codeBuf.get(), codeBuf.get());
    }
}
