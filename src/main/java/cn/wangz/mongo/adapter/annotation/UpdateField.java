package cn.wangz.mongo.adapter.annotation;

import java.lang.annotation.*;

/**
 * @author wang_zh
 * @date 2020/1/9
 */
@Documented
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UpdateField {

    String value() default "";

    /**
     * Update Operators
     * $currentDate $inc $min $max $mul $rename $set $setOnInsert $unset
     * @return
     */
    String operator() default "$set";


}
