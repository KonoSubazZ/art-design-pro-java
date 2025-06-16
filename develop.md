# 项目结构

```text
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