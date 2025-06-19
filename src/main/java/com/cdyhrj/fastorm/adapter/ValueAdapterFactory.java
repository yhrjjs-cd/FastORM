package com.cdyhrj.fastorm.adapter;

import com.cdyhrj.fastorm.adapter.impl.BooleanValueAdapter;
import com.cdyhrj.fastorm.adapter.impl.DateValueAdapter;
import com.cdyhrj.fastorm.adapter.impl.DoubleValueAdapter;
import com.cdyhrj.fastorm.adapter.impl.FloatValueAdapter;
import com.cdyhrj.fastorm.adapter.impl.IntValueAdapter;
import com.cdyhrj.fastorm.adapter.impl.LocalDateTimeValueAdapter;
import com.cdyhrj.fastorm.adapter.impl.LocalDateValueAdapter;
import com.cdyhrj.fastorm.adapter.impl.LongValueAdapter;
import com.cdyhrj.fastorm.adapter.impl.StringValueAdapter;
import com.cdyhrj.fastorm.adapter.impl.TimestampValueAdapter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ValueAdapter 工厂
 *
 * @author huangqi
 */
@Slf4j
public class ValueAdapterFactory {
    private static final Map<Class<?>, ValueAdapter<?>> ADAPTOR_MAP = new HashMap<>();

    static {
        ADAPTOR_MAP.put(String.class, new StringValueAdapter());
        ADAPTOR_MAP.put(Long.class, new LongValueAdapter());
        ADAPTOR_MAP.put(Long.TYPE, ADAPTOR_MAP.get(Long.class));
        ADAPTOR_MAP.put(Integer.class, new IntValueAdapter());
        ADAPTOR_MAP.put(Integer.TYPE, ADAPTOR_MAP.get(Integer.class));
        ADAPTOR_MAP.put(Double.class, new DoubleValueAdapter());
        ADAPTOR_MAP.put(Double.TYPE, ADAPTOR_MAP.get(Double.class));
        ADAPTOR_MAP.put(Float.class, new FloatValueAdapter());
        ADAPTOR_MAP.put(Float.TYPE, ADAPTOR_MAP.get(Float.class));
        ADAPTOR_MAP.put(Date.class, new DateValueAdapter());
        ADAPTOR_MAP.put(Timestamp.class, new TimestampValueAdapter());
        ADAPTOR_MAP.put(LocalDate.class, new LocalDateValueAdapter());
        ADAPTOR_MAP.put(LocalDateTime.class, new LocalDateTimeValueAdapter());
        ADAPTOR_MAP.put(Boolean.class, new BooleanValueAdapter());
        ADAPTOR_MAP.put(Boolean.TYPE, ADAPTOR_MAP.get(Boolean.class));
        ADAPTOR_MAP.put(Enum.class, new EnumValueAdapter());

        ADAPTOR_MAP.put(JsonValueAdapter.class, new JsonValueAdapter());
        ADAPTOR_MAP.put(ObjectValueAdapter.class, new ObjectValueAdapter());
    }

    public static ValueAdapter<?> getValueAdapter(Class<? extends ValueAdapter<?>> adapterClass) {
        return ADAPTOR_MAP.get(adapterClass);
    }

    public static ValueAdapter<?> getValueAdapterOfProperty(Object value) {
        if (value instanceof Enum<?>) {
            return ADAPTOR_MAP.get(Enum.class);
        }

        ValueAdapter<?> adaptor = ADAPTOR_MAP.get(value.getClass());

        if (Objects.isNull(adaptor)) {
            // default use json
            return ADAPTOR_MAP.get(ObjectValueAdapter.class);
        }

        return adaptor;
    }


    /**
     * 代码动态生成中使用
     *
     * @param classOfT 类目
     * @return 适配器
     */
    public static ValueAdapter<?> getValueAdapterOfType(Class<?> classOfT) {
        if (classOfT.isEnum()) {
            return ADAPTOR_MAP.get(Enum.class);
        }

        ValueAdapter<?> adaptor = ADAPTOR_MAP.get(classOfT);

        if (Objects.isNull(adaptor)) {
            // default use json
            return ADAPTOR_MAP.get(ObjectValueAdapter.class);
        }

        return adaptor;
    }

    @SneakyThrows
    public static void registerValueAdapter(Class<? extends ValueAdapter<?>> adapterClass) {
        if (ADAPTOR_MAP.containsKey(adapterClass)) {
            return;
        }

        ADAPTOR_MAP.put(adapterClass, adapterClass.getDeclaredConstructor().newInstance());
    }
}
