[中文](README.md) | [English](README.en.md)
# Iboot Studio —— Java企业级后台管理系统

这是一个说简单但是又不"简单的" Java 企业级后台管理系统。示例网站：[www.art-design-pro.iboot.top](http://117.72.212.138:18080/)

## 概述

iboot studio 是在学习前端项目 [Art Design Pro](https://github.com/Daymychen/art-design-pro) 时，为了对接真实的后端开发而创建的。

## 配套前端

[art-design-pro-iboot](https://github.com/anganing/art-design-pro-iboot) 前端项目 Fork 自 [Art Design Pro](https://github.com/Daymychen/art-design-pro)，并在此基础上进行了修改，以满足后端开发的需求。


## 特性
- [Art Design Pro](https://github.com/Daymychen/art-design-pro) 部分后端接口实现
- 标准 RESTful API 实现
- 安全的身份验证和授权（前端动态控制、后端动态控制双重校验）
- 多数据库兼容（Mybatis-Plus支持的数据库它都支持）
- 可配置化请求响应日志记录（请求数据是什么、响应数据是什么、请求的cURL通通记录）
- 可配置化枚举字典接口（无需再重复写数据字典）
- Docker 镜像支持
- 前端可用Nginx反向代理也可用本项目的SpringBoot内嵌的Tomcat（本项目已经将最新前端打包后的dist目录复制到resources/static目录下）
- ...（后续会持续更新，欢迎探索、贡献）

## 运行项目

### 源码运行

环境要求：

- Java 17+
- Maven 3.8+

```bash
git clone https://github.com/anganing/art-design-pro-java.git
cd art-design-pro-java
mvn spring-boot:run
```

### Docker 运行
Docker 镜像：[ibootio/iboot-studio](https://hub.docker.com/r/ibootio/iboot-studio)

```bash
# 拉取镜像
docker pull ibootio/iboot-studio
# 运行容器
docker run -d --name iboot-studio -p 18080:18080 ibootio/iboot-studio
# 访问 http://localhost:18080
```

## 技术栈
- Spring Boot 3.3.0
- Sa-Token 1.44.0
- Maven 3.8.1
- SQLite 3.49.1.0
- Mybatis-Plus 3.5.12
- Mybatis-Plus-Join 1.5.3
- BeanSearcher 4.4.1
- Retrofit 3.1.7
- Lombok 1.18.24
- Disruptor 3.4.4
- Hutool 5.8.38
- Minio-Plus 1.0.5
- Concept-Download 2.1.0
- Ulid-Creator 5.2.3
- ...

## 项目结构

```plaintext
iboot-studio
├── src/main/java/com/iboot/studio/    # 主包目录
│   ├── common/                        # 通用模块
│   │   ├── annotation/                # 自定义注解
│   │   ├── aspect/                    # AOP切面
│   │   ├── config/                    # 配置类
│   │   ├── constant/                  # 常量类
│   │   ├── enumdict/                  # 枚举字典
│   │   ├── exception/                 # 异常类
│   │   ├── handler/                   # 处理器
│   │   ├── util/                      # 工具类
│   │   └── wrapper/                   # 包装器
│   ├── service/                       # 服务层
│   │   ├── impl/                      # 服务实现类
│   │   ├── UserService.java           # 用户服务接口
│   │   ├── RoleService.java           # 角色服务接口
│   │   ├── ResourceService.java       # 资源服务接口
│   │   ├── SysDataConfigService.java  # 系统数据配置服务接口
│   │   └── AuthService.java           # 认证服务接口
│   ├── web/                           # Web层
│   │   ├── controller/                # 控制器
│   │   ├── dto/                       # 数据传输对象
│   │   └── vo/                        # 视图对象
│   └── infrastructure/                # 基础设施层
│       ├── persistence/               # 持久化实现
│       │   ├── entity/                # 数据库实体类
│       │   └── repository/            # 数据仓库
│       └── integration/               # 集成层
│           ├── satoken/               # Sa-Token集成
│           ├── mybatisplus/           # MyBatis-Plus集成
│           ├── concept/               # Concept集成
│           └── minioplus/             # Minio-Plus集成
├── src/main/resources/                # 资源文件目录
├── src/test/                          # 测试代码目录
├── database/                          # 数据库相关文件
├── logs/                              # 日志文件
├── target/                            # Maven 构建输出
├── .idea/                             # IntelliJ IDEA 配置
├── .git/                              # Git 版本控制
├── .style/                            # 代码风格配置
├── pom.xml                            # Maven 项目配置
├── Dockerfile                         # Docker 构建
├── README.md                          # 项目说明文档（中文）
├── README.en-US.md                    # 项目说明文档（英文）
├── LICENSE                            # 开源许可证文件
├── develop.md                         # 开发文档
├── lombok.config                      # Lombok 配置文件
├── .gitignore                         # Git 忽略文件配置
└── .gitattributes                     # Git 属性配置文件
```

## 文档
后续项目文档更新将会在以下平台发布（优先：微信公众号，其他平台看时间）：
- ![iboot微信公众号](src/main/resources/static/iboot/iboot_wx_pub.jpg)
- [掘金](https://juejin.cn/user/2928754709504893)
- [iboot官网](http://www.iboot.top)
- [iboot博客](http://www.blog.iboot.top)

## 贡献
欢迎贡献代码，请参考 [develop.md](develop.md) 文档及[.style](.style) 代码风格配置。

## 赞助
你的赞助将用于维护网站运行、系统维护、文档更新、项目维护等，感谢你的支持！

| 微信支付 | 支付宝 |
|:---:|:---:|
| <img src="/payment/微信支付.jpg" alt="微信支付" width="200" height="200"> | <img src="/payment/支付宝.jpg" alt="支付宝" width="200" height="200"> |

## 许可证

本项目采用 MIT 许可证 - 详情请查看 [LICENSE](LICENSE) 文件
