package com.luhyah.overlay.repository

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.luhyah.overlay.ui.theme.bitcountprop

data class OverlayUIState (

    val enabled: Boolean = false ,
    val overlayText: String = "" ,
    val fontSize: Float = 20f ,
    val xPosition: Float = 0f ,
    val yPosition: Float = 0f ,
    val fontType: FontFamily = bitcountprop ,
    val selectedType: String = "TEXT" ,
    val fontColor: Color = Color.White ,
    val bgColor: Color = Color.Black

)