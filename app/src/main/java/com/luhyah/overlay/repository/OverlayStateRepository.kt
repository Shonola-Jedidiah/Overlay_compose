package com.luhyah.overlay.repository

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object OverlayStateRepository {
    private val _overlayState = MutableStateFlow(OverlayUIState())
    val overlayState: StateFlow<OverlayUIState> = _overlayState

    fun updateState(update: OverlayUIState.() -> OverlayUIState) {
        _overlayState.value = _overlayState.value.update()
    }

    fun setOverlayText(text: String) = updateState { copy(overlayText = text) }
    fun setFontSize(size: Float) = updateState { copy(fontSize = size) }
    fun setFontColor(color: Color) = updateState { copy(fontColor = color) }
    fun setBgColor(color: Color) = updateState { copy(bgColor = color) }
    fun setXPosition(percent: Float) = updateState { copy(xPosition = percent) }
    fun setYPosition(percent: Float) = updateState { copy(yPosition = percent) }
    fun setFontType(type: FontFamily) = updateState { copy(fontType = type) }

}
