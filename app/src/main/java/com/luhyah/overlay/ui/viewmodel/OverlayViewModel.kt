package com.luhyah.overlay.ui.viewmodel



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import com.luhyah.overlay.repository.OverlayStateRepository
import com.luhyah.overlay.ui.theme.bitcountprop
import com.luhyah.overlay.ui.theme.chewy
import com.luhyah.overlay.ui.theme.comicrelief
import com.luhyah.overlay.ui.theme.roboto
import com.luhyah.overlay.ui.theme.spacegrotesk

class OverlayViewModel(): ViewModel() {


    var permissionNotGranted by mutableStateOf(true)

    var enabled by mutableStateOf(false)
    var overlayText by mutableStateOf("")
    var fontSize by mutableFloatStateOf(20f)
    var xPosition by mutableFloatStateOf(0f)
    var yPosition by mutableFloatStateOf(0f)
    var selectedTypeSize by mutableStateOf(Size.Zero)
    var typeExpanded by mutableStateOf(false)


    val fontTypes = hashMapOf(0 to bitcountprop, 1 to chewy, 2 to comicrelief, 3 to roboto, 4 to spacegrotesk)
    var fontType = fontTypes[0]
    var fontTxt by mutableStateOf("bitcountprop")
    var fontTypeSize by mutableStateOf(Size.Zero)
    var fontTypeExpanded by mutableStateOf(false)

    var redF by mutableFloatStateOf(0f)
    var greenF by mutableFloatStateOf(0f)
    var blueF by mutableFloatStateOf(0f)

    var redBg by mutableFloatStateOf(0f)
    var greenBg by mutableFloatStateOf(0f)
    var blueBg by mutableFloatStateOf(0f)

    var fontColor by mutableStateOf(Color.White)
    var bgColor by mutableStateOf(Color.Black)


    fun updateOverlayText(text: String) = OverlayStateRepository.setOverlayText(text)
    fun updateFontSize(size: Float) = OverlayStateRepository.setFontSize(size)
    fun updateFontColor(color: Color) = OverlayStateRepository.setFontColor(color)
    fun updateBgColor(color: Color) = OverlayStateRepository.setBgColor(color)
    fun updateXPosition(percent: Float) = OverlayStateRepository.setXPosition(percent)
    fun updateYPosition(percent: Float) = OverlayStateRepository.setYPosition(percent)
    fun updateFontType(type: FontFamily) = OverlayStateRepository.setFontType(type)


}
