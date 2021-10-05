package bytecode.type.constant;

import bytecode.type.CpInfo;
import bytecode.type.U1;
import bytecode.type.U2;

import java.nio.ByteBuffer;

public class CONSTANT_NameAndType_info extends CpInfo {
    private U2 name_index;
    private U2 description_index;

    public CONSTANT_NameAndType_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        this.name_index = new U2(codeBuf.get(), codeBuf.get());
        this.description_index = new U2(codeBuf.get(), codeBuf.get());
    }
}
