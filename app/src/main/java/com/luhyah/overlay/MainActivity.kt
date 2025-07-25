package com.luhyah.overlay


import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.luhyah.overlay.ui.composables.Home
import com.luhyah.overlay.ui.theme.OverlayTheme
import com.luhyah.overlay.ui.viewmodel.OverlayViewModel
import java.util.Locale


class MainActivity : ComponentActivity() {
    private val oVM: OverlayViewModel by viewModels()
//    private lateinit var countDownTimer: CountDownTimer
//    private var TimeLeft = mutableLongStateOf(0)
//    private val timeUp = mutableStateOf("00:00:00")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0 )
        }




        setContent {
            OverlayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Home(
                        modifier = Modifier.safeDrawingPadding()
                            /*.systemBarsPadding()*/.
                            padding(innerPadding), oVM = oVM, context =  this,
                        requestPermission =  { requestPermission() },
//                        startCountDownTimer = {StartCountDownTimer(it)},
//                        pauseCountDownTimer = {PauseCountDownTimer(oVM, TimeLeft, countDownTimer)},
//                        endCountDownTimer = {EndCountDownTimer()},
                        callService = {startService(it)}
                    )

                }
            }
        }
    }
    private val requestCode: Int = 1
    private fun requestPermission(){
        val intent =
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION ,
                ("package:$packageName").toUri()
            )
        Log.d("Error", "Called requestPermission")
        startActivityForResult(intent , requestCode)
    }


//    private fun StartCountDownTimer(timeInMin: Long){
//        if (oVM.isCountDownTimerActive)countDownTimer.cancel()
//        oVM.isCountDownTimerPaused = false
//
//        countDownTimer = object :CountDownTimer(timeInMin, 1000){
//            override fun onTick(millisUntilFinished: Long) {
//                TimeLeft.longValue = millisUntilFinished
//                var hours: Long = millisUntilFinished / (60 * 60 * 1000)
//                var minutes: Long = millisUntilFinished / (60 * 1000) % 60
//                var seconds: Long = (millisUntilFinished / 1000) % 60
//
//                Log.d("START", hours.toString())
//
//                timeUp.value = String.format(Locale.getDefault() , "%02d:%02d:%02d" , hours , minutes , seconds)
//                Log.d("START", timeUp.value)
//
//            }
//
//            override fun onFinish() {
//                timeUp.value = "00:00:00"
//                oVM.isCountDownTimerActive  = false
//            }
//
//        }
//
//        countDownTimer.start()
//        oVM.isCountDownTimerActive = true
//        Log.d("START", "ACTIVEEEEEEE")
//
//
//    }
//
//    private fun EndCountDownTimer(){
//        Log.d("End", "ERRRRRRRRRRRRRRRRRRRRR")
//        oVM.isCountDownTimerActive = false
//        oVM.isCountDownTimerPaused = false
//        countDownTimer.cancel()
//    }
//
//}
//private fun PauseCountDownTimer(oVM: OverlayViewModel , timeLeft: MutableState<Long> , countDownTimer: CountDownTimer){
//    oVM.remainingCountDownTime = timeLeft.value
//    oVM.isCountDownTimerPaused = true
//   if(countDownTimer!=null){
//       countDownTimer.cancel()
//   }



}





@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OverlayTheme {
        Greeting("Android")
    }
}