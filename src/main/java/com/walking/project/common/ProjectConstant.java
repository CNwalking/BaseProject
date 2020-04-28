package com.walking.project.common;

import java.util.HashMap;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/9 21:46
 * @Description: 项目常量
 */
public final class ProjectConstant {
    // 生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）
    public static final String BASE_PACKAGE = "com.walking.project";
    // 生成的Mapper所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";
    // 生成的Model所在包
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".dataobject.entity";
    // 生成的Service所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";
    // 生成的ServiceImpl所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
    // 生成的Controller所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";
    // Mapper插件基础接口的完全限定名
    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".Mapper";

    public static final String MD5_SECRET_KEY = "!@#$CNwalking$#@!";

    public static final HashMap<Integer, String> ROLE_MAP = new HashMap<>();
    static {
        ROLE_MAP.put(1, "user");
        ROLE_MAP.put(0, "admin");
    }

    /**
     * 过期时间
     */
    public static class ExpireTime {
        private ExpireTime() {
        }
        public static final int TEN_SEC =  10;//10s
        public static final int THIRTY_SEC =  30;//30s
        public static final int ONE_MINUTE =  60;//一分钟
        public static final int THIRTY_MINUTES =  60 * 30;//30分钟
        public static final int ONE_HOUR = 60 * 60;//一小时
        public static final int THREE_HOURS = 60 * 60 * 3;//三小时
        public static final int TWELVE_HOURS =  60 * 60 * 12;//十二小时，单位s
        public static final int ONE_DAY = 60 * 60 * 24;//二十四小时
    }
}
