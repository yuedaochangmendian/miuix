# PiliNara 主题

将 [PiliNara](https://github.com/Starfallan/PiliNara) 的主题移植到 Miuix。

PiliNara 是一个 Flutter 视频客户端，它的整套外观都由一个种子色推导而来。本页记录 Miuix 中的等价实现：相同的预设种子色、相同的配色生成管线、相同的形状 token，以及 PiliNara 渲染所用的 Material 3 字阶。

## 快速开始

```kotlin
PiliNaraTheme {
    // 下方所有内容都会被主题化，并且可以照常通过 Miuix API 读取。
    Scaffold {
        Text("Hello PiliNara", style = MiuixTheme.textStyles.title2)
    }
}

// 使用 PiliNara 提供的任意种子色，深色模式 + 纯黑背景。
PiliNaraTheme(
    seed = PiliNaraTheme.SeedColors[3].color,
    dark = true,
    pureBlack = true,
) {
    // 内容
}
```

`PiliNaraTheme` 内部已经为你配置好了 `MiuixTheme`，因此在它内部 `MiuixTheme.colorScheme` 与 `MiuixTheme.textStyles` 依然可用。

## 配色方案

PiliNara 使用 `flex_seed_scheme` 生成明暗两套配色，其默认的 `FlexSchemeVariant.material` 复现了 Flutter `ColorScheme.fromSeed` 的默认行为：Material Color Utilities 的 `tonalSpot` 调色板、颜色规范 2021、对比度等级 0。这正是 `colorsFromSeed(seed, ThemeColorSpec.Spec2021, ThemePaletteStyle.TonalSpot, dark)` 的语义，因此这次移植是一一对应的映射，而不是近似。如果你更愿意自己接线：

```kotlin
val controller = remember {
    ThemeController(
        colorSchemeMode = ColorSchemeMode.System,
        keyColor = PiliNaraTheme.DefaultSeed,
        colorSpec = ThemeColorSpec.Spec2021,
        paletteStyle = ThemePaletteStyle.TonalSpot,
    )
}
MiuixTheme(controller = controller) { /* 内容 */ }
```

### 种子色

`PiliNaraTheme.SeedColors` 保存了 PiliNara 颜色选择器中提供的全部种子色，顺序与文案都保持一致；`PiliNaraTheme.DefaultSeed` 是 PiliNara 的默认种子色。

| 文案 | 种子色 | 文案 | 种子色 |
|------------|----------|--------------|----------|
| 默认绿     | `0xFF5CB67B` | 蓝绿色   | `0xFF00BCD4` |
| 粉红色     | `0xFFFF7299` | 浅蓝色   | `0xFF03A9F4` |
| 红色       | `0xFFF44336` | 蓝色     | `0xFF2196F3` |
| 橙色       | `0xFFFF9800` | 靛蓝色   | `0xFF3F51B5` |
| 琥珀色     | `0xFFFFC107` | 紫色     | `0xFF9C27B0` |
| 黄色       | `0xFFFFEB3B` | 深紫色   | `0xFF673AB7` |
| 酸橙色     | `0xFFCDDC39` | 蓝灰色   | `0xFF607D8B` |
| 浅绿色     | `0xFF8BC34A` | 棕色     | `0xFF795548` |
| 绿色       | `0xFF4CAF50` | 灰色     | `0xFF9E9E9E` |
| 青色       | `0xFF009688` |          |          |

任何颜色都可以作为种子色，上面只是预设。也可以直接构建配色：

```kotlin
val light = PiliNaraTheme.lightColors(seed = Color(0xFF5CB67B))
val dark = PiliNaraTheme.darkColors(seed = Color(0xFF5CB67B), pureBlack = true)
```

### 纯黑模式

PiliNara 的「纯黑」深色模式会把整个画布渲染成纯黑。移植后保留了这一行为：`background` 与 `surface` 变为 `Color.Black`，各层抬升表面按各自的幅度压暗，主色与次要色也会略微向黑色靠拢，以保证在纯黑画布上依然可读。

有两处细节无法在移植中保留，因为 Miuix 通过参数而不是 `ThemeData` 中的逐组件颜色来定制外观：

- PiliNara 给对话框、底部弹层、导航栏、导航侧栏和弹出菜单分别指定了背景色。在 Miuix 中这些表面对应的是 `surfaceContainerHighest`，因此压暗后的值统一由该角色承载。
- PiliNara 会压暗的 `inversePrimary`、`surfaceTint`、`inverseSurface`、`onInverseSurface` 四个角色被丢弃，因为 `Colors` 中没有对应槽位。

## 品牌色

PiliNara 还使用了一些不属于其 Material 配色的强调色，因此它们被单独承载，并跟随主题解析出的明暗模式：

```kotlin
val brand = PiliNaraTheme.brandColors
Icon(tint = brand.vip, imageVector = MiuixIcons.Useful.Vip)
```

| 属性 | 浅色 | 深色 | 用途 |
|----------|-------|------|----------|
| `vip`    | `0xFFFF6699` | `0xFFD44E7D` | 会员标识 |
| `blue`   | `0xFF008AC5` | `0xFF2C9CC8` | 链接与信息标识 |
| `button` | `0xFFFF6699` | `0xFF8F0030` | 强调操作按钮 |
| `free`   | `0xFFFF7F24` | `0xFFD66011` | 免费标识 |

## 形状

PiliNara 在 `Style` 辅助类与各组件主题中声明几何尺寸。移植后通过 `PiliNaraTheme.Shapes` 暴露同样的数值，可以直接传给接收圆角或间距参数的 Miuix 组件：

```kotlin
Card(
    cornerRadius = PiliNaraTheme.Shapes.cardCornerRadius,
    colors = CardDefaults.defaultColors(color = MiuixTheme.colorScheme.surfaceContainer),
) { /* 内容 */ }
```

| 属性 | 数值 | 属性 | 数值 |
|----------|-------|----------|-------|
| `cardSpacing` | 8dp | `dialogMinWidth` | 280dp |
| `safeSpacing` | 12dp | `dialogMaxWidth` | 420dp |
| `imageCornerRadius` | 10dp | `topBarHeight` | 52dp |
| `cardCornerRadius` | 12dp | `menuVerticalPadding` | 4dp |
| `menuCornerRadius` | 16dp | `videoAspectRatio` | 16 / 10 |
| `bottomSheetCornerRadius` | 28dp | `videoWideAspectRatio` | 16 / 9 |
| `tooltipCornerRadius` | 4dp | `imageMaxAspectRatio` | 2.6 |

## 文字样式

PiliNara 没有自定义字阶：它直接使用 Flutter 默认的 `Typography.material2021`，只覆盖字体家族，以及当用户选择「非 normal」时覆盖所有角色的字重。`piliNaraTextStyles` 在 Miuix 的十四个槽位上复现了这一点：

```kotlin
PiliNaraTheme(textStyles = piliNaraTextStyles()) { /* Material 3 字阶 */ }
PiliNaraTheme(textStyles = piliNaraTextStyles(fontWeight = FontWeight.Bold)) { /* 全部加粗 */ }
```

| Miuix 槽位 | Material 3 角色 | 字号 | 行高 | 字距 | 字重 |
|------------|-----------------|------|-------------|----------------|--------|
| `main`      | `bodyLarge`    | 16sp | 24sp | 0.5sp  | Normal |
| `paragraph` | `bodyLarge`    | 16sp | 24sp | 0.5sp  | Normal |
| `body1`     | `bodyLarge`    | 16sp | 24sp | 0.5sp  | Normal |
| `body2`     | `bodyMedium`   | 14sp | 20sp | 0.25sp | Normal |
| `button`    | `labelLarge`   | 14sp | 20sp | 0.1sp  | Medium |
| `footnote1` | `bodySmall`    | 12sp | 16sp | 0.4sp  | Normal |
| `footnote2` | `labelSmall`   | 11sp | 16sp | 0.5sp  | Medium |
| `headline1` | `titleMedium`  | 16sp | 24sp | 0.15sp | Medium |
| `headline2` | `titleSmall`   | 14sp | 20sp | 0.1sp  | Medium |
| `subtitle`  | `titleSmall`   | 14sp | 20sp | 0.1sp  | Bold   |
| `title1`    | `headlineLarge`| 32sp | 40sp | 0sp    | Normal |
| `title2`    | `headlineSmall`| 24sp | 32sp | 0sp    | Normal |
| `title3`    | `titleLarge`   | 22sp | 28sp | 0sp    | Normal |
| `title4`    | `titleMedium`  | 16sp | 24sp | 0.15sp | Medium |

两套字阶并非一一对应：Miuix 以 MIUI 的比例声明了十四个槽位，而 Material 3 声明了十五个角色。因此 `main`、`paragraph`、`body1` 都回落到 `bodyLarge`，`subtitle` 则保留了 Miuix 赋予它的加粗字重。

## API

| API | 说明 |
|-----|-------------|
| `PiliNaraTheme(seed, dark, pureBlack, textStyles, content)` | 将 PiliNara 主题应用到 `content` |
| `PiliNaraTheme.DefaultSeed` | PiliNara 的默认种子色 |
| `PiliNaraTheme.SeedColors` | PiliNara 颜色选择器的种子色，类型为 `List<PiliNaraSeedColor>` |
| `PiliNaraTheme.LightBrandColors` / `PiliNaraTheme.DarkBrandColors` | 两种模式各自的品牌色 |
| `PiliNaraTheme.brandColors` | 与当前 `PiliNaraTheme` 匹配的品牌色 |
| `PiliNaraTheme.Shapes` | 形状与间距 token |
| `PiliNaraTheme.lightColors(seed)` / `PiliNaraTheme.darkColors(seed, pureBlack)` | 不依赖 Composable 地构建 `Colors` |
| `piliNaraTextStyles(fontFamily, fontWeight)` | Material 3 字阶，类型为 `TextStyles` |
