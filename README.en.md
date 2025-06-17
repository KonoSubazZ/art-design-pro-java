[中文](README.md) | [English](README.en.md)
# Iboot Studio —— Java Enterprise Backend Management System

This is a "simple but not simple" Java enterprise backend management system. Example website: [www.art-design-pro.iboot.top](http://117.72.212.138:18080/)

## Overview

iboot studio was created while learning the frontend project [Art Design Pro](https://github.com/Daymychen/art-design-pro) to implement real backend development.

## Matching Frontend

The [art-design-pro-iboot](https://github.com/anganing/art-design-pro-iboot) frontend project is forked from [Art Design Pro](https://github.com/Daymychen/art-design-pro) and modified to meet backend development requirements.

## Features
- Partial backend interface implementation for [Art Design Pro](https://github.com/Daymychen/art-design-pro)
- Standard RESTful API implementation
- Secure authentication and authorization (dual validation with frontend dynamic control and backend dynamic control)
- Multi-database compatibility (supports all databases supported by Mybatis-Plus)
- Configurable request-response logging (records request data, response data, and request cURL)
- Configurable enum dictionary interface (no need to rewrite data dictionaries)
- Docker image support
- Frontend can use Nginx reverse proxy or the project's built-in SpringBoot Tomcat (the project has copied the latest frontend dist directory to resources/static)
- ... (more updates to come, welcome to explore and contribute)

## Running the Project

### Source Code Run

Requirements:

- Java 17+
- Maven 3.8+

```bash
git clone https://github.com/anganing/art-design-pro-java.git
cd art-design-pro-java
mvn spring-boot:run
```

### Docker Run
Docker Image: [ibootio/iboot-studio](https://hub.docker.com/r/ibootio/iboot-studio)

```bash
# Pull image
docker pull ibootio/iboot-studio
# Run container
docker run -d --name iboot-studio -p 18080:18080 ibootio/iboot-studio
# Access http://localhost:18080
```

## Technology Stack
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

## Project Structure

```plaintext
iboot-studio
├── src/main/java/com/iboot/studio/    # Main package directory
│   ├── common/                        # Common module
│   │   ├── annotation/                # Custom annotations
│   │   ├── aspect/                    # AOP aspects
│   │   ├── config/                    # Configuration classes
│   │   ├── constant/                  # Constant classes
│   │   ├── enumdict/                  # Enum dictionaries
│   │   ├── exception/                 # Exception classes
│   │   ├── handler/                   # Handlers
│   │   ├── util/                      # Utility classes
│   │   └── wrapper/                   # Wrappers
│   ├── service/                       # Service layer
│   │   ├── impl/                      # Service implementations
│   │   ├── UserService.java           # User service interface
│   │   ├── RoleService.java           # Role service interface
│   │   ├── ResourceService.java       # Resource service interface
│   │   ├── SysDataConfigService.java  # System data config service interface
│   │   └── AuthService.java           # Authentication service interface
│   ├── web/                           # Web layer
│   │   ├── controller/                # Controllers
│   │   ├── dto/                       # Data transfer objects
│   │   └── vo/                        # View objects
│   └── infrastructure/                # Infrastructure layer
│       ├── persistence/               # Persistence implementation
│       │   ├── entity/                # Database entities
│       │   └── repository/            # Data repositories
│       └── integration/               # Integration layer
│           ├── satoken/               # Sa-Token integration
│           ├── mybatisplus/           # MyBatis-Plus integration
│           ├── concept/               # Concept integration
│           └── minioplus/             # Minio-Plus integration
├── src/main/resources/                # Resource files directory
├── src/test/                          # Test code directory
├── database/                          # Database related files
├── logs/                              # Log files
├── target/                            # Maven build output
├── .idea/                             # IntelliJ IDEA configuration
├── .git/                              # Git version control
├── .style/                            # Code style configuration
├── pom.xml                            # Maven project configuration
├── Dockerfile                         # Docker build
├── README.md                          # Project documentation (Chinese)
├── README.en-US.md                    # Project documentation (English)
├── LICENSE                            # Open source license file
├── develop.md                         # Development documentation
├── lombok.config                      # Lombok configuration
├── .gitignore                         # Git ignore configuration
└── .gitattributes                     # Git attributes configuration
```

## Document
The project documentation will be updated on the following platforms (priority: WeChat Official Account, other platforms based on time):
- ![iboot WeChat Official Account](src/main/resources/static/iboot/iboot_wx_pub.jpg)
- [Juejin](https://juejin.cn/user/2928754709504893)
- [iboot Website](http://www.iboot.top)
- [iboot Blog](http://blog.iboot.top)

## Contribution
Welcome to contribute code, please refer to the [develop.md](develop.md) document and [.style](.style) code style configuration.

## Sponsorship
Your sponsorship will be used to maintain website operation, system maintenance, document updates, project maintenance, etc., thank you for your support!

|                 WeChat Pay                 |                  Alipay                  |
|:------------------------------------------:|:----------------------------------------:|
| <img src="/payment/微信支付.jpg" alt="微信支付" /> | <img src="/payment/支付宝.jpg" alt="支付宝" /> |

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
