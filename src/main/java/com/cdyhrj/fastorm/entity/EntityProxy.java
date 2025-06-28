package com.cdyhrj.fastorm.entity;

import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.api.entity.FieldNameSpec;
import com.cdyhrj.fastorm.api.enums.RelationType;
import com.cdyhrj.fastorm.entity.meta.ManyToManyMasterInfo;
import com.cdyhrj.fastorm.entity.meta.ManyToManyMeta;
import com.cdyhrj.fastorm.entity.meta.OneToManyMeta;
import com.cdyhrj.fastorm.entity.meta.OneToOneMeta;
import com.cdyhrj.fastorm.exception.ResultSetTransformException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

/**
 * 对等实体
 *
 * @author huangqi
 */

public interface EntityProxy {
    /**
     * 新对象
     *
     * @return 新对象
     */
    Entity newEntity();

    /**
     * 获取表名
     *
     * @return 表名
     */
    String getTableName();

    /**
     * 获取主键字段
     *
     * @return 主键字段
     */
    String getIdFieldName();

    /**
     * 获取@Name字段名
     *
     * @return @Name字段名
     */
    String getNameFieldName();

    /**
     * 更新Id值
     * 插入后，更新对应的Id值，用于后续使用
     *
     * @param entity  实体对象
     * @param idValue Id值
     */
    void updateEntityId(Entity entity, Long idValue);

    /**
     * 获取标记了@Id字段的值
     *
     * @param entity 对应的实体
     * @return Id字段值
     */
    Long getIdValue(Entity entity);

    /**
     * 获取标记了@Name字段的值,如果不存在@Name注解，返回异常
     *
     * @param entity 对应的实体
     * @return Name字段值
     */
    String getNameValue(Entity entity);

    /**
     * 属性名转为字段名
     *
     * @return 属性到字段名转换Map
     */
    PropertyToFieldNameMap propertyToFieldName();

    /**
     * 获取所有字段信息
     *
     * @return 所有字段信息
     */
    List<FieldNameSpec> getAllFieldInfo();

    /**
     * 获取所有关联属性
     *
     * @return 所有关系
     */
    List<String> getAllRelations();

    /**
     * 获取默认字段名列表
     *
     * @param operationType 操作类型
     * @return 默认字段名列表
     */
    List<String> getDefaultFieldNames(OperationType operationType);

    /**
     * 更新对象
     *
     * @param entity    实体对象
     * @param index     index
     * @param fieldName 字段名
     * @param rs        数据集
     */
    void updateEntity(Entity entity, int index, String fieldName, ResultSet rs);

    /**
     * 更新实体默认值
     *
     * @param entity        实体对象
     * @param operationType 操作类型
     */
    void updateEntityWithDefaultValue(Entity entity, OperationType operationType);

    /**
     * 获取默认值Map
     *
     * @param operationType 操作类型
     * @return 默认值Map
     */
    Map<String, Object> getDefaultValueMap(OperationType operationType);

    /**
     * 获取关系类型
     *
     * @param relation 关系
     * @return 关系类型
     */
    RelationType getRelationType(String relation);

    /**
     * 获取OneToOne对应的值
     *
     * @param entity   实体
     * @param relation OneToOne关系
     * @return 实体对象
     */
    Entity getRelationOneToOneValue(Entity entity, String relation);

    /**
     * 更新一对一主表值
     *
     * @param entity 一对一对象
     * @param value  主表值
     */
    void updateEntityOneToOneId(Entity entity, long value);

    /**
     * 获取OneToMany对应的值
     *
     * @param entity   实体
     * @param relation OneToMany关系
     * @return 所有实体
     */
    List<Entity> getRelationOneToManyValue(Entity entity, String relation);


    /**
     * 更新一对多主表值
     *
     * @param entity 一对一对象
     * @param value  主表值
     */
    void updateEntityOneToManyName(Entity entity, long value);


    /**
     * 获取ManyToMany对应的值
     *
     * @param entity   实体
     * @param relation ManyToMany
     * @return 所有实体
     */
    List<Entity> getRelationManyToManyValue(Entity entity, String relation);

    /**
     * 获取 ManyToMany 元数据
     *
     * @return ManyToMany 元数据
     */
    ManyToManyMeta getRelationManyToManyMeta();

    /**
     * 获取 OneToMany 元数据
     *
     * @return OneToMany 元数据
     */
    OneToManyMeta getRelationOneToManyMeta();

    /**
     * 获取 OneToOne元数据，有@IdOneToOne注解的
     *
     * @return OneToOne元数据
     */
    OneToOneMeta getRelationOneToOneMeta();

    /**
     * 获取 ValueMap，所有值写入Map中
     *
     * @param entity 实体对象
     * @return Value Map
     */
    Map<String, Object> getValueMap(Entity entity);


    /**
     * 获取关系实际对象类
     *
     * @param relation 关系名
     * @return 对象类
     */
    Class<?> getRelationClass(String relation);

    /**
     * 获取多对多关系主信息
     *
     * @param relation 关系
     * @return ManyToMany信息
     */
    ManyToManyMasterInfo getManyToManyRelationMasterInfoClass(String relation);

    /**
     * 通过resultSet更新实体
     *
     * @param rs resultSet
     * @return 实体对象
     */
    @SuppressWarnings({"unchecked", "RedundantSuppression"})
    default <T> T updateEntity(ResultSet rs) {
        Entity entity = newEntity();

        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                updateEntity(entity, i, rsMetaData.getColumnLabel(i).toLowerCase(), rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResultSetTransformException();
        }

        return (T) entity;
    }
}
