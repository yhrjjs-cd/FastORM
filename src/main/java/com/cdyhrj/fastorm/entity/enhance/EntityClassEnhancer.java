package com.cdyhrj.fastorm.entity.enhance;

import com.cdyhrj.fastorm.adapter.ValueAdapterFactory;
import com.cdyhrj.fastorm.annotation.ColDefine;
import com.cdyhrj.fastorm.annotation.ManyToMany;
import com.cdyhrj.fastorm.annotation.enums.OperationType;
import com.cdyhrj.fastorm.api.entity.FieldNameSpec;
import com.cdyhrj.fastorm.api.entity.FieldNameType;
import com.cdyhrj.fastorm.api.enums.RelationType;
import com.cdyhrj.fastorm.entity.EnhancedEntityClassMap;
import com.cdyhrj.fastorm.entity.Entity;
import com.cdyhrj.fastorm.entity.EntityProxy;
import com.cdyhrj.fastorm.entity.PropertyToFieldNameMap;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.FunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetAllFieldInfoFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetAllRelationsFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetDefaultFieldNamesFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetDefaultValueMapFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetIdFieldNameFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetIdValueFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetNameFieldNameFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetNameValueFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetRelationClassFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetRelationManyToManyMasterInfoFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetRelationManyToManyMetaFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetRelationManyToManyValueFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetRelationOneToManyMetaFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetRelationOneToManyValueFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetRelationOneToOneMetaFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetRelationOneToOneValueFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetRelationTypeFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetTableNameFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.GetValueMapFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.NewEntityFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.PropertyToFieldNameFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.UpdateEntityFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.UpdateEntityIdFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.UpdateEntityOneToManyIdFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.UpdateEntityOneToOneIdFunGenerator;
import com.cdyhrj.fastorm.entity.enhance.generator.proxy.UpdateEntityWithDefaultValueFunGenerator;
import com.cdyhrj.fastorm.entity.meta.ManyToManyMeta;
import com.cdyhrj.fastorm.exception.EntityClassEnhanceException;
import com.cdyhrj.fastorm.util.IdUtils;
import com.google.common.collect.Lists;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实体类增强
 *
 * @author huangqi
 */
@Slf4j
public class EntityClassEnhancer {
    /**
     * 代理类名模版
     */
    public static final String PROXY_CLASS_NAME_TEMPLATE = "%sProxy$";
    /**
     * 获取泛型类
     */
    private static final Pattern PATTERN_GENERIC_OF_LIST = Pattern.compile("L[a-zA-Z0-9/]+<L([a-zA-Z0-9/]+);>;");

    /**
     * Many To Many关联类
     */
    private static final Pattern PATTERN_RELATION_CLASS_OF_MANY_TO_MANY = Pattern.compile(
            "@[a-zA-Z0-9.]+\\(relationClass=([a-zA-Z0-9.]+)\\.class\\)");

    List<FunGenerator> generators = Lists.newArrayList(
            new NewEntityFunGenerator(),
            new GetTableNameFunGenerator(),
            new GetIdFieldNameFunGenerator(),
            new GetNameFieldNameFunGenerator(),
            new GetIdValueFunGenerator(),
            new GetNameValueFunGenerator(),
            new GetRelationClassFunGenerator(),
            new PropertyToFieldNameFunGenerator(),
            new GetAllFieldInfoFunGenerator(),
            new GetAllRelationsFunGenerator(),
            new GetDefaultFieldNamesFunGenerator(),
            new UpdateEntityFunGenerator(),
            new UpdateEntityIdFunGenerator(),
            new UpdateEntityWithDefaultValueFunGenerator(),
            new GetRelationTypeFunGenerator(),
            new GetRelationOneToOneValueFunGenerator(),
            new UpdateEntityOneToOneIdFunGenerator(),
            new GetRelationOneToManyValueFunGenerator(),
            new UpdateEntityOneToManyIdFunGenerator(),
            new GetRelationManyToManyValueFunGenerator(),
            new GetRelationManyToManyMetaFunGenerator(),
            new GetRelationManyToManyMasterInfoFunGenerator(),
            new GetRelationOneToManyMetaFunGenerator(),
            new GetRelationOneToOneMetaFunGenerator(),
            new GetValueMapFunGenerator(),
            new GetDefaultValueMapFunGenerator());

    /**
     * 增强实体类
     *
     * @param classes 类
     */
    public void enhance(String[] classes) {
        try {
            ClassPool classPool = getClassPool();

            CtClass ctEntityInterface = classPool.get(Entity.class.getName());
            CtClass ctEntityPeerInterface = classPool.get(EntityProxy.class.getName());

            List<String> toEnhancedClass = orderClasses(classes, classPool);

            for (String classStringOfT : toEnhancedClass) {
                if (log.isInfoEnabled()) {
                    log.info("Enhance entity class: {}", classStringOfT);
                }

                Class<? extends Entity> entityClass = enhanceBaseEntity(classStringOfT, classPool, ctEntityInterface);
                generateEntityProxies(entityClass, classPool, ctEntityPeerInterface);
                registerValueAdapter(entityClass);
                EnhancedEntityClassMap.registerEntity(entityClass);
            }
        } catch (Exception ex) {
            log.error("Enhance error: {}", ex.getMessage());
        }

    }

    private static ClassPool getClassPool() {
        ClassPool classPool = ClassPool.getDefault();
        classPool.importPackage(Entity.class.getName());
        classPool.importPackage(EntityProxy.class.getName());
        classPool.importPackage(FieldNameSpec.class.getName());
        classPool.importPackage(FieldNameType.class.getName());
        classPool.importPackage(IdUtils.class.getName());
        classPool.importPackage(Date.class.getName());
        classPool.importPackage(RelationType.class.getName());
        classPool.importPackage(ManyToManyMeta.class.getName());
        classPool.importPackage(ValueAdapterFactory.class.getName());
        classPool.importPackage(OperationType.class.getName());
        classPool.importPackage(PropertyToFieldNameMap.class.getName());

        return classPool;
    }

    /**
     * 注册ValueAdapter
     *
     * @param entityClass 实体类
     */
    private void registerValueAdapter(Class<?> entityClass) {
        ReflectionUtils.doWithFields(entityClass, field -> {
            ColDefine colDefine = field.getAnnotation(ColDefine.class);

            ValueAdapterFactory.registerValueAdapter(colDefine.adapter());
        }, field -> field.isAnnotationPresent(ColDefine.class));
    }

    /**
     * 保证一个类只能增强一次，对于关系表，需要提前增强
     *
     * @param classes   所有需要增强的类
     * @param classPool 池
     * @return 排序后的增强类
     */
    private List<String> orderClasses(String[] classes, ClassPool classPool) {
        List<String> result = Lists.newArrayList();
        List<String> source = Arrays.asList(classes.clone());

        try {
            for (String c : classes) {
                if (result.contains(c)) {
                    continue;
                }

                CtClass ctClass = classPool.get(c);
                for (CtField field : ctClass.getDeclaredFields()) {
                    String type = field.getType().getName();
                    String signature = field.getGenericSignature();
                    if (Objects.nonNull(signature)) {
                        Matcher matcher = PATTERN_GENERIC_OF_LIST.matcher(signature);
                        if (matcher.find()) {
                            type = matcher.group(1).replace("/", ".");
                        }
                    }

                    if (!result.contains(type) && !c.equals(type) && source.contains(type)) {
                        result.add(type);
                    }

                    Object manyToMany = field.getAnnotation(ManyToMany.class);
                    if (Objects.nonNull(manyToMany)) {

                        Matcher matcher = PATTERN_RELATION_CLASS_OF_MANY_TO_MANY.matcher(manyToMany.toString());
                        if (matcher.find()) {
                            String relationClassType = matcher.group(1);

                            if (!result.contains(relationClassType) &&
                                    !c.equals(relationClassType) &&
                                    source.contains(relationClassType)) {
                                result.add(relationClassType);
                            }
                        }
                    }
                }

                if (!result.contains(c)) {
                    result.add(c);
                }
            }
        } catch (Exception ex) {
            log.error("Order Classes Error: {}", ex.getMessage());
        }

        return result;
    }

    /**
     * 增强实体类
     *
     * @param classStringOfT    类名
     * @param classPool         类池
     * @param ctEntityInterface 实体类接口
     * @return 增强后实体类
     */
    private Class<? extends Entity> enhanceBaseEntity(String classStringOfT, ClassPool classPool, CtClass ctEntityInterface) {
        try {
            CtClass ctClass = classPool.get(classStringOfT);
            if (!ctClass.subtypeOf(ctEntityInterface)) {
                ctClass.addInterface(ctEntityInterface);
            }

            @SuppressWarnings("unchecked")
            Class<? extends Entity> clazz = (Class<? extends Entity>) ctClass.toClass();
            ctClass.detach();

            return clazz;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Enhance error: {}", ex.getMessage());
            throw new EntityClassEnhanceException(classStringOfT);
        }
    }

    /**
     * 生成对等实体类
     *
     * @param classOfT              实体类
     * @param classPool             类池
     * @param ctEntityPeerInterface 接口
     */
    private void generateEntityProxies(Class<?> classOfT, ClassPool classPool, CtClass ctEntityPeerInterface) {
        try {
            CtClass ctClass = classPool.makeClass(PROXY_CLASS_NAME_TEMPLATE.formatted(classOfT.getName()));
            ctClass.addInterface(ctEntityPeerInterface);

            for (FunGenerator generator : generators) {
                ctClass.addMethod(CtMethod.make(generator.generate(classOfT), ctClass));
            }

            ctClass.toClass();
            ctClass.detach();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("generatePeerEntity error: {}", ex.getMessage());
        }
    }
}
