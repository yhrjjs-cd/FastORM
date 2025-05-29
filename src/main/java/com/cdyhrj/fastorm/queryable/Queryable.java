package com.cdyhrj.fastorm.queryable;

import com.cdyhrj.fastorm.condition.Condition;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.lambda.PropFn;
import com.cdyhrj.fastorm.meta.AliasEntity;
import com.cdyhrj.fastorm.queryable.join.Join;
import com.cdyhrj.fastorm.queryable.join.JoinType;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * Queryable
 *
 * @author <a href="mailto:huangqi@cdyhrj.com">黄奇</a>
 */
public class Queryable<E extends Entity> {
    /**
     * 所有使用的类
     */
    private final List<AliasEntity<? extends Entity>> entityClasses = new ArrayList<>();

    /**
     * 连接
     */
    private List<Join<?, ?>> joins = new ArrayList<>();
    /**
     * 主键Id值
     */
    private Long id;

    /**
     * Name 值
     */
    private String name;

    /**
     * 条件
     */
    private Condition condition;

    /**
     * 基础类别名实体
     */
    private final AliasEntity<E> baseAliasEntity;

    public Queryable(Class<E> entityClass) {
        this.baseAliasEntity = newAliasEntity(entityClass);
        this.entityClasses.add(baseAliasEntity);
    }

    /**
     * 左连接
     *
     * @param targetClass 目标类
     * @param <E1>        目标类型
     * @return 当前对象
     */
    public <E1 extends Entity> Join<E, E1> leftJoin(Class<E1> targetClass) {
        AliasEntity<E1> aliasEntity = newAliasEntity(targetClass);
        Join<E, E1> join = Join.of(this, JoinType.LEFT, baseAliasEntity, aliasEntity);
        joins.add(join);

        return join;
    }

    public Queryable<E> id(Long id) {
        this.id = id;

        return this;
    }

    public Queryable<E> name(String name) {
        this.name = name;

        return this;
    }

    public Queryable<E> where(Condition condition) {
        this.condition = condition;

        return this;
    }

    /**
     * 设置参数
     *
     * @param keyFun 字段函数
     * @param value  值
     * @return 当前对象
     */
    public Queryable<E> param(@NonNull PropFn<E, ?> keyFun, Object value) {

        return this;
    }

    /**
     * 查询实体列表
     *
     * @return 实体列表
     */
    public List<E> toList() {
        return Collections.emptyList();
    }

    /**
     * 查询总数
     *
     * @return 总数
     */
    public int count() {
        return 0;
    }

    public String toSqlString() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("SELECT * FROM")
                .add(baseAliasEntity.toSqlString());

        joins.forEach(join -> sj.add(join.toSqlString()));

        return sj.toString();
    }

    /**
     * 新别名实体
     *
     * @param entityClass 实体类
     */
    private <T extends Entity> AliasEntity<T> newAliasEntity(Class<T> entityClass) {
        return AliasEntity.of("T%d".formatted(this.entityClasses.size() + 1), entityClass);
    }
}
