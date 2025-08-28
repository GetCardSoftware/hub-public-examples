package com.getcard.hub.getcardpay.example.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Adf_scanner: ImageVector
    get() {
        if (_Adf_scanner != null) return _Adf_scanner!!

        _Adf_scanner = ImageVector.Builder(
            name = "Adf_scanner",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(80f, 800f)
                verticalLineToRelative(-200f)
                quadToRelative(0f, -50f, 35f, -85f)
                reflectiveQuadToRelative(85f, -35f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(-320f)
                horizontalLineToRelative(480f)
                verticalLineToRelative(320f)
                horizontalLineToRelative(40f)
                quadToRelative(50f, 0f, 85f, 35f)
                reflectiveQuadToRelative(35f, 85f)
                verticalLineToRelative(200f)
                close()
                moveToRelative(240f, -320f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(-240f)
                horizontalLineTo(320f)
                close()
                moveTo(160f, 720f)
                horizontalLineToRelative(640f)
                verticalLineToRelative(-120f)
                quadToRelative(0f, -17f, -11.5f, -28.5f)
                reflectiveQuadTo(760f, 560f)
                horizontalLineTo(200f)
                quadToRelative(-17f, 0f, -28.5f, 11.5f)
                reflectiveQuadTo(160f, 600f)
                close()
                moveToRelative(560f, -40f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(760f, 640f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(720f, 600f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(680f, 640f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(720f, 680f)
                moveTo(160f, 560f)
                horizontalLineToRelative(640f)
                close()
            }
        }.build()

        return _Adf_scanner!!
    }

private var _Adf_scanner: ImageVector? = null

