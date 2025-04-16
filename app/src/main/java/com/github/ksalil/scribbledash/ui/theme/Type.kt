package com.github.ksalil.scribbledash.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.github.ksalil.scribbledash.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val bagelFatOneFont = GoogleFont("Bagel Fat One")
val outfitFont = GoogleFont("Outfit")

val bagelFatOneFontFamily = FontFamily(
    Font(
        googleFont = bagelFatOneFont,
        fontProvider = provider,
        weight= FontWeight.Normal
    )
)

val outfitFontFamily = FontFamily(
    Font(
        googleFont = outfitFont,
        fontProvider = provider,
        weight = FontWeight.Normal
    ),
    Font(
        googleFont = outfitFont,
        fontProvider = provider,
        weight = FontWeight.Medium
    ),
    Font(
        googleFont = outfitFont,
        fontProvider = provider,
        weight = FontWeight.SemiBold
    )
)

val Typography = Typography(
    // Display Styles
    displayLarge = TextStyle(
        fontFamily = bagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 66.sp,
        lineHeight = 80.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = bagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = bagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp, // Derived assumption
        lineHeight = 48.sp, // Derived assumption
        letterSpacing = 0.sp
    ),

    // Headline Styles
    headlineLarge = TextStyle(
        fontFamily = bagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 48.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = bagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = bagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),

    // Title Styles
    titleLarge = TextStyle( // Mapped from "Label X-Large"
        fontFamily = outfitFontFamily, // Uses variable font
        fontWeight = FontWeight.SemiBold, // Selects SemiBold from outfit.ttf
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle( // Derived assumption
        fontFamily = outfitFontFamily, // Uses variable font
        fontWeight = FontWeight.Medium, // Selects Medium from outfit.ttf
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle( // Derived assumption
        fontFamily = outfitFontFamily, // Uses variable font
        fontWeight = FontWeight.Medium, // Selects Medium from outfit.ttf
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),

    // Body Styles
    bodyLarge = TextStyle(
        fontFamily = outfitFontFamily, // Uses variable font
        fontWeight = FontWeight.Medium, // Selects Medium from outfit.ttf
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp // Adjust if needed
    ),
    bodyMedium = TextStyle(
        fontFamily = outfitFontFamily, // Uses variable font
        fontWeight = FontWeight.Normal, // Selects Regular/Normal from outfit.ttf
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = outfitFontFamily, // Uses variable font
        fontWeight = FontWeight.Normal, // Selects Regular/Normal from outfit.ttf
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.4.sp
    ),

    // Label Styles
    labelLarge = TextStyle( // Mapped from "Label Large"
        fontFamily = outfitFontFamily, // Uses variable font
        fontWeight = FontWeight.SemiBold, // Selects SemiBold from outfit.ttf
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle( // Mapped from "Label Medium"
        fontFamily = outfitFontFamily, // Uses variable font
        fontWeight = FontWeight.Medium, // Selects Medium from outfit.ttf
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle( // Mapped from "Label Small"
        fontFamily = outfitFontFamily, // Uses variable font
        fontWeight = FontWeight.Normal, // Selects Regular/Normal from outfit.ttf
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp
    )
)