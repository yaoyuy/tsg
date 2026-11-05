# 图书馆管理系统（tsg）

一门课程设计做出来的完整图书馆系统：读者能查书、借书、续借、预约、写书评、查罚款；管理员能管书、管人、管借还、盯超期、看统计。

Java 17 + Spring Boot 3.2 + MyBatis + Thymeleaf + MySQL，服务端渲染，没有前端构建步骤，配好数据库就能跑。

## 功能

### 读者端 `/user`

- 注册 / 登录 / 改资料 / 改密码
- 书目检索、图书详情、分类与标签
- 借书、续借、预约、收藏
- 借阅历史、当前借阅、预约记录
- 书评、站内消息、罚款查询

### 管理端 `/admin`

- 图书 / 分类 / 标签管理
- 读者与管理员账号管理
- 借还登记与在借清单
- 超期清单、罚款管理
- 预约管理与书评审核
- 公告发布
- 统计看板
- 数据导入（`DataPreprocessController`）

## 技术栈

| 层 | 选型 |
|---|---|
| 语言 | Java 17 |
| 框架 | Spring Boot 3.2.0 |
| 持久层 | MyBatis（XML Mapper） |
| 视图 | Thymeleaf |
| 数据库 | MySQL 8 |
| 构建 | Maven（自带 wrapper） |

## 快速开始

**1. 建库并导入表结构**（库名 `library_management`）：

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS library_management DEFAULT CHARSET utf8mb4"
mysql -u root -p library_management < src/main/resources/db/schema.sql
```

**2. 配置数据库账号**（不写死在配置文件里）：

```bash
# Windows
set DB_USERNAME=root
set DB_PASSWORD=你的密码

# macOS / Linux
export DB_USERNAME=root
export DB_PASSWORD=你的密码
```

**3. 启动**：

```bash
./mvnw spring-boot:run        # macOS / Linux
mvnw.cmd spring-boot:run      # Windows
```

**4. 打开** http://localhost:8080 —— 读者端 `/user/login`，管理端 `/admin/login`。

## 目录结构

```
src/main/java/com/ehxy/tsg
  controller/   页面与表单入口
  service/      业务逻辑
  mapper/       MyBatis 接口
  entity/       14 个实体
  config/       MyBatis 配置
src/main/resources
  templates/    Thymeleaf 页面（管理端 22 个，读者端 10 个）
  db/schema.sql 建表脚本
  mapper/       SQL 映射
```

## 说明

课程设计作品，功能覆盖完整，但**没有按生产标准做安全加固**：未启用 CSRF 防护、权限校验是简化实现、未处理并发借阅。请勿直接用于生产环境。

## License

MIT
