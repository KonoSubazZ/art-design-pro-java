# Print Studio 编码规范

## 1. 命名规范

### 1.1 包命名

- 包名使用小写字母
- 包名使用单数形式
- 包名应该具有描述性
- 示例：`com.iboot.studio.core`, `com.iboot.studio.controller`

### 1.2 类命名

- 使用大驼峰命名法（UpperCamelCase）
- 类名应该是名词或名词短语
- 接口名可以是名词或形容词
- 示例：`PrintTemplate`, `PrintService`, `Printable`

### 1.3 方法命名

- 使用小驼峰命名法（lowerCamelCase）
- 方法名应该是动词或动词短语
- 示例：`generateTemplate()`, `renderPrint()`, `validateData()`

### 1.4 变量命名

- 使用小驼峰命名法（lowerCamelCase）
- 变量名应该是名词或名词短语
- 常量使用全大写，单词间用下划线分隔
- 示例：`printData`, `templateConfig`, `MAX_RETRY_COUNT`

## 2. 代码格式（Google Java Style）

### 2.1 源文件基础

- 文件名：使用 PascalCase 命名，如 `PrintTemplate.java`
- 文件编码：UTF-8
- 特殊字符：使用 ASCII 字符，避免使用 Unicode 转义

### 2.2 源文件结构

```java
/*
 * Proprietary Software License
 *
 * Copyright (c) 2025 iboot
 *
 * This software and its associated documentation ("Software") are proprietary property of iboot.
 * Without explicit written permission from iboot, no individual or entity may:
 *
 * 1. Copy, modify, merge, publish, distribute, sublicense, or sell copies of the Software;
 * 2. Reverse engineer, decompile, or disassemble the Software;
 * 3. Remove or alter any copyright notices or other proprietary markings in the Software;
 * 4. Use the Software for any commercial purposes.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * IBOOT BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * The Software may not be used without explicit written permission from iboot.
 * Author: tangsc.
 */

package com.iboot.studio.core;  // 包声明

/**
 * 类注释
 */
public class Example {
	// 静态变量
	private static final int CONSTANT = 1;

	// 实例变量
	private int instanceVar;

	// 构造函数
	public Example() {
		// ...
	}

	// 方法
	public void method() {
		// ...
	}
}
```

### 2.3 缩进

- 使用 2 个空格进行缩进（不使用 4 个空格）
- 不使用 Tab 字符
- 缩进示例：

```java
public void method() {
  if (condition) {
    doSomething();
  }
}
```

### 2.4 行长度

- 每行代码不超过 100 个字符
- 超出长度时在以下位置换行：
    - 在逗号后
    - 在操作符前
    - 在括号前
    - 在句点前

### 2.5 空行

- 类成员之间使用一个空行分隔
- 方法之间使用一个空行分隔
- 逻辑相关的代码块之间使用一个空行分隔
- 在方法内的逻辑块之间使用空行分隔

### 2.6 空格

- 关键字和括号之间使用空格
- 方法名和括号之间不使用空格
- 操作符两侧使用空格
- 逗号后使用空格
- 分号后不使用空格
- 类型转换的括号内不使用空格

### 2.7 括号

- if/for/while 等语句必须使用大括号
- 即使只有一条语句，也必须使用大括号
- 大括号使用 K&R 风格（左大括号不换行）

### 2.8 数组

- 数组初始化使用以下格式：

```java
int[] array = new int[] {1, 2, 3};
String[] strings = {"a", "b", "c"};
```

### 2.9 switch 语句

- 每个 case 后必须使用 break 或 return
- 必须包含 default 分支
- 使用缩进格式：

```java
switch (input) {
  case 1:
    doSomething();
    break;
  case 2:
    doSomethingElse();
    break;
  default:
    break;
}
```

### 2.10 注解

- 注解应该放在它们所注解的代码之前
- 多个注解应该按照字母顺序排列
- 示例：

```java
@Override
@Nullable
public String toString() {
  return "example";
}
```

### 2.11 注释

- 使用 `//` 进行单行注释
- 使用 `/* */` 进行多行注释
- 使用 `/** */` 进行文档注释
- 注释应该解释代码的"为什么"，而不是"是什么"

## 3. 注释规范

### 3.1 类注释

```java
/**
 * 类的功能描述
 *
 * @author 作者名
 * @since 版本号
 */
```

### 3.2 方法注释

```java
/**
 * 方法的功能描述
 *
 * @param param1 参数1的说明
 * @param param2 参数2的说明
 * @return 返回值的说明
 * @throws Exception 异常说明
 */
```

### 3.3 代码注释

- 使用 `//` 进行单行注释
- 复杂的代码块使用 `/* */` 进行多行注释
- 注释应该解释代码的"为什么"，而不是"是什么"

## 4. 异常处理

### 4.1 异常捕获

- 捕获具体的异常，而不是捕获 Exception
- 不要忽略异常，至少记录日志
- 在 finally 块中释放资源

### 4.2 异常抛出

- 使用有意义的异常信息
- 优先使用自定义异常
- 在接口文档中说明可能抛出的异常

## 5. 日志规范

### 5.1 日志级别

- ERROR：系统错误，影响系统运行
- WARN：警告信息，不影响系统运行
- INFO：重要业务信息
- DEBUG：调试信息
- TRACE：最详细的日志信息

### 5.2 日志格式

```java
log.error("操作失败，原因：{}",errorMessage, exception);
log.

info("处理完成，结果：{}",result);
```

## 6. 测试规范

### 6.1 单元测试

- 测试类名以 Test 结尾
- 测试方法名应该描述测试场景
- 使用 Given-When-Then 模式编写测试
- 测试覆盖率要求：行覆盖率 > 80%

### 6.2 测试命名

```java
@Test
void shouldReturnSuccessWhenValidDataProvided() {
    // 测试代码
}
```

## 7. 安全规范

### 7.1 数据安全

- 敏感数据必须加密存储
- 打印数据必须进行权限验证
- 避免在日志中打印敏感信息

### 7.2 接口安全

- 所有接口必须进行参数验证
- 使用 Spring Security 进行认证授权
- 实现防重放攻击机制

## 8. 性能规范

### 8.1 代码优化

- 避免在循环中创建对象
- 使用 StringBuilder 进行字符串拼接
- 合理使用缓存机制

### 8.2 资源管理

- 及时释放数据库连接
- 使用连接池管理资源
- 避免内存泄漏

## 9. 版本控制

### 9.1 提交规范

- 提交信息要清晰描述改动内容
- 使用统一的提交信息格式
- 具体规范：[Git提交规范1.0.0(在线)](https://www.conventionalcommits.org/zh-hans/v1.0.0/) | [Git提交规范1.0.0(本地)](Git约定提交规范1.0.0.md)
- 常见提交示例：
```markdown
* feat: 新功能
* fix: 修复 Bug
* revert: 版本回退
* build: 用于修改项目构建系统，例如修改依赖库、外部接口或者升级 Node 版本等；
* chore: 用于对非业务性代码进行修改，例如修改构建流程或者工具配置等；
* ci: 用于修改持续集成流程，例如修改 Travis、Jenkins 等工作流配置；
* docs: 用于修改文档，例如修改 README 文件、API 文档等；
* style: 用于修改代码的样式，例如调整缩进、空格、空行等；
* refactor: 用于重构代码，例如修改代码结构、变量名、函数名等但不修改功能逻辑；
* perf: 用于优化性能，例如提升代码的性能、减少内存占用等；
* test: 用于修改测试用例，例如添加、删除、修改代码的测试用例等。
```

### 9.2 分支管理

- 主分支：main
- 开发分支：develop
- 功能分支：feature/*
- 修复分支：hotfix/*
- 发布分支：release/*
