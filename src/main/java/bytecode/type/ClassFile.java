package bytecode.type;

import lombok.Data;

@Data
public class ClassFile {
    private U4 magic;//魔数
    private U2 minor_version;//副板本号
    private U2 major_version;//主板本号
    private U2 constant_pool_count;//常量池计数器
    private CpInfo constant_pool;//常量池
    private U2 access_flags;//访问标记
    private U2 this_class;//类索引
    private U2 super_class;//父类索引
    private U2 interfaces_count;//接口总数
    private U2[] interfaces;//杰佛那个数组
    private U2 fields_count;//字段总数
    private FieldsInfo[] fields;//字段表
    private U2 methods_count;//方法总数
    private MethodInfo[] methods;//方法表
    private U2 attributes_count;//属性总数
    private AttributeInfo[] attributes;//属性表
}
