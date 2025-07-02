package com.cdyhrj.fast.orm.util;


import com.cdyhrj.fast.orm.annotation.Column;
import com.cdyhrj.fast.orm.annotation.Id;
import com.cdyhrj.fast.orm.annotation.Name;
import com.cdyhrj.fast.orm.annotation.OneToOne;
import com.cdyhrj.fast.orm.annotation.Readonly;
import com.cdyhrj.fast.orm.api.entity.FieldNameSpec;
import com.cdyhrj.fast.orm.api.entity.FieldNameType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 实体类辅助类
 *
 * @author huangqi
 */

public class EntityUtils {
    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Class<?> classOfT = obj.getClass();

            return FieldUtils.readField(FieldUtils.getField(classOfT, fieldName, true), obj, true);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static String getFieldName(Field field) {
        String fieldName = field.getName();

        Column column = field.getAnnotation(Column.class);
        if (Objects.nonNull(column)) {
            if (StringUtils.isNotEmpty(column.value())) {
                fieldName = column.value();
            }
            if (column.hump()) {
                fieldName = NameUtils.toUnderScoreCase(fieldName);
            }
        }

        return fieldName;
    }

    /**
     * 解析对象属性名(使用中)
     *
     * @param classOfT 对象类
     * @return 对象所有字段
     */
    public static List<FieldNameSpec> getObjectFieldNames(Class<?> classOfT) {
        List<FieldNameSpec> fields = new ArrayList<>();

        ReflectionUtils.doWithFields(classOfT,
                field -> {
                    FieldNameSpec fieldNameSpec = FieldNameSpec.of()
                            .propertyName(field.getName())
                            .fieldName(getFieldName(field))
                            .dataType(field.getType());

                    if (field.isAnnotationPresent(Id.class)) {
                        fieldNameSpec.type(FieldNameType.Id);
                    } else if (field.isAnnotationPresent(Name.class)) {
                        fieldNameSpec.type(FieldNameType.Name);
                    } else if (field.isAnnotationPresent(OneToOne.class)) {
                        fieldNameSpec.type(FieldNameType.OneToOne);
                    }

                    if (field.isAnnotationPresent(Readonly.class)) {
                        fieldNameSpec.readonly(true);
                    }

                    fields.add(fieldNameSpec);
                },
                field -> field.isAnnotationPresent(Column.class));

        return fields;
    }
}
