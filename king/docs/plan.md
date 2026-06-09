# plan.md —— 王者荣耀信息管理系统

---

## 1. 项目目标

本项目使用 Java 面向对象编程实现一个“王者荣耀信息管理系统”。系统围绕玩家、英雄、装备、战队和比赛记录组织数据，提供登录认证、信息查询、排名统计、数据管理和文件持久化等功能。

系统包含两类用户：

| 用户类型 | 权限说明 |
|---|---|
| `Admin` | 可以查看公共信息，管理玩家、英雄、战队、比赛记录等系统数据，并保存数据 |
| `Player` | 可以登录系统，查看公共信息、排名信息和自己的个人资料 |

开发语言：Java  
运行方式：控制台菜单程序  
主入口：`Main.java`

---

## 2. 需求分析

| 编号 | 功能 | 当前实现方式 | 状态 |
|---|---|---|---|
| F1 | Player Query | 支持按玩家 ID、姓名、战队查询玩家信息 | 已实现 |
| F2 | Team Overview | 显示战队成员、平均等级、比赛数量、胜率和最佳玩家 | 已实现 |
| F3 | Hero Details | 支持按英雄 ID 和英雄类型查看英雄属性、适配装备和拥有者 | 已实现 |
| F4 | Equipment Statistics | 显示装备类型、攻击加成、防御加成等装备信息 | 已实现 |
| F5 | Match History | 支持按战队查看比赛记录，并显示比赛结果和胜者 | 已实现 |
| F6 | Ranking List | 支持玩家胜率、等级、比赛数量、英雄数量排名；支持战队胜率、平均等级、总比赛数排名 | 已实现 |
| F7 | Data Management | 管理员可以添加/删除玩家、英雄、战队、比赛记录；玩家可以查看个人信息 | 已实现 |
| F8 | Data Persistence | 使用 Java 对象序列化保存和加载 `GameDataManager` | 已实现 |
| F9 | Login Authentication | 使用 ID 和密码登录，区分 `Admin` 与 `Player` 两种角色 | 已实现 |

---

## 3. Java 概念使用情况

| Java 概念 | 项目中的体现 |
|---|---|
| 继承 | `Person` 是抽象父类，`Player` 和 `Admin` 继承 `Person` |
| 抽象类 | `Person` 定义公共属性和抽象方法 `login()`、`getRole()` |
| 接口 | `Searchable` 统一提供 `getId()` 和 `getName()`；`Equipment` 定义装备对象的公共行为 |
| 多态 | `AuthenticationService` 使用 `Person` 统一处理 `Player` 和 `Admin` 登录对象 |
| 封装 | 各实体类字段使用 `private`，通过 getter/setter 或业务方法访问 |
| 集合 | 使用 `ArrayList` 保存玩家、英雄、装备、战队、比赛记录和管理员数据 |
| 枚举 | 使用 `HeroType`、`EquipmentType`、`MatchResult` 表示固定类型 |
| 异常处理 | 输入转换、英雄类型转换、文件保存/加载中使用 `try-catch` 处理异常 |
| 文件持久化 | `FileStorageService` 使用 `ObjectOutputStream` 和 `ObjectInputStream` 保存/加载数据 |
| 双向关联 | `Player ↔ Hero`、`Player ↔ Team` 通过 add/remove 方法保持同步 |

---

## 4. 类设计

### 4.1 model 包

| 类名 | 职责 |
|---|---|
| `Searchable` | 定义可搜索对象的公共方法 `getId()` 和 `getName()` |
| `Person` | 抽象父类，保存 id、name、password，并定义登录和角色方法 |
| `Player` | 保存玩家等级、胜率、拥有英雄列表和所属战队 |
| `Admin` | 表示管理员用户，拥有系统数据管理权限 |
| `Hero` | 保存英雄类型、属性、适配装备列表和拥有该英雄的玩家列表 |
| `Equipment` | 装备接口，定义装备 ID、名称、类型和属性加成 |
| `EquipmentItem` | 装备接口的具体实现类 |
| `Team` | 保存战队 ID、名称、成员列表和比赛历史 |
| `MatchRecord` | 保存比赛 ID、参赛战队、比赛结果、胜者、日期和出场英雄 |
| `HeroType` | 英雄类型枚举 |
| `EquipmentType` | 装备类型枚举 |
| `MatchResult` | 比赛结果枚举 |

### 4.2 service 包

| 类名 | 职责 |
|---|---|
| `GameDataManager` | 系统数据中心，负责保存集合数据并提供基础增删查操作 |
| `AuthenticationService` | 负责登录、登出、当前用户状态和角色判断 |
| `SearchService` | 负责玩家、英雄、战队、比赛等查询功能 |
| `RankingService` | 负责玩家排名和战队排名 |
| `FileStorageService` | 负责将系统数据保存到文件，并在启动时加载 |

### 4.3 util 包

| 类名 | 职责 |
|---|---|
| `DataInitializer` | 创建初始测试数据，包括战队、玩家、英雄、装备、比赛记录和管理员 |
| `InputHelper` | 统一处理控制台输入、数字读取和暂停操作 |

### 4.4 主程序

| 类名 | 职责 |
|---|---|
| `Main` | 控制程序启动、登录菜单、玩家菜单、管理员菜单和各功能入口 |

---

## 5. UML 草图

```text
                       <<interface>>
                       Searchable
                    + getId()
                    + getName()
                            ▲
                            │
             ┌──────────────┴──────────────┐
             │                             │
       ┌────────────┐                ┌──────────┐
       │  Person    │                │   Hero   │
       │ (abstract) │                │──────────│
       │────────────│                │ - id     │
       │ - id       │                │ - name   │
       │ - name     │                │ - type   │
       │ - password │                │ - stats  │
       └─────┬──────┘                └────┬─────┘
             │                            │
      ┌──────┴──────┐                     │ aggregation
      │             │                     ▼
 ┌─────────┐   ┌─────────┐          <<interface>>
 │ Player  │   │  Admin  │           Equipment
 │─────────│   │─────────│              ▲
 │ - level │   │ role    │              │
 │ - heroes│   └─────────┘       ┌───────────────┐
 │ - team  │                     │ EquipmentItem │
 └────┬────┘                     └───────────────┘
      │
      │ belongs to
      ▼
 ┌─────────┐       has history       ┌─────────────┐
 │  Team   │────────────────────────▶│ MatchRecord │
 │─────────│                         │─────────────│
 │ - name  │                         │ - teamA     │
 │ - players                         │ - teamB     │
 │ - matches                         │ - result    │
 └─────────┘                         │ - winner    │
                                     │ - date      │
                                     └─────────────┘
```

---

## 6. 数据设计

### 6.1 初始数据

| 数据类型 | 数量 | 创建位置 |
|---|---:|---|
| Team | 3 | `DataInitializer` |
| Player | 10 | `DataInitializer` |
| Hero | 15 | `DataInitializer` |
| Equipment | 20 | `DataInitializer` |
| MatchRecord | 10 | `DataInitializer` |
| Admin | 2 | `DataInitializer` |

### 6.2 数据关系

| 关系 | 说明 |
|---|---|
| `Player → Team` | 一个玩家最多属于一个战队 |
| `Team → Player` | 一个战队包含多个玩家 |
| `Player → Hero` | 一个玩家可以拥有多个英雄 |
| `Hero → Player` | 一个英雄可以被多个玩家拥有 |
| `Hero → Equipment` | 一个英雄可以适配多个装备 |
| `Team → MatchRecord` | 一个战队可以拥有多条比赛记录 |
| `MatchRecord → Team` | 一条比赛记录关联两个参赛战队 |

### 6.3 数据存储方式

系统启动时先调用 `FileStorageService.loadAll()` 尝试加载本地数据。如果没有保存文件，系统使用 `DataInitializer` 创建初始数据。

系统保存时调用 `FileStorageService.saveAll(dataManager)`，将完整的 `GameDataManager` 对象序列化到：

```text
data/game_data.dat
```

使用对象序列化的原因是当前项目存在较多对象关系，例如 `Player ↔ Team`、`Player ↔ Hero`。保存整个 `GameDataManager` 可以保留这些对象关系，避免手动拆分和重建关系。

---

## 7. 功能菜单设计

### 7.1 未登录菜单

| 选项 | 功能 |
|---|---|
| 1 | Login |
| 2 | Register (New Player) |
| 0 | Exit |

### 7.2 Player 菜单

| 选项 | 功能 |
|---|---|
| 1 | Search Player |
| 2 | Team Overview |
| 3 | Hero Details |
| 4 | Equipment Stats |
| 5 | Match History |
| 6 | Rankings |
| 7 | My Profile |
| 0 | Logout |

### 7.3 Admin 菜单

| 选项 | 功能 |
|---|---|
| 1 | Search Player |
| 2 | Team Overview |
| 3 | Hero Details |
| 4 | Equipment Stats |
| 5 | Match History |
| 6 | Rankings |
| 7 | Data Management (Add/Remove) |
| 8 | Save Data |
| 0 | Logout |

---

## 8. AI 使用计划

| AI 角色 | 使用范围 | 限制 |
|---|---|---|
| Architect Agent | 提供类设计、服务层划分、UML 结构建议 | 最终设计由本人确认 |
| Implementation Agent | 协助实现单个类、方法或局部功能 | 不直接一次性生成整个项目 |
| Testing/Reviewer Agent | 协助设计测试用例、检查代码错误、验证功能结果 | 测试结论需要本人复核 |

个人 AI 使用记录：

> 由学生本人填写。

---

## 9. Prompt Strategy

本项目使用 AI 时主要采用以下方式：

1. 先说明当前已经完成的代码和文件结构。
2. 明确指定 AI 的角色，例如架构师、实现智能体、测试智能体。
3. 限制 AI 每次只处理一个明确任务，例如实现一个类或测试一个功能。
4. 要求 AI 解释代码作用，便于本人理解和修改。
5. 对 AI 生成的代码进行编译和测试，再决定是否保留。

个人 prompt 示例与记录：

> 由学生本人填写。

---

## 10. Development Timeline

| 阶段 | 内容 | 状态 |
|---|---|---|
| Stage 1 | 需求分析，建立项目结构，编写初始 `plan.md` | 已完成 |
| Stage 2 | 设计 model 包类结构和 UML 草图 | 已完成 |
| Stage 3 | 实现实体类、枚举、接口和双向关联 | 已完成 |
| Stage 4 | 实现 `DataInitializer` 初始数据 | 已完成 |
| Stage 5 | 实现 service 包，包括认证、查询、排名和数据管理 | 已完成 |
| Stage 6 | 实现控制台菜单、管理员菜单和玩家菜单 | 已完成 |
| Stage 7 | 实现文件持久化保存和加载 | 已完成 |
| Stage 8 | 使用测试智能体和人工测试验证主要功能 | 已完成 |
| Stage 9 | 整理测试文档、项目说明和最终反思 | 进行中 |

---

## 11. Testing Plan

### 11.1 测试范围

| 测试范围 | 测试内容 | 记录位置 |
|---|---|---|
| Login / Logout | 管理员和玩家登录、登出、错误密码、错误 ID | `docs/test-cases.md` |
| Role-Based Access | 管理员和玩家权限判断 | `docs/test-cases.md` |
| Search Functions | 玩家、战队、英雄、装备、比赛查询 | `docs/test-cases.md` |
| Rankings | 玩家排名和战队排名 | `docs/test-cases.md` |
| Data Management | 管理员添加/删除数据，玩家查看个人信息 | `docs/test-cases.md` |
| Bidirectional Sync | 玩家-战队、玩家-英雄双向同步 | `docs/test-cases.md` |
| Persistence | 保存和加载 `GameDataManager` | `docs/test-cases.md` |
| Edge Cases | 空值、错误输入、不存在 ID、平局比赛等 | `docs/test-cases.md` |
| Manual Menu Test | 人工测试菜单功能 1 到 6 | `docs/test-cases.md` |

### 11.2 测试方法

项目测试使用两种方式：

1. 临时 Java 测试文件：用于快速验证服务类和模型类逻辑，测试后删除，不保留在 `src` 中。
2. 人工菜单测试：运行 `Main.java`，通过控制台输入验证菜单功能。

测试记录保存在：

```text
docs/test-cases.md
```

测试代码不作为最终提交内容保留。

人工测试细节：

> 由学生本人填写。

---

## 12. 风险分析

| 风险 | 影响 | 处理方式 |
|---|---|---|
| 双向关联不同步 | 玩家、英雄、战队数据可能不一致 | 在 add/remove 方法中同步维护两侧关系 |
| 文件保存失败 | 程序重启后数据丢失 | 使用 `try-catch` 捕获异常，并在测试中验证保存/加载 |
| 输入非法数据 | 程序可能异常退出 | 使用 `InputHelper` 统一处理输入 |
| 角色权限混淆 | 玩家可能访问管理员功能 | 菜单层区分 Admin 和 Player |
| AI 生成代码不准确 | 可能出现编译错误或逻辑错误 | 每次修改后编译运行，并记录测试结果 |

---

## 13. Final Reflection Placeholder

> 由学生本人填写。

---

*version：2.0*  
*Time：2026-06-09*
