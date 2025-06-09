# 项目结构

```text
src/main/java/com/iboot/print/
├── common/                           # 通用模块
│   ├── config/                       # 配置类
│   ├── constant/                     # 常量类
│   ├── exception/                    # 异常类
│   └── util/                         # 工具类
├── service/                          # 服务层
│   └── impl/                         # 服务实现类
├── web/                              # Web层
│   ├── controller/                   # 控制器
│   ├── model/                        # 请求响应模型
│   ├── dto/                          # 数据传输对象
│   └── vo/                           # 视图对象
└── infrastructure/                   # 基础设施层
    ├── persistence/                  # 持久化实现
    │   ├── entity/                   # 数据库实体类
    │   └── mapper/                   # MyBatis Mapper接口
    └── integration/                  # 外部集成
```