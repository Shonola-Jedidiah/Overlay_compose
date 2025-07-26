package com.luhyah.overlay


import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.luhyah.overlay.ui.composables.Home
import com.luhyah.overlay.ui.theme.OverlayTheme
import com.luhyah.overlay.ui.viewmodel.OverlayViewModel


class MainActivity : ComponentActivity() {
    private val oVM: OverlayViewModel by viewModels()

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