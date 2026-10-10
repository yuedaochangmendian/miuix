# PiliNara Theme

A port of the [PiliNara](https://github.com/Starfallan/PiliNara) theme to Miuix.

PiliNara is a Flutter video client that builds its whole look from a single seed color. This page
documents the Miuix equivalent: the same seed palette, the same color scheme pipeline, the same
shape tokens, and the Material 3 type scale PiliNara renders with.

## Quick Start

```kotlin
PiliNaraTheme {
    // Everything below is themed and readable through the usual Miuix APIs.
    Scaffold {
        Text("Hello PiliNara", style = MiuixTheme.textStyles.title2)
    }
}

// Pick any of the seeds PiliNara offers, in dark mode with a pure black background.
PiliNaraTheme(
    seed = PiliNaraTheme.SeedColors[3].color,
    dark = true,
    pureBlack = true,
) {
    // Content
}
```

`PiliNaraTheme` sets up `MiuixTheme` for you, so `MiuixTheme.colorScheme` and
`MiuixTheme.textStyles` keep working inside it.

## Color Scheme

PiliNara generates its light and dark schemes with `flex_seed_scheme`, whose default
`FlexSchemeVariant.material` reproduces Flutter's `ColorScheme.fromSeed` defaults: the Material Color
Utilities `tonalSpot` palette under color spec 2021 at contrast level 0. That is exactly
`colorsFromSeed(seed, ThemeColorSpec.Spec2021, ThemePaletteStyle.TonalSpot, dark)`, so the port is a
one to one mapping rather than an approximation. If you would rather wire it up yourself:

```kotlin
val controller = remember {
    ThemeController(
        colorSchemeMode = ColorSchemeMode.System,
        keyColor = PiliNaraTheme.DefaultSeed,
        colorSpec = ThemeColorSpec.Spec2021,
        paletteStyle = ThemePaletteStyle.TonalSpot,
    )
}
MiuixTheme(controller = controller) { /* Content */ }
```

### Seeds

`PiliNaraTheme.SeedColors` holds the seeds PiliNara offers in its color picker, in the same order
and with the same labels, and `PiliNaraTheme.DefaultSeed` is the one PiliNara ships as its default.

| Label | Seed | Label | Seed |
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

Any color works as a seed, these are only the presets. Building the schemes directly:

```kotlin
val light = PiliNaraTheme.lightColors(seed = Color(0xFF5CB67B))
val dark = PiliNaraTheme.darkColors(seed = Color(0xFF5CB67B), pureBlack = true)
```

### Pure Black

PiliNara's "pure black" dark mode re-renders the dark scheme with a completely black canvas. The
port keeps that behaviour: `background` and `surface` become `Color.Black`, the elevated surfaces
are darkened by their own amount, and the tonal and secondary roles are pulled towards black
slightly so they stay readable.

Two details do not survive the port, because Miuix styles components through parameters instead of
per-component colors in a `ThemeData`:

- PiliNara gives dialogs, bottom sheets, navigation bars, navigation rails and popup menus their own
  background color. In Miuix the equivalent surfaces read `surfaceContainerHighest`, so that single
  role carries the darkened value.
- The `inversePrimary`, `surfaceTint`, `inverseSurface` and `onInverseSurface` roles PiliNara darkens
  are dropped, since `Colors` has no slots for them.

## Brand Colors

PiliNara uses a few accents that are not part of its Material color scheme, so they are carried
separately and follow the light or dark mode the theme resolved to:

```kotlin
val brand = PiliNaraTheme.brandColors
Icon(tint = brand.vip, imageVector = MiuixIcons.Useful.Vip)
```

| Property | Light | Dark | Used for |
|----------|-------|------|----------|
| `vip`    | `0xFFFF6699` | `0xFFD44E7D` | VIP badges |
| `blue`   | `0xFF008AC5` | `0xFF2C9CC8` | Links and information badges |
| `button` | `0xFFFF6699` | `0xFF8F0030` | Highlighted action buttons |
| `free`   | `0xFFFF7F24` | `0xFFD66011` | Free-of-charge badges |

## Shapes

PiliNara declares its geometry in a `Style` helper and in its component themes. The port exposes the
same values through `PiliNaraTheme.Shapes`, so they can be fed straight into the Miuix components
that take a corner radius or a spacing:

```kotlin
Card(
    cornerRadius = PiliNaraTheme.Shapes.cardCornerRadius,
    colors = CardDefaults.defaultColors(color = MiuixTheme.colorScheme.surfaceContainer),
) { /* Content */ }
```

| Property | Value | Property | Value |
|----------|-------|----------|-------|
| `cardSpacing` | 8dp | `dialogMinWidth` | 280dp |
| `safeSpacing` | 12dp | `dialogMaxWidth` | 420dp |
| `imageCornerRadius` | 10dp | `topBarHeight` | 52dp |
| `cardCornerRadius` | 12dp | `menuVerticalPadding` | 4dp |
| `menuCornerRadius` | 16dp | `videoAspectRatio` | 16 / 10 |
| `bottomSheetCornerRadius` | 28dp | `videoWideAspectRatio` | 16 / 9 |
| `tooltipCornerRadius` | 4dp | `imageMaxAspectRatio` | 2.6 |

## Text Styles

PiliNara does not define its own type scale: it takes Flutter's default `Typography.material2021` and
only overrides the font family and, when the user picks something other than "normal", the font
weight of every role. `piliNaraTextStyles` reproduces that on top of the fourteen Miuix slots:

```kotlin
PiliNaraTheme(textStyles = piliNaraTextStyles()) { /* Material 3 type scale */ }
PiliNaraTheme(textStyles = piliNaraTextStyles(fontWeight = FontWeight.Bold)) { /* All bold */ }
```

| Miuix slot | Material 3 role | Size | Line height | Letter spacing | Weight |
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

The two scales do not line up one to one, because Miuix declares fourteen slots with MIUI proportions
while Material 3 declares fifteen roles: `main`, `paragraph` and `body1` all fall back to
`bodyLarge`, and `subtitle` keeps the bold weight Miuix gives it.

## API

| API | Description |
|-----|-------------|
| `PiliNaraTheme(seed, dark, pureBlack, textStyles, content)` | Applies the PiliNara theme to `content` |
| `PiliNaraTheme.DefaultSeed` | The seed PiliNara ships as its default |
| `PiliNaraTheme.SeedColors` | The seeds of the PiliNara color picker, as `List<PiliNaraSeedColor>` |
| `PiliNaraTheme.LightBrandColors` / `PiliNaraTheme.DarkBrandColors` | The brand accents of each mode |
| `PiliNaraTheme.brandColors` | The brand accents matching the enclosing `PiliNaraTheme` |
| `PiliNaraTheme.Shapes` | The shape and spacing tokens |
| `PiliNaraTheme.lightColors(seed)` / `PiliNaraTheme.darkColors(seed, pureBlack)` | Builds a `Colors` without a composable |
| `piliNaraTextStyles(fontFamily, fontWeight)` | The Material 3 type scale as `TextStyles` |
