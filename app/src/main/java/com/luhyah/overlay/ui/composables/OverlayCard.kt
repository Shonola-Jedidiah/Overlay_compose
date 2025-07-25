package com.luhyah.overlay.ui.composables


import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luhyah.overlay.repository.OverlayStateRepository
import com.luhyah.overlay.ui.viewmodel.OverlayViewModel

@Composable
fun OverlayCard(oVMC: OverlayViewModel = OverlayViewModel()){
    val state by OverlayStateRepository.overlayState.collectAsState()

    val xScreen = LocalConfiguration.current.screenWidthDp
    val yScreen = LocalConfiguration.current.screenHeightDp

    var xCard by remember { mutableIntStateOf(0) }
    var yCard by remember { mutableIntStateOf(0) }

    var x = ((xScreen - xCard/LocalDensity.current.density)*state.xPosition/100).toInt()
    var y = ((yScreen - yCard/LocalDensity.current.density)*state.yPosition/100).toInt()

    Card(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .onGloballyPositioned { it ->
                xCard = it.size.width
                yCard = it.size.height
            }
            .offset(x = x.dp , y = y.dp) ,
        colors = CardDefaults.cardColors(containerColor = state.bgColor)
    ) {
        Text(modifier = Modifier.padding(3.dp) , text = state.overlayText,
            fontSize = state.fontSize.sp,
            fontFamily = state.fontType,
            color = state.fontColor)


//        when(state.selectedType) {
//            oVMC.overlayTypes[0] -> {
//                Text(modifier = Modifier.padding(3.dp) , text = "00:00:00", fontSize = state.fontSize.sp , color = state.fontColor)
//            }
//
//            oVMC.overlayTypes[1] -> {
//                Text(modifier = Modifier.padding(3.dp) , text = state.countUpTimeString, fontSize = state.fontSize.sp , color = state.fontColor)
//            }
//
//            oVMC.overlayTypes[2] -> {
//                Text(modifier = Modifier.padding(3.dp) , text = state.overlayText , fontSize = state.fontSize.sp , color = state.fontColor)
//            }
//        }
    }
}







//fun CountUpTimer(mode:String, handler: Handler, oVM: OverlayViewModel){
//    //mode = start and stop
//    if (mode == "stop") {
//        handler.removeCallbacksAndMessages(null)
//        oVM.countUpTime = 0
//        oVM.countUpTimeString = "00:00:00"
//        oVM.updateCountUpTimeString(oVM.countUpTimeString)
//    }
//    else {
//        handler.post(object : Runnable {
//            override fun run() {
//
//
//                oVM.countUpTime++
//
//                val hours = TimeUnit.SECONDS.toHours(oVM.countUpTime)
//                val remainder: Long = oVM.countUpTime - TimeUnit.HOURS.toSeconds(hours)
//                val minutes = TimeUnit.SECONDS.toMinutes(remainder)
//                val seconds = remainder - TimeUnit.MINUTES.toSeconds(minutes)
//
//                oVM.countUpTimeString = String.format(Locale.getDefault() ,
//                    "%02d:%02d:%02d" , hours , minutes , seconds
//                )
//
//                oVM.updateCountUpTimeString(oVM.countUpTimeString)
//                handler.postDelayed(this, 1000)
//            }
//        })
//    }
//
//
//}