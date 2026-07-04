# ArcStory 重构架构与计划

---

## 一、现状分析

### 1.1 master 分支遗留代码的问题

| 问题 | 说明 |
|------|------|
| **单体架构** | 所有 UI 和逻辑混在少数几个类中（`SetStage.java` ~860 行, `StoryPlayer.java` ~630 行, `ChapterPane.java` ~650 行） |
| **内部类泛滥** | `StoryButton`、`StoryButtonPane`、`TextPlayer`、`Item`、`Loading` 等全部是内部类，测试和复用几乎不可能 |
| **数据硬编码** | 谱面（`Charts.java`）、搭档（`Partners.java`）、资源路径（`Resources.java`）全部是静态常量，扩展需改代码 |
| **故事数据格式简陋** | 当前 JSON 只是一个 `{type, path}` 数组，不支持分支/选择、角色对话、BGM 切换、过渡效果等 |
| **动画散落各处** | `Timeline`、`FadeTransition` 等直接在各个类的 `play()` 方法中创建，无复用 |
| **状态管理缺失** | 无集中式游戏状态（解锁进度、已读标记），依赖 UI 零散字段 |
| **无测试** | 所有逻辑与 JavaFX UI 紧密耦合，完全无法单元测试 |
| **资源管理脆弱** | `Resources.ofString()` 用 `String.valueOf()` 包裹 URL 对象（会变成 `"null"` 如果 path 无效），无优雅降级 |
| **同步问题** | 多处直接调用 `Screen.getPrimary().getVisualBounds()` 而非使用已有的 `ScreenMetrics` |

### 1.2 保留的部分（可用）

- **数据模型类**：`Chart`、`Difficulty`、`DifficultyLevel`、`Paradigms`、`Partner`（设计合理，仅需从 `model` 包移入新包）
- **工具类**：`ScreenMetrics`、`MathUtil`、`Interpolators`、`Alerts`（质量尚可，可直接复用）
- **资源文件**：音频、字体、图片、故事 JSON 和文本文件（全部保留路径不变）
- **依赖**：Jackson、JavaFX（controls/media）、JetBrains Annotations（不变）

---

## 二、原始 master 功能清单（需全部复现）

### 2.1 核心视觉小说引擎

| 功能 | 说明 |
|------|------|
| **CG 展示** | 全宽图片展示 + 扫线揭露动画（clip polygon 平移 + 发光效果 + 亮度调节） |
| **文字播放** | 打字机效果逐字显示，字体 NotoSansCJKsc，尺寸 = 对角线/5；支持即时跳过和确认推进 |
| **文字阴影** | 对话框背后的暗化遮罩（FadeTransition 0.25s） |
| **线性推进** | 按顺序播放 Item 列表，最后一个结束时淡出 |
| **点击交互** | CG 扫线结束后点击推进；文字打字中点击直接完整显示，再点清除并推进 |
| **搭档头像** | 文字面板角落显示菱形旋转头像 |

### 2.2 故事选择 UI

| 功能 | 说明 |
|------|------|
| **菱形按钮** | 45° 旋转的菱形 `StoryButton`，边长 = 屏幕高度的 10% |
| **按钮状态** | 锁定（灰色带锁图标）→ 激活（显示解锁条件）→ 解锁（可播放） |
| **水平编组** | `StoryButtonPane` 将按钮水平排列，用白色 `lightLine` 连接已解锁的按钮 |
| **解锁条件** | 仅谱面条件或"搭档+谱面"双重条件，通过 `StoryUnlockConditionDisplayer` 弹窗查看 |
| **NEW 标记** | 未读按钮显示菱形 NEW 标签 |
| **搭档头像** | 部分按钮组旁边显示搭档头像 |
| **章节选择** | `ChapterSelectionPane` 支持左右滑动切换 Act|

### 2.3 主窗口管理

| 功能 | 说明 |
|------|------|
| **窗口尺寸** | 主屏宽度的 50%，16:9 比例 |
| **自适应缩放** | root `StackPane` 随窗口缩放 |
| **BGM 管理** | `故事模式BGM.wav` 循环播放，音量 20%，切换画面时 0.5s 淡出 |
| **场景切换** | `transitionNode()` 简单淡入淡出；`switchNode()` 含 Loading 画面切换 |
| **全屏切换** | F11 键切换全屏 |
| **Loading 画面** | 多层动画：两侧滑入 + 插画缩放 + 曲目信息标签 + 范式图像，有 10 种 Type 枚举（NORMAL, COURSE, GRIEVOUS, FRACTURE, TEMPESTISSIMO, FINAL, ARGHENA, ALTER, DESIGNANT, UNDYING） |

### 2.4 其他界面

| 功能 | 说明 |
|------|------|
| **Opening** | 4 种开场序列（ETERNAL/FINAL, SILENT, LUCENT），英文+日文文本动画 |
| **PotentialCalculator** | 仿 Arcaea PTT 计算器（PM/EX/AA 分界计算） |
| **AVGStory** | 冒险剧情模式（目前为骨架） |
| **StoryUnlockDisplayer** | 解锁条件弹窗，缩放+淡入动画 |

---

## 三、新架构设计

### 3.1 分层架构

```
┌──────────────────────────────────────────────────┐
│                   Presentation                   │
│    (JavaFX UI: Screen, Component, Animation)     │
├──────────────────────────────────────────────────┤
│                     Domain                       │
│   (StoryEngine, GameState, ConditionEvaluator)   │
├──────────────────────────────────────────────────┤
│                      Data                        │
│   (Story, Chart, Partner, Resources - models)    │
├──────────────────────────────────────────────────┤
│                 Infrastructure                   │
│   (AudioManager, ResourceLoader, SaveManager)    │
└──────────────────────────────────────────────────┘
```

**核心原则：**
- **Domain 层零 JavaFX 依赖**：StoryEngine 等用纯 POJO + 回调接口，可独立测试
- **Screen 模式**：每个"画面"是一个独立的 Screen 实现，由 ScreenManager 管理切换
- **事件驱动**：组件间通过回调/事件通信，避免直接引用

### 3.2 新包结构

**以下为大致结构，应当在此基础上增添新类：**

```
io/github/phiseecodyhsp/arcstory
├── ArcStoryLauncher.java              (Application 入口，组装依赖)
├── res/
│   ├── Audios.java                    (工具类，播放音频音效)
│   ├── ResourceLoader.java            (工具类，统一资源加载)
│   └── ResourceLocation.java          (资源路径的表示)
├── service/                           (领域层，逻辑接口服务)
│   ├── BgmService.java                (BGM 管理)
│   └── GameStateService.java          (游戏状态查询 & 保存)
├── serviceimpl/                       (基础设施实现层)
│   ├── BGMServiceImpl.java
│   ├── GameStateServiceImpl.java
├── model/                             (Model 层)
│   ├── Chart.java
│   ├── Charts.java
│   ├── Difficulty.java
│   ├── DifficultyLevel.java
│   ├── Paradigms.java
│   ├── Partner.java
│   ├── Partners.java
│   └── story/                         (故事相关的数据模型)
│       ├── Paragragh.java
│       ├── ParagraghType.java
│       └── Story.java
├── viewmodel/                         (ViewModel 层)
│   ├── AvatarNodeViewModel.java
│   ├── ButtonNodeViewModel.java
│   ├── StoryBranchViewModel.java
│   └── StoryNodeViewModel.java
├── ui/                                (JavaFX UI 层)
│   ├── base/
│   │   ├── AppWindow.java             (主窗口 Stage 管理)
│   │   ├── Screen.java                (场景接口)
│   │   ├── ScreenManager.java         (场景切换管理)
│   │   └── Transition.java            (过渡动画类型枚举)
│   ├── screen/
│   │   ├── StoryScreen.java           (故事界面)
│   │   ├── ChapterSelectScreen.java   (章节选择)
│   │   ├── viewmodel/                 (场景 ViewModel 层)
│   │   │   ├── StoryScreenViewModel.java
│   │   │   ├── StoryViewModel.java
│   │   │   └── ChapterSelectScreenViewModel.java
│   │   └── view/                      (场景 View 层)
│   │       ├── StoryScreenView.java
│   │       ├── StoryView.java         (故事播放 View)
│   │       └── ChapterSelectScreenView.java
│   ├── view/                          (View 层)
│   │   ├── Avatar.java                (搭档头像框)
│   │   ├── Effects.java               (效果常量池)
│   │   ├── IndicatorBadge.java        (NEW/Star 标记)
│   │   ├── StoryBranch.java           (故事分支)
│   │   ├── StoryNodeRegistry.java     (故事节点类型注册)
│   │   ├── UnlockPopup.java           (工具类，解锁条件弹窗)
│   │   └── node/                      (故事节点)
│   │       ├── ButtonNode.java
│   │       ├── AvatarNode.java
│   │       └── StoryNode.java         (抽象类)
│   └── util/                          (工具类)
│       ├── ScreenMetrics.java
│       ├── Interpolators.java
│       └── JavaFxUtil.java            (JavaFX 线程工具方法)
└── util/                              (工具类)
    ├── MathUtil.java
    └── Alerts.java                    (异常记录报告)
```

### 3.3 故事 JSON 数据结构（重构后）

```json
{
  "id": "story_001",
  "title": "光光真可爱",
  "version": "1.0",
  "background": null,
  "bgm": null,
  "unlock": {
    "type": "chart_and_partner",
    "chart": "Tutorial_PST",
    "partner": "Hikari"
  },
  "hasCG": true,
  "scenes": [
    {
      "id": "s1",
      "type": "cg",
      "image": "images/0-3.jpg",
      "reveal": "sweep",
      "music": null,
      "duration": null
    },
    {
      "id": "s2",
      "type": "dialogue",
      "speaker": null,
      "text": "光光真可爱。|Hikari is so cute.|光ちゃんはとてもかわいいね。",
      "avatar": null,
      "audio": null,
      "fontSize": null
    }
  ]
}
```

**Scene 类型：**

| type | 说明 | 关键字段 |
|------|------|----------|
| `cg` | CG 图片展示 | `image`, `reveal`(sweep/fade/none), `music` |
| `dialogue` | 对话/文本 | `speaker`, `text`（`|` 分隔多语言）, `avatar`, `audio` |
| `choice` | 分支选择 | `prompt`, `options: [{text, nextScene}]` |
| `effect` | 画面特效 | `effect`(fade_in/fade_out/shake/flash), `duration` |
| `change_bg` | 切换背景 | `image`, `duration` |
| `end` | 故事结束 | `returnTo`(menu/chapter) |

### 3.4 核心类职责

#### `StoryEngine` (core.story)

```
┌──────────────────────────────────┐
│           StoryEngine            │
│  - currentSceneIndex: int       │
│  - story: Story                 │
│  ────────────────────────────── │
│  + nextScene(): Scene           │
│  + jumpTo(sceneId): Scene       │
│  + hasNextScene(): boolean      │
│  + getCurrentScene(): Scene     │
│  + makeChoice(choiceIdx): void  │
│  ------- 回调 (在 UI 线程) ------│
│  + onSceneChanged: Consumer     │
│  + onStoryEnd: Runnable         │
└──────────────────────────────────┘
```

- 管理故事播放顺序
- 支持线性推进和分支跳转
- 通过回调通知 UI 更新，自身不持有 UI 引用

#### `ScreenManager` (ui.base)

```
┌──────────────────────────────────┐
│          ScreenManager           │
│  - screens: Map<Id, Screen>     │
│  - currentScreen: Screen        │
│  - root: StackPane             │
│  ────────────────────────────── │
│  + register(id, screen)         │
│  + navigateTo(id, transition)   │
│  + goBack()                     │
│  + getRoot(): Parent            │
└──────────────────────────────────┘
```

- 管理所有 Screen 注册和切换
- 支持过渡动画类型（fade/slide/loading）
- 维护导航栈（用于返回）

#### `VisualNovelScreen` (ui.screen)

```
┌──────────────────────────────────┐
│       VisualNovelScreen          │
│  - engine: StoryEngine          │
│  - cgReveal: CgReveal          │
│  - typewriter: TypewriterText   │
│  - avatar: PartnerAvatar        │
│  - choicePanel: VBox           │
│  ────────────────────────────── │
│  + play(story: Story)           │
│  - renderScene(scene: Scene)    │
│  - setupClickHandler()          │
│  - showChoices(options)         │
└──────────────────────────────────┘
```

- 观察 StoryEngine 的场景变化
- 将每个 Scene 渲染为对应的 UI 表现
- 管理点击交互 → 调用 engine.nextScene()

### 3.5 资源管理重构

`Resources.java` 当前问题严重（硬编码、路径分散、`ofString` 可能返回 `"null"`）。

重构为：

```java
// ResourceLoader.java - 单一入口
public final class ResourceLoader {
    private static final String BASE = "/io/github/phiseecodyhsp/arcstory/";

    // 按类别分组的路径常量，外部化到 resource-config.json
    public InputStream loadStream(String path);
    public String loadUrl(String path);
    public Font loadFont(String path, double size);
    public Image loadImage(String path);
    public AudioClip loadAudio(String path);
}
```

同时将资源路径从代码迁移到 `resource-config.json`（一个可编辑的映射文件）。

---

## 四、重写计划（分阶段）

### Phase 0 — 准备工作（已完成）
- [x] 删除旧代码中不可抢救的部分（UI、StoryMode 等）
- [x] 保留数据模型和工具类
- [x] 保留所有资源文件
- [x] 更新 `pom.xml` 主类为 `ArcStoryLauncher`
- [x] 清理 `module-info.java`

### Phase 1 — 基础设施层（预计 2-3 天）

**目标：搭建可运行的空框架，所有测试可跑。**

| 任务                                | 描述                                                            |
|-----------------------------------|---------------------------------------------------------------|
| 1.1 包结构创建                         | 按 3.2 创建所有包目录                                                 |
| 1.2 迁移现有类                         | 将 `model/` 和 `util/` 下的类移入对应的新包，调整 import                     |
| 1.3 `ResourceLoader`              | 重构资源加载，替换静态 `Resources.java`。写单元测试验证路径正确                      |
| 1.4 `Story` 模型                    | 定义 `Story`、`Scene`、`SceneType`、`UnlockCondition` 的 POJO + Jackson 注解 |
| 1.5 `StoryLoader`                 | 实现 JSON → Story 反序列化 + 校验。写单元测试                               |
| 1.6 `GameState` & `SaveManager`   | 实现 JSON 持久化的游戏状态（已解锁故事集、已读故事集、已通关谱面集）。写单元测试                   |
| 1.7 `AppWindow`                   | 实现主窗口创建、16:9 自适应缩放。写集成测试                                      |
| 1.8 `BgmService`                  | 实现 BGM 管理服务                                                   |
| 1.9 `ScreenManager` + `Screen` 接口 | 实现屏幕注册和切换（先用简单的 `setCenter` 切换，过渡动画后续）                        |
| 1.10 `ArcStoryLauncher`           | 组装所有依赖，启动到空白画面                                                |
| **验证点**                           | `mvn clean javafx:run` 能启动一个空白窗口，BGM 播放                       |

### Phase 2 — 故事选择 UI（预计 2-3 天）

**目标：能从章节选择界面进入到故事选择，看到菱形按钮网格。**

| 任务 | 描述 |
|------|------|
| 2.1 `DiamondButton` | 45° 旋转菱形按钮。支持"锁定/激活/解锁"三种状态，布局 = Pane + Rectangle + ImageView + Label 叠加 |
| 2.2 `IndicatorBadge` | NEW 徽章、Star 徽章（CG 指示） |
| 2.3 `PartnerAvatar` | 菱形头像框组件 |
| 2.4 `UnlockPopup` | 解锁条件弹窗，显示谱面/搭档需求 |
| 2.5 `StorySelectScreen` | 故事菱形按钮网格布局 + 连接线（lightLine/darkLine）+ 搭档头像 |
| 2.6 `ChapterSelectScreen` | Act 左右滑动选择 + Chapter 按钮 + 罗马数字转换 |
| 2.7 `Transition` | 实现 fade / slide 过渡动画 |
| 2.8 `MenuScreen` | 简单主菜单（标题 + 进入故事模式按钮） |
| 2.9 串联 | 菜单 → 章节选择 → 故事选择（选择任意按钮点进去暂时空） |
| **验证点** | 能从菜单一路点到故事按钮，菱形按钮正常显示，锁定/解锁逻辑正常 |

### Phase 3 — 视觉小说引擎（预计 3-4 天）

**目标：能在故事选择后进入视觉小说播放，CG+文字正常播放。**

| 任务 | 描述 |
|------|------|
| 3.1 `StoryEngine` | 实现播放状态机：线性推进、分支跳转、结束检测。写单元测试（用 mock 回调） |
| 3.2 `CgReveal` | 扫线揭露动画：clip polygon + DropShadow 发光 + ColorAdjust 亮度 + Timeline 多个 KeyFrame。封装为可复用组件 |
| 3.3 `TypewriterText` | 打字机效果 TextFlow：逐字显示、0.05s 间隔、点击跳过/确认、空行替换为空格（留白） |
| 3.4 `VisualNovelScreen` | 整合 CG 展示、文字播放、对话框遮罩、点击交互、搭档头像、选择分支面板 |
| 3.5 与 StorySelectScreen 串联 | 点击解锁按钮 → new VisualNovelScreen(StoryLoader.load("stories/xxx.json")).play() |
| 3.6 故事结束处理 | 淡出 → 标记已读 → 回到 StorySelectScreen |
| **验证点** | `test.json` 的故事可以完整播放（CG 扫线 → 文字打字） |

### Phase 4 — 转场与 Loading 画面（预计 1-2 天）

**目标：画面切换有 Arcaea 风格的 Loading 转场动画。**

| 任务 | 描述 |
|------|------|
| 4.1 `LoadingScreen` | 实现多范式转场：两侧滑入面板 + 插画缩放 + 信息标签 + 范式图像 |
| 4.2 范式适配 | LIGHT / CONFLICT / ACHROMIC / LEPHON 四种侧的对应用色/动画 |
| 4.3 Type 枚举 | 10 种转场 Type（NORMAL 到 UNDYING），每种对应不同素材组合 |
| 4.4 `ScreenManager` 集成 | `navigateTo(id, Transition.LOADING)` 时插入 LoadingScreen |
| **验证点** | 章节间切换、故事播放间切换有完整的 Loading 转场 |

### Phase 5 — 功能补充与串联（预计 2-3 天）

**目标：所有 master 分支功能复现，应用可整体运行。**

| 任务 | 描述 |
|------|------|
| 5.1 `Opening` | 4 种开场序列，文本强调（L / C 高亮）+ 背景切换 |
| 5.2 `PotentialCalculator` | PM/EX/AA PTT 计算 UI |
| 5.3 `AVGStory` | 冒险故事模式（若需要） |
| 5.4 `SettingsScreen` | 音量、文字速度、语言设置 |
| 5.4 全屏切换 | F11 全屏/窗口切换 |
| 5.5 主菜单完善 | 所有功能入口（故事模式、设置、退出） |
| 5.6 端到端测试 | 从头到尾完整走一遍流程 |
| **验证点** | 完整功能等价于 master 分支 |

### Phase 6 — 优化与完善（持续）

**目标：代码质量保障，素材替换，性能优化。**

| 任务 | 描述 |
|------|------|
| 6.1 代码审查 | 检查命名规范、注释、异常处理 |
| 6.2 单元测试覆盖 | StoryEngine、StoryLoader、GameState、SaveManager 关键路径全覆盖 |
| 6.3 素材替换 | 替换所有 TODO 标记的占位图（NEW 图标、Star 图标、范式图像、Loading 素材） |
| 6.4 性能优化 | 图片预加载、字体缓存、Timeline 复用 |
| 6.5 国际化 | 抽取字符串，支持中/英/日 |

---

## 五、关键设计决策

### 5.1 为何不使用 FXML

- 本项目 UI 动态性强（按钮动态生成、动画多），FXML 边际收益低
- master 分支也未使用 FXML（pom 引用了 javafx-fxml 但无 .fxml 文件）
- 纯代码构建更灵活，调试方便
- **决定：移除 javafx-fxml 依赖**，减少一个依赖

### 5.2 为何保留 model/ 和 util/ 下的类

- `Chart`、`Partner` 等是独立的数据模型，设计合理，无需重写
- `ScreenMetrics`、`MathUtil`、`Interpolators`、`Alerts` 是纯工具类，已有注释
- 只需移入新包结构，调整 import

### 5.3 Java Module System

- 保留 `module-info.java`
- 需要 `opens` 的包：故事 JSON 反序列化中 Jackson 需要反射访问的包
- 导出：core 和 ui 包给测试模块

### 5.4 线程安全

- JavaFX UI 操作始终在 `Platform.runLater()` 中执行
- `StoryEngine` 的回调通过 `JavaFxUtil.runOnFxThread()` 包装
- 避免直接在回调中操作 UI 节点

### 5.5 资源管理

- 将资源路径从代码迁移到 `resource-config.json`：
  ```json
  {
    "images": {
      "new_icon": "images/Icon_scenery_Beyond.png",
      "star": "images/Icon_scenery_Beyond.png",
      "loading_light_l": "images/TransAnimaL_resized.png",
      ...
    },
    "audio": {
      "start": "audios/START.mp3",
      "story_bgm": "audios/故事模式BGM.wav",
      ...
    },
    "fonts": {
      "geosans_light": "fonts/GeosansLight.ttf",
      ...
    }
  }
  ```
- `ResourceLoader` 读取此配置，按需懒加载

---

## 六、依赖清单

| 依赖 | 版本 | 用途 | 变更 |
|------|------|------|------|
| `org.openjfx:javafx-controls` | 26.0.1 | UI 组件 | 保留 |
| `org.openjfx:javafx-media` | 26.0.1 | 音频播放 | 保留 |
| `com.fasterxml.jackson.core:jackson-databind` | 2.21.3 | JSON 解析 | 保留 |
| `org.jetbrains:annotations` | RELEASE | @NotNull 等 | 保留 |
| `org.junit.jupiter` | 5.12.1 | 测试 | 保留 |
| `org.openjfx:javafx-fxml` | 26.0.1 | FXML | **移除** |

---

## 七、目录结构总览（最终态）

```
src/
├── main/
│   ├── java/
│   │   ├── module-info.java
│   │   └── io/github/phiseecodyhsp/arcstory/
│   │       ├── ArcStoryLauncher.java
│   │       ├── core/
│   │       │   ├── story/  (Story, StoryLoader, StoryEngine, Scene, StoryProgress)
│   │       │   ├── condition/  (UnlockCondition, ChartCondition, PartnerCondition)
│   │       │   └── state/  (GameState, SaveManager)
│   │       ├── ui/
│   │       │   ├── base/  (Screen, ScreenManager, AppWindow, Transition)
│   │       │   ├── screen/  (Menu, ChapterSelect, StorySelect, VisualNovel, Loading, Settings)
│   │       │   ├── component/  (DiamondButton, TypewriterText, CgReveal, PartnerAvatar, UnlockPopup, IndicatorBadge)
│   │       │   └── util/  (JavaFxUtil)
│   │       ├── model/  (Chart, Charts, Partner, Partners, Difficulty, DifficultyLevel, Paradigms)
│   │       ├── res/  (ResourceLoader)
│   │       ├── util/  (ScreenMetrics, MathUtil, Interpolators, Alerts)
│   │       ├── opening/  (Opening, OpeningType)
│   │       ├── calculator/  (PotentialCalculator)
│   │       └── avg/  (AVGStory)
│   └── resources/io/github/phiseecodyhsp/arcstory/
│       ├── resource-config.json              (新增)
│       ├── audios/  (7 个文件，不变)
│       ├── fonts/  (28 个文件，不变)
│       ├── images/  (55 个文件，不变)
│       ├── stories/  (empty.json, test.json，后续新增故事)
│       └── texts/  (test.txt，后续新增文本)
└── test/
    └── java/io/github/phiseecodyhsp/arcstory/
        ├── core/story/  (StoryLoaderTest, StoryEngineTest)
        └── core/state/  (SaveManagerTest, GameStateTest)
```

