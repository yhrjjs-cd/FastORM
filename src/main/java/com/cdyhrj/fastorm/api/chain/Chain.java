package com.cdyhrj.fastorm.api.chain;

import com.cdyhrj.fastorm.adapter.ValueAdapter;
import com.cdyhrj.fastorm.adapter.ValueAdapterFactory;
import com.cdyhrj.fastorm.api.lambda.LambdaColumn;
import com.cdyhrj.fastorm.api.lambda.PropFn;
import com.cdyhrj.fastorm.entity.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 名值链
 *
 * @author huangqi
 */
public class Chain<E extends Entity> {
    final private ChainEntry<E> head;
    private ChainEntry<E> current;
    private ChainEntry<E> tail;
    private int size;

    public Chain(@NonNull PropFn<? extends E, ?> keyFn, Object value) {
        this.head = ChainEntry.of(LambdaColumn.resolve(keyFn).getName(), value);
        this.current = head;
        this.tail = head;
        this.size = 1;
    }

    public static <E extends Entity> Chain<E> make(@NonNull PropFn<? extends E, ?> keyFn, Object value) {
        return new Chain<>(keyFn, value);
    }

    public static <E extends Entity> Chain<E> makeSpecial(@NonNull PropFn<? extends E, ?> keyFn, Object value) {
        Chain<E> chain = new Chain<>(keyFn, value);
        chain.head.special = true;

        return chain;
    }

    public int size() {
        return this.size;
    }

    public Chain<E> adaptor(ValueAdapter<E> adaptor) {
        current.adaptor = adaptor;

        return this;
    }

    public ValueAdapter<E> adaptor() {
        return current.adaptor;
    }

    public Chain<E> add(@NonNull PropFn<? extends E, ?> keyFn, Object value) {
        String name = LambdaColumn.resolve(keyFn).getName();

        tail.next = ChainEntry.of(name, value);
        tail = tail.next;
        size++;

        return this;
    }

    public Chain<E> addSpecial(@NonNull PropFn<? extends E, ?> keyFn, Object value) {
        add(keyFn, value);
        tail.special = true;

        return this;
    }

    public Chain<E> next() {
        current = current.next;

        return current == null ? null : this;
    }

    public Chain<E> head() {
        current = head;

        return this;
    }

    public boolean special() {
        return current.special;
    }

    public Map<String, Object> toParamMap() {
        Map<String, Object> paraMap = new HashMap<>(size);

        ChainEntry<E> _current = head;
        while (_current != null) {
            if (Objects.isNull(_current.value)) {
                paraMap.put(_current.name, null);
            } else {
                ValueAdapter<?> adaptor = ValueAdapterFactory.getValueAdapterOfProperty(_current.value);
                paraMap.put(_current.name, adaptor.transValue(_current.value));
            }


            _current = _current.next;
        }

        return paraMap;
    }

    public void each(EachFn<E> eachFn) {
        ChainEntry<E> current = head;
        while (current != null) {
            eachFn.withChain(current);

            current = current.next;
        }

    }

    @FunctionalInterface
    public interface EachFn<E extends Entity> {
        /**
         * 处理each
         *
         * @param chainEntry 项name
         */
        void withChain(ChainEntry<E> chainEntry);
    }

    public static class ChainEntry<E extends Entity> {
        @Getter
        @Setter
        private String name;
        protected Object value;
        protected transient ValueAdapter<E> adaptor;
        protected boolean special;
        protected ChainEntry<E> next;

        public static <E extends Entity> ChainEntry<E> of(String name, Object value) {
            ChainEntry<E> chainEntry = new ChainEntry<>();
            chainEntry.name = name;
            chainEntry.value = value;

            return chainEntry;
        }
    }
}
