package com.walking.project.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: CNwalking
 * @DateTime: 2020/4/26 4:06 下午
 * @Description: 有此标记的方法不验证身份
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pass {
}
