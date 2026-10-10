// Copyright 2026, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class PiliNaraThemeTest {
    @Test
    fun seedColorsKeepThePiliNaraOrderAndLabels() {
        val seeds = PiliNaraTheme.SeedColors

        assertEquals(19, seeds.size)
        assertEquals(Color(0xFF5CB67B), PiliNaraTheme.DefaultSeed)
        assertEquals(PiliNaraSeedColor(Color(0xFF5CB67B), "默认绿"), seeds.first())
        assertEquals(Color(0xFF9E9E9E), seeds.last().color)
        assertEquals(seeds.size, seeds.map { it.color }.distinct().size)
        assertEquals(seeds.size, seeds.map { it.label }.distinct().size)
    }

    @Test
    fun brandColorsMatchThePiliNaraThemeExtension() {
        val light = PiliNaraTheme.LightBrandColors
        assertEquals(Color(0xFFFF6699), light.vip)
        assertEquals(Color(0xFF008AC5), light.blue)
        assertEquals(Color(0xFFFF6699), light.button)
        assertEquals(Color(0xFFFF7F24), light.free)

        val dark = PiliNaraTheme.DarkBrandColors
        assertEquals(Color(0xFFD44E7D), dark.vip)
        assertEquals(Color(0xFF2C9CC8), dark.blue)
        assertEquals(Color(0xFF8F0030), dark.button)
        assertEquals(Color(0xFFD66011), dark.free)
    }

    @Test
    fun shapesMatchThePiliNaraStyleTokens() {
        val shapes = PiliNaraTheme.Shapes

        assertEquals(8.dp, shapes.cardSpacing)
        assertEquals(12.dp, shapes.safeSpacing)
        assertEquals(10.dp, shapes.imageCornerRadius)
        assertEquals(12.dp, shapes.cardCornerRadius)
        assertEquals(16.dp, shapes.menuCornerRadius)
        assertEquals(28.dp, shapes.bottomSheetCornerRadius)
        assertEquals(4.dp, shapes.tooltipCornerRadius)
        assertEquals(280.dp, shapes.dialogMinWidth)
        assertEquals(420.dp, shapes.dialogMaxWidth)
        assertEquals(52.dp, shapes.topBarHeight)
        assertEquals(4.dp, shapes.menuVerticalPadding)
        assertEquals(16f / 10f, shapes.videoAspectRatio)
        assertEquals(16f / 9f, shapes.videoWideAspectRatio)
        assertEquals(2.6f, shapes.imageMaxAspectRatio)
    }

    @Test
    fun theSchemesStayOnSpec2021TonalSpot() {
        val seed = Color(0xFF5CB67B)

        val light = PiliNaraTheme.lightColors(seed)
        val expectedLight = colorsFromSeed(seed, ThemeColorSpec.Spec2021, ThemePaletteStyle.TonalSpot, dark = false)
        assertEquals(expectedLight.primary, light.primary)
        assertEquals(expectedLight.background, light.background)
        assertEquals(expectedLight.surfaceContainerHighest, light.surfaceContainerHighest)
        assertEquals(expectedLight.onSurfaceVariantSummary, light.onSurfaceVariantSummary)

        val dark = PiliNaraTheme.darkColors(seed)
        val expectedDark = colorsFromSeed(seed, ThemeColorSpec.Spec2021, ThemePaletteStyle.TonalSpot, dark = true)
        assertEquals(expectedDark.primary, dark.primary)
        assertEquals(expectedDark.background, dark.background)
        assertEquals(expectedDark.surfaceContainerHigh, dark.surfaceContainerHigh)
        assertEquals(expectedDark.onSurface, dark.onSurface)
    }

    @Test
    fun lightAndDarkSchemesAreDistinct() {
        val light = PiliNaraTheme.lightColors()
        val dark = PiliNaraTheme.darkColors()

        assertTrue(light.background.luminance() > dark.background.luminance())
        assertTrue(light.surface.luminance() > dark.surface.luminance())
        assertTrue(light.onBackground.luminance() < dark.onBackground.luminance())
        assertNotEquals(light.primary, dark.primary)
        assertNotEquals(light.surfaceContainer, dark.surfaceContainer)
    }

    @Test
    fun distinctSeedsProduceDistinctPrimaryColors() {
        val primaries = listOf(
            Color(0xFF5CB67B),
            Color(0xFFF44336),
            Color(0xFF2196F3),
            Color(0xFF9C27B0),
        ).map { PiliNaraTheme.lightColors(it).primary }

        assertEquals(4, primaries.distinct().size)
    }

    @Test
    fun pureBlackReplacesTheBackgroundAndTheSurface() {
        val pureBlack = PiliNaraTheme.darkColors(pureBlack = true)

        assertEquals(Color.Black, pureBlack.background)
        assertEquals(Color.Black, pureBlack.surface)
        assertNotEquals(Color.Black, PiliNaraTheme.darkColors().background)
    }

    @Test
    fun pureBlackDarkensTheElevatedSurfacesLikePiliNara() {
        val dark = PiliNaraTheme.darkColors()
        val pureBlack = PiliNaraTheme.darkColors(pureBlack = true)

        assertEquals(dark.surfaceContainer.darken(0.75f), pureBlack.surfaceContainer)
        assertEquals(dark.surfaceContainerHigh.darken(0.5f), pureBlack.surfaceContainerHigh)
        assertEquals(dark.surfaceContainerHighest.darken(0.7f), pureBlack.surfaceContainerHighest)
        assertTrue(pureBlack.onSurface.luminance() < dark.onSurface.luminance())
        assertTrue(pureBlack.primary.luminance() < dark.primary.luminance())
    }

    @Test
    fun pureBlackKeepsAlphaBearingRolesOpaque() {
        val pureBlack = PiliNaraTheme.darkColors(pureBlack = true)

        val roles = listOf(
            pureBlack.surfaceContainer,
            pureBlack.surfaceContainerHigh,
            pureBlack.surfaceContainerHighest,
            pureBlack.onSurface,
            pureBlack.onBackground,
            pureBlack.disabledPrimary,
            pureBlack.disabledPrimaryButton,
            pureBlack.disabledSecondary,
            pureBlack.sliderBackground,
        )
        for (color in roles) {
            assertEquals(1f, color.alpha)
        }
    }

    @Test
    fun darkenMovesEveryChannelTowardsBlackAndKeepsAlpha() {
        assertEquals(0xFF404040.toInt(), Color(0xFF808080).darken(0.5f).toArgb())
        assertEquals(0xFF808080.toInt(), Color(0xFF808080).darken(0f).toArgb())
        assertEquals(0xFF000000.toInt(), Color(0xFF808080).darken(1f).toArgb())
        assertEquals(0x40808080.toInt(), Color(0x40808080).darken(0f).toArgb())
        assertEquals(0x40404040.toInt(), Color(0x40808080).darken().toArgb())
        assertEquals(0xFFCC6633.toInt(), Color(0xFFFF8040).darken(0.2f).toArgb())
    }

    @Test
    fun textStylesFollowTheMaterial3TypeScale() {
        val styles = piliNaraTextStyles()

        assertEquals(16.sp, styles.main.fontSize)
        assertEquals(24.sp, styles.main.lineHeight)
        assertEquals(0.5.sp, styles.main.letterSpacing)
        assertEquals(16.sp, styles.paragraph.fontSize)
        assertEquals(16.sp, styles.body1.fontSize)
        assertEquals(14.sp, styles.body2.fontSize)
        assertEquals(14.sp, styles.button.fontSize)
        assertEquals(12.sp, styles.footnote1.fontSize)
        assertEquals(11.sp, styles.footnote2.fontSize)
        assertEquals(16.sp, styles.headline1.fontSize)
        assertEquals(14.sp, styles.headline2.fontSize)
        assertEquals(14.sp, styles.subtitle.fontSize)
        assertEquals(32.sp, styles.title1.fontSize)
        assertEquals(24.sp, styles.title2.fontSize)
        assertEquals(22.sp, styles.title3.fontSize)
        assertEquals(16.sp, styles.title4.fontSize)

        assertEquals(FontWeight.Medium, styles.button.fontWeight)
        assertEquals(FontWeight.Bold, styles.subtitle.fontWeight)
        assertEquals(FontWeight.Normal, styles.title1.fontWeight)
        assertNotEquals(defaultTextStyles().main.fontSize, styles.main.fontSize)
    }

    @Test
    fun fontWeightOverrideAppliesToEverySlot() {
        val styles = piliNaraTextStyles(fontWeight = FontWeight.Bold)

        for (style in styles.all()) {
            assertEquals(FontWeight.Bold, style.fontWeight)
        }
    }

    private fun TextStyles.all() = listOf(
        main,
        paragraph,
        body1,
        body2,
        button,
        footnote1,
        footnote2,
        headline1,
        headline2,
        subtitle,
        title1,
        title2,
        title3,
        title4,
    )
}
