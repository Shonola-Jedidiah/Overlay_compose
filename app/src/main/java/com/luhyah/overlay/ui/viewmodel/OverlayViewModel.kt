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
//    var overlayTimerLong by mutableLongStateOf(0)
//    var overlayTimerString by mutableStateOf("")
    var fontSize by mutableFloatStateOf(20f)
    var xPosition by mutableFloatStateOf(0f)
    var yPosition by mutableFloatStateOf(0f)

//    val overlayTypes = arrayOf("COUNTDOWN TIMER" , "COUNT-UP TIMER" , "TEXT")
//    var selectedType by mutableStateOf(overlayTypes[2])
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

//    var remainingCountDownTime by mutableLongStateOf(0)
//    var isCountDownTimerActive by mutableStateOf(false)
//    var isCountDownTimerPaused by mutableStateOf(false)

//    var isCountUpTimerActive by mutableStateOf(false)
//    var countUpTime by mutableLongStateOf(0)
//    var countUpTimeString by mutableStateOf("00:00:00")

//    val overlayState: StateFlow<OverlayUIState> = OverlayStateRepository.overlayState

    fun updateOverlayText(text: String) = OverlayStateRepository.setOverlayText(text)
    fun updateFontSize(size: Float) = OverlayStateRepository.setFontSize(size)
    fun updateFontColor(color: Color) = OverlayStateRepository.setFontColor(color)
    fun updateBgColor(color: Color) = OverlayStateRepository.setBgColor(color)
//    fun updateOverlayType(type: String) = OverlayStateRepository.setSelectedType(type)
    fun updateXPosition(percent: Float) = OverlayStateRepository.setXPosition(percent)
    fun updateYPosition(percent: Float) = OverlayStateRepository.setYPosition(percent)
    fun updateFontType(type: FontFamily) = OverlayStateRepository.setFontType(type)
//    fun updateOverlayTimerText(type: String) = OverlayStateRepository.setOverlayTimerText(type)
//    fun updateCountUpTimeString(time: String) = OverlayStateRepository.setCountUpTimeString(time)

//    val currentOverlayText get() = overlayState.value.overlayText
//    val currentFontSize get() = overlayState.value.fontSize
//    val currentFontColor get() = overlayState.value.fontColor
//    val currentBgColor get() = overlayState.value.bgColor
//    val currentOverlayType get() = overlayState.value.selectedType
//    val currentXPosition get() = overlayState.value.xPosition
//    val currentYPosition get() = overlayState.value.yPosition

}
