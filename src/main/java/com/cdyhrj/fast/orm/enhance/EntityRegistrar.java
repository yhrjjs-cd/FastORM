package com.cdyhrj.fast.orm.enhance;

import com.cdyhrj.fast.orm.EnableFastORM;
import com.cdyhrj.fast.orm.annotation.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 实体注册
 *
 * @author huangqi
 */
@Slf4j
public class EntityRegistrar implements ImportSelector, ResourceLoaderAware {
    //测试环境下，会多次注册，导致出错
    public static boolean isRegistered = false;
    private boolean doRegister = true;
    private ResourcePatternResolver resourcePatternResolver;
    private CachingMetadataReaderFactory cachingMetadataReaderFactory;
    private final EntityClassEnhancer entityClassEnhancer = new EntityClassEnhancer();
    private static final String DEFAULT_RESOURCE_PATTERN = "/**/*.class";

    public EntityRegistrar() {
        if (isRegistered) {
            this.doRegister = false;

            return;
        }

        isRegistered = true;

        if (log.isInfoEnabled()) {
            log.info("FastORM Enhance entities.");
        }
    }

    @Override
    public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
        if (!this.doRegister) {
            return;
        }
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.cachingMetadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    @Override
    @NonNull
    public String[] selectImports(@NonNull AnnotationMetadata importingClassMetadata) {
        if (!this.doRegister) {
            return new String[0];
        }

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableFastORM.class.getName()));

        if (Objects.nonNull(attributes)) {
            Class<?>[] scanPackageClasses = attributes.getClassArray("scanPackageClasses");
            Set<String> entityClasses = new HashSet<>();

            for (Class<?> classOfT : scanPackageClasses) {
                scanForEnhanceEntityPackage(classOfT.getPackageName(), entityClasses);
            }

            entityClassEnhancer.enhance(entityClasses.toArray(new String[0]));
        }

        return new String[0];
    }

    private void scanForEnhanceEntityPackage(String packageName, Set<String> toEntityClassesSet) {
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(packageName) + DEFAULT_RESOURCE_PATTERN;

        try {
            Resource[] resources = this.resourcePatternResolver.getResources(pattern);

            for (Resource resource : resources) {
                MetadataReader metadataReader = this.cachingMetadataReaderFactory.getMetadataReader(resource);
                if (metadataReader.getAnnotationMetadata().hasAnnotation(Table.class.getName())) {
                    String className = metadataReader.getClassMetadata().getClassName();

                    toEntityClassesSet.add(className);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Scan entity class error！");
        }
    }
}
