package bytecode.type.constant;

import bytecode.type.CpInfo;
import bytecode.type.U1;
import bytecode.type.U2;

import java.nio.ByteBuffer;

public class CONSTANT_InvokeDynamic_info extends CpInfo {
    private U2 bootstrap_method_attr_index;
    private U2 name_and_type_index;

    public CONSTANT_InvokeDynamic_info(U1 tag) {
        super(tag);
    }

    @Override
    public void read(ByteBuffer codeBuf) throws Exception {
        this.bootstrap_method_attr_index = new U2(codeBuf.get(), codeBuf.get());
        this.name_and_type_index = new U2(codeBuf.get(), codeBuf.get());
    }
}
