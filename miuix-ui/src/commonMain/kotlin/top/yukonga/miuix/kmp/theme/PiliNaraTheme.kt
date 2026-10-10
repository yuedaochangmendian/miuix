// Copyright 2026, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A seed color offered by the PiliNara color picker.
 *
 * @property color The seed color to hand to [PiliNaraTheme.lightColors] or [PiliNaraTheme.darkColors].
 * @property label The label PiliNara shows for this seed, kept verbatim from its `colorThemeTypes` list.
 */
@Immutable
data class PiliNaraSeedColor(
    val color: Color,
    val label: String,
)

/**
 * The brand colors PiliNara uses outside of its Material color scheme.
 *
 * These accents have no equivalent role in [Colors], so they are carried separately and published
 * through [PiliNaraTheme.brandColors].
 *
 * @property vip The accent used for VIP badges.
 * @property blue The secondary accent used for links and information badges.
 * @property button The accent used for highlighted action buttons.
 * @property free The accent used for free-of-charge badges.
 */
@Immutable
data class PiliNaraBrandColors(
    val vip: Color,
    val blue: Color,
    val button: Color,
    val free: Color,
)

/**
 * The shape and spacing tokens PiliNara declares in its `Style` helper and component themes.
 *
 * They are expressed as [Dp] so they can be reused with Miuix components, which take corner radii
 * and spacings as parameters.
 *
 * @property cardSpacing The vertical gap between cards.
 * @property safeSpacing The vertical gap of the safe area of a page.
 * @property imageCornerRadius The corner radius of cover images.
 * @property cardCornerRadius The corner radius of list tiles.
 * @property menuCornerRadius The corner radius of popup menus.
 * @property bottomSheetCornerRadius The corner radius of the top corners of a bottom sheet.
 * @property tooltipCornerRadius The corner radius of tooltips.
 * @property dialogMinWidth The minimum width of a dialog.
 * @property dialogMaxWidth The maximum width of a dialog.
 * @property topBarHeight The height of the top app bar.
 * @property menuVerticalPadding The vertical padding of a popup menu.
 * @property videoAspectRatio The aspect ratio of the video player.
 * @property videoWideAspectRatio The aspect ratio of the video player in wide mode.
 * @property imageMaxAspectRatio The maximum aspect ratio a cover image may occupy.
 */
@Immutable
data class PiliNaraShapes(
    val cardSpacing: Dp = 8.dp,
    val safeSpacing: Dp = 12.dp,
    val imageCornerRadius: Dp = 10.dp,
    val cardCornerRadius: Dp = 12.dp,
    val menuCornerRadius: Dp = 16.dp,
    val bottomSheetCornerRadius: Dp = 28.dp,
    val tooltipCornerRadius: Dp = 4.dp,
    val dialogMinWidth: Dp = 280.dp,
    val dialogMaxWidth: Dp = 420.dp,
    val topBarHeight: Dp = 52.dp,
    val menuVerticalPadding: Dp = 4.dp,
    val videoAspectRatio: Float = 16f / 10f,
    val videoWideAspectRatio: Float = 16f / 9f,
    val imageMaxAspectRatio: Float = 2.6f,
)

/**
 * The PiliNara theme: the seed colors, brand accents, shape tokens and color schemes of the
 * PiliNara video client, ported to Miuix.
 *
 * PiliNara builds its light and dark schemes from a single seed color with `flex_seed_scheme`,
 * whose default `FlexSchemeVariant.material` reproduces Flutter's `ColorScheme.fromSeed` defaults:
 * the Material Color Utilities `tonalSpot` palette under color spec 2021 at contrast level 0. That
 * is exactly what [colorsFromSeed] produces with [ThemeColorSpec.Spec2021] and
 * [ThemePaletteStyle.TonalSpot], so the whole color side of the theme is a single call.
 *
 * ```kotlin
 * PiliNaraTheme(seed = PiliNaraTheme.SeedColors[3].color, pureBlack = true) {
 *     // Miuix components, themed like PiliNara.
 * }
 * ```
 *
 * [PiliNaraTheme.Shapes] and [piliNaraTextStyles] carry the geometry and the Material 3 type scale
 * PiliNara uses, since Miuix components are styled through parameters rather than through a
 * `ThemeData`-like object.
 */
object PiliNaraTheme {
    /**
     * The seed color PiliNara ships as its default, labelled "默认绿" in its color picker.
     */
    val DefaultSeed: Color = Color(0xFF5CB67B)

    /**
     * The seed colors PiliNara offers, in the order its color picker shows them.
     */
    val SeedColors: List<PiliNaraSeedColor> = listOf(
        PiliNaraSeedColor(Color(0xFF5CB67B), "默认绿"),
        PiliNaraSeedColor(Color(0xFFFF7299), "粉红色"),
        PiliNaraSeedColor(Color(0xFFF44336), "红色"),
        PiliNaraSeedColor(Color(0xFFFF9800), "橙色"),
        PiliNaraSeedColor(Color(0xFFFFC107), "琥珀色"),
        PiliNaraSeedColor(Color(0xFFFFEB3B), "黄色"),
        PiliNaraSeedColor(Color(0xFFCDDC39), "酸橙色"),
        PiliNaraSeedColor(Color(0xFF8BC34A), "浅绿色"),
        PiliNaraSeedColor(Color(0xFF4CAF50), "绿色"),
        PiliNaraSeedColor(Color(0xFF009688), "青色"),
        PiliNaraSeedColor(Color(0xFF00BCD4), "蓝绿色"),
        PiliNaraSeedColor(Color(0xFF03A9F4), "浅蓝色"),
        PiliNaraSeedColor(Color(0xFF2196F3), "蓝色"),
        PiliNaraSeedColor(Color(0xFF3F51B5), "靛蓝色"),
        PiliNaraSeedColor(Color(0xFF9C27B0), "紫色"),
        PiliNaraSeedColor(Color(0xFF673AB7), "深紫色"),
        PiliNaraSeedColor(Color(0xFF607D8B), "蓝灰色"),
        PiliNaraSeedColor(Color(0xFF795548), "棕色"),
        PiliNaraSeedColor(Color(0xFF9E9E9E), "灰色"),
    )

    /**
     * The brand accents PiliNara uses in its light theme.
     */
    val LightBrandColors: PiliNaraBrandColors = PiliNaraBrandColors(
        vip = Color(0xFFFF6699),
        blue = Color(0xFF008AC5),
        button = Color(0xFFFF6699),
        free = Color(0xFFFF7F24),
    )

    /**
     * The brand accents PiliNara uses in its dark theme.
     */
    val DarkBrandColors: PiliNaraBrandColors = PiliNaraBrandColors(
        vip = Color(0xFFD44E7D),
        blue = Color(0xFF2C9CC8),
        button = Color(0xFF8F0030),
        free = Color(0xFFD66011),
    )

    /**
     * The shape and spacing tokens PiliNara uses.
     */
    val Shapes: PiliNaraShapes = PiliNaraShapes()

    /**
     * Builds the light [Colors] of the PiliNara theme from a seed color.
     *
     * @param seed The seed color, [DefaultSeed] by default.
     */
    fun lightColors(seed: Color = DefaultSeed): Colors = colorsFromSeed(
        seed = seed,
        colorSpec = ThemeColorSpec.Spec2021,
        paletteStyle = ThemePaletteStyle.TonalSpot,
        dark = false,
    )

    /**
     * Builds the dark [Colors] of the PiliNara theme from a seed color.
     *
     * When [pureBlack] is enabled the scheme is darkened the way PiliNara's `darkenTheme` does it:
     * the background and the main surface turn completely black, the elevated surfaces are darkened
     * by their own amount, and the tonal and secondary roles are pulled towards black slightly so
     * that they stay readable on a pure black canvas.
     *
     * Two details of PiliNara's pure black handling do not survive the port, because Miuix has no
     * per-component color overrides: the dialog, bottom sheet, navigation bar, navigation rail and
     * popup menu backgrounds are all merged into the `surfaceContainerHighest` role, and the
     * `inversePrimary`, `surfaceTint`, `inverseSurface` and `onInverseSurface` roles are dropped
     * since [Colors] has no slots for them.
     *
     * @param seed The seed color, [DefaultSeed] by default.
     * @param pureBlack Whether to darken the scheme to a pure black background.
     */
    fun darkColors(seed: Color = DefaultSeed, pureBlack: Boolean = false): Colors {
        val roles = dynamicSchemeFromSeed(
            seed = seed,
            colorSpec = ThemeColorSpec.Spec2021,
            paletteStyle = ThemePaletteStyle.TonalSpot,
            dark = true,
        ).toMonetRoles()
        return mapMd3RolesToMiuixColorsCommon(if (pureBlack) roles.darkened() else roles, dark = true)
    }

    /**
     * The brand accents of the enclosing [PiliNaraTheme].
     */
    val brandColors: PiliNaraBrandColors
        @Composable @ReadOnlyComposable
        get() = LocalPiliNaraBrandColors.current
}

/**
 * The brand accents of the enclosing [PiliNaraTheme].
 */
internal val LocalPiliNaraBrandColors = staticCompositionLocalOf { PiliNaraTheme.LightBrandColors }

/**
 * Applies the PiliNara theme to [content].
 *
 * The light or dark scheme is picked from [dark], the brand accents from [pureBlack]'s sibling state,
 * and everything is handed to [MiuixTheme] so that the composition locals Miuix expects are all set.
 *
 * @param seed The seed color of the scheme, [PiliNaraTheme.DefaultSeed] by default.
 * @param dark Whether to use the dark scheme, following the system by default.
 * @param pureBlack Whether the dark scheme should use a pure black background, `false` by default.
 * @param textStyles The text styles to use, the ambient [MiuixTheme] text styles by default. Use
 *   [piliNaraTextStyles] for the Material 3 type scale PiliNara renders with.
 * @param content The content to theme.
 */
@Composable
fun PiliNaraTheme(
    seed: Color = PiliNaraTheme.DefaultSeed,
    dark: Boolean = isSystemInDarkTheme(),
    pureBlack: Boolean = false,
    textStyles: TextStyles = MiuixTheme.textStyles,
    content: @Composable () -> Unit,
) {
    val colors = remember(seed, dark, pureBlack) {
        if (dark) PiliNaraTheme.darkColors(seed = seed, pureBlack = pureBlack) else PiliNaraTheme.lightColors(seed = seed)
    }
    val brandColors = if (dark) PiliNaraTheme.DarkBrandColors else PiliNaraTheme.LightBrandColors
    CompositionLocalProvider(LocalPiliNaraBrandColors provides brandColors) {
        MiuixTheme(colors = colors, textStyles = textStyles, content = content)
    }
}

/**
 * Darkens this color towards black by [amount], keeping its alpha.
 *
 * This mirrors Flutter's `Color.darken`, which lerps each channel towards black in the sRGB color
 * space. Compose's [androidx.compose.ui.graphics.lerp] is deliberately not used here: it
 * interpolates in Oklab, which would produce different values than the ones PiliNara renders.
 *
 * @param amount How far to move towards black, in the range `0f..1f`, `0.5f` by default.
 */
internal fun Color.darken(amount: Float = 0.5f): Color {
    require(amount in 0f..1f) { "amount must be in the range 0f..1f, but was $amount" }
    return Color(
        red = red * (1f - amount),
        green = green * (1f - amount),
        blue = blue * (1f - amount),
        alpha = alpha,
    )
}

/**
 * The pure black transform PiliNara's `darkenTheme` applies to its dark color scheme.
 */
private fun MonetRoles.darkened(): MonetRoles = copy(
    primary = primary.darken(0.1f),
    onPrimary = onPrimary.darken(0.1f),
    primaryContainer = primaryContainer.darken(0.1f),
    onPrimaryContainer = onPrimaryContainer.darken(0.1f),
    primaryFixed = primaryFixed.darken(0.1f),
    onPrimaryFixed = onPrimaryFixed.darken(0.1f),
    secondary = secondary.darken(0.05f),
    onSecondary = onSecondary.darken(0.05f),
    secondaryContainer = secondaryContainer.darken(0.05f),
    onSecondaryContainer = onSecondaryContainer.darken(0.05f),
    error = error.darken(0.05f),
    background = Color.Black,
    onBackground = onBackground.darken(0.15f),
    surface = Color.Black,
    onSurface = onSurface.darken(0.15f),
    surfaceContainer = surfaceContainer.darken(0.75f),
    surfaceContainerHigh = surfaceContainerHigh.darken(),
    surfaceContainerHighest = surfaceContainerHighest.darken(0.7f),
)
