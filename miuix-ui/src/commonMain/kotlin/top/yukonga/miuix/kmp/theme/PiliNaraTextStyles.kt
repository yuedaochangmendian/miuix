// Copyright 2026, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package top.yukonga.miuix.kmp.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * The Material 3 type scale PiliNara renders with, expressed as Miuix [TextStyles].
 *
 * PiliNara does not define its own type scale: it takes Flutter's default `Typography.material2021`
 * and only overrides the font family and, when the user picks something other than "normal", the
 * font weight of every role. This function reproduces that, mapping each of the fourteen Miuix text
 * slots onto the closest Material 3 role:
 *
 * | Miuix slot | Material 3 role | Size | Line height | Letter spacing | Weight |
 * | --- | --- | --- | --- | --- | --- |
 * | `main` | `bodyLarge` | 16sp | 24sp | 0.5sp | 400 |
 * | `paragraph` | `bodyLarge` | 16sp | 24sp | 0.5sp | 400 |
 * | `body1` | `bodyLarge` | 16sp | 24sp | 0.5sp | 400 |
 * | `body2` | `bodyMedium` | 14sp | 20sp | 0.25sp | 400 |
 * | `button` | `labelLarge` | 14sp | 20sp | 0.1sp | 500 |
 * | `footnote1` | `bodySmall` | 12sp | 16sp | 0.4sp | 400 |
 * | `footnote2` | `labelSmall` | 11sp | 16sp | 0.5sp | 500 |
 * | `headline1` | `titleMedium` | 16sp | 24sp | 0.15sp | 500 |
 * | `headline2` | `titleSmall` | 14sp | 20sp | 0.1sp | 500 |
 * | `subtitle` | `titleSmall` | 14sp | 20sp | 0.1sp | 700 |
 * | `title1` | `headlineLarge` | 32sp | 40sp | 0sp | 400 |
 * | `title2` | `headlineSmall` | 24sp | 32sp | 0sp | 400 |
 * | `title3` | `titleLarge` | 22sp | 28sp | 0sp | 400 |
 * | `title4` | `titleMedium` | 16sp | 24sp | 0.15sp | 500 |
 *
 * The two scales do not line up one to one, because Miuix declares fourteen slots with MIUI
 * proportions while Material 3 declares fifteen roles: `main`, `paragraph` and `body1` all fall back
 * to `bodyLarge`, and `subtitle` keeps the bold weight Miuix gives it. Nothing in the table is
 * invented, every size, line height and letter spacing is the Material 3 2021 baseline.
 *
 * @param fontFamily The font family to apply to every slot, `null` to keep the default font.
 * @param fontWeight The font weight to apply to every slot, `null` to keep each Material 3 role's
 *   own weight. PiliNara only replaces the weight when the user picks one, which is what `null` and
 *   an explicit value model here.
 */
fun piliNaraTextStyles(
    fontFamily: FontFamily? = null,
    fontWeight: FontWeight? = null,
): TextStyles {
    val bodyLarge = piliNaraTextStyle(16.sp, 24.sp, 0.5.sp, FontWeight.Normal, fontFamily, fontWeight)
    val bodyMedium = piliNaraTextStyle(14.sp, 20.sp, 0.25.sp, FontWeight.Normal, fontFamily, fontWeight)
    val bodySmall = piliNaraTextStyle(12.sp, 16.sp, 0.4.sp, FontWeight.Normal, fontFamily, fontWeight)
    val titleSmall = piliNaraTextStyle(14.sp, 20.sp, 0.1.sp, FontWeight.Medium, fontFamily, fontWeight)
    val titleMedium = piliNaraTextStyle(16.sp, 24.sp, 0.15.sp, FontWeight.Medium, fontFamily, fontWeight)
    return defaultTextStyles(
        main = bodyLarge,
        paragraph = bodyLarge,
        body1 = bodyLarge,
        body2 = bodyMedium,
        button = piliNaraTextStyle(14.sp, 20.sp, 0.1.sp, FontWeight.Medium, fontFamily, fontWeight),
        footnote1 = bodySmall,
        footnote2 = piliNaraTextStyle(11.sp, 16.sp, 0.5.sp, FontWeight.Medium, fontFamily, fontWeight),
        headline1 = titleMedium,
        headline2 = titleSmall,
        subtitle = piliNaraTextStyle(14.sp, 20.sp, 0.1.sp, FontWeight.Bold, fontFamily, fontWeight),
        title1 = piliNaraTextStyle(32.sp, 40.sp, 0.sp, FontWeight.Normal, fontFamily, fontWeight),
        title2 = piliNaraTextStyle(24.sp, 32.sp, 0.sp, FontWeight.Normal, fontFamily, fontWeight),
        title3 = piliNaraTextStyle(22.sp, 28.sp, 0.sp, FontWeight.Normal, fontFamily, fontWeight),
        title4 = titleMedium,
    )
}

private fun piliNaraTextStyle(
    fontSize: TextUnit,
    lineHeight: TextUnit,
    letterSpacing: TextUnit,
    fontWeight: FontWeight,
    fontFamily: FontFamily?,
    weightOverride: FontWeight?,
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontWeight = weightOverride ?: fontWeight,
    fontSize = fontSize,
    lineHeight = lineHeight,
    letterSpacing = letterSpacing,
)
