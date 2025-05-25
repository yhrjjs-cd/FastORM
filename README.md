### FastORM：基于Java的轻量级Spring ORM框架

#### 一、框架定位与核心特性
FastORM是一款专为**Spring生态系统**设计的**对象关系映射（ORM）工具**，旨在通过**简化数据库操作**、**提升开发效率**并**保持灵活性**，帮助Java开发者快速实现数据持久化逻辑。其核心特性包括：

1. **极简配置与约定优先**  
   - 无需编写复杂的XML或注解配置，通过**命名约定**（如类名映射表名、字段名映射列名）自动完成对象与数据库表的映射。
   - 支持少量注解（如`@Table`、`@Column`）自定义映射规则，兼顾约定与灵活性。

2. **流式API与链式操作**  
   - 提供**流式查询构建器**，通过链式调用（如`fastORM.select().from(User.class).where("age > ?", 18).orderBy("create_time DESC")`）组合查询条件，避免拼接SQL字符串的繁琐与安全隐患。
   - 支持动态条件（如`when(condition).then(where("status", "ACTIVE"))`），适配复杂业务逻辑。

3. **性能优化与自动缓存**  
   - 内置**一级缓存**（基于ThreadLocal），自动缓存单例查询结果，减少重复数据库访问。
   - 支持分页查询（`page(int pageNum, int pageSize)`）与批量操作（`batchInsert()`、`batchUpdate()`），提升大数据量场景下的性能。

4. **多数据库兼容**  
   - 支持主流关系型数据库（MySQL、PostgreSQL、Oracle、SQL Server等），通过底层数据源切换实现跨数据库移植。
   - 自动适配不同数据库的SQL语法差异（如分页语法、函数调用）。

5. **Spring无缝集成**  
   - 作为Spring Boot Starter组件，可通过`@Autowired`直接注入`FastORM`实例，与Spring事务管理（`@Transactional`）深度整合。
   - 支持与Spring Data JPA混合使用，兼容现有JPA仓库代码。


#### 二、核心功能模块
| **模块**         | **功能描述**                                                                 |
|------------------|-----------------------------------------------------------------------------|
| **对象映射**     | 自动将Java对象映射为数据库表，支持继承关系（单表继承、Joined继承）与枚举类型映射。 |
| **CRUD操作**    | 提供`insert()`、`update()`、`delete()`、`selectOne()`、`selectList()`等基础方法，支持主键自动生成（如UUID、自增ID）。 |
| **动态查询**     | 通过`Condition`对象或Lambda表达式动态拼接WHERE条件，避免SQL注入风险。          |
| **事务管理**     | 支持声明式事务（`@Transactional`）与编程式事务，确保数据一致性。                |
| **分页与排序**   | 内置`PageResult`类，支持物理分页（通过数据库原生分页语句）与自定义排序规则。    |
| **批量操作**     | 支持批量插入（`batchInsert(Collection<?>)`）与批量更新，减少数据库交互次数。   |
| **原生SQL兼容** | 提供`executeNativeSQL(String sql, Object... params)`方法，保留对原生SQL的控制权。 |


#### 三、典型应用场景
1. **快速开发业务系统**  
   在中小型企业应用（如OA、CRM、电商后台）中，可大幅减少数据层代码量，聚焦业务逻辑实现。

2. **替代传统ORM工具**  
   作为MyBatis或Hibernate的轻量级替代品，适合追求简单高效、避免过度配置的开发团队。

3. **微服务架构**  
   与Spring Cloud生态集成，支持分布式事务（需结合外部事务管理器如Seata），适配微服务场景下的多数据源需求。

4. **数据迁移与ETL**  
   利用批量操作与多数据库兼容特性，可快速实现数据在不同数据库之间的迁移或清洗。


#### 四、与其他ORM框架对比
| **维度**         | **FastORM**                | **MyBatis**                | **Hibernate**              |
|------------------|----------------------------|----------------------------|----------------------------|
| **学习成本**     | 低（约定优先，API简洁）     | 中（需掌握SQL映射与动态SQL）| 高（复杂配置与JPA规范）     |
| **灵活性**       | 中（流式API+少量注解）      | 高（原生SQL完全可控）       | 低（全自动化映射，调整成本高）|
| **性能**         | 优（一级缓存+批量操作）     | 优（按需优化SQL）           | 中（默认自动生成SQL可能低效）|
| **适用场景**     | 快速业务开发、轻量级系统     | 复杂查询、性能敏感系统       | 领域模型驱动的大型系统       |


#### 五、入门示例
```java
// 注入FastORM实例
@Autowired
private FastORM fastORM;

// 插入数据
User user = new User("Alice", 25, "active");
fastORM.insert(user); // 自动生成主键并回填到对象

// 查询数据
List<User> users = fastORM.select()
    .from(User.class)
    .where("age > ? AND status = ?", 18, "active")
    .orderBy("create_time DESC")
    .page(1, 10) // 分页查询，第1页，每页10条
    .list();

// 更新数据
fastORM.update(User.class)
    .set("status", "inactive")
    .where("id = ?", userId)
    .execute();

// 批量插入
List<User> batchUsers = ...; // 批量数据集合
fastORM.batchInsert(batchUsers, 100); // 分批次插入，每批100条
```