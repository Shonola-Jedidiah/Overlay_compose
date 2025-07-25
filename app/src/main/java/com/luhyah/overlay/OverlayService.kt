package com.luhyah.overlay


import android.content.Intent
import android.graphics.PixelFormat
import android.view.View
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.luhyah.overlay.ui.composables.OverlayCard

class OverlayService() :  LifecycleService(),
    SavedStateRegistryOwner{

    enum class Actions{ START , STOP }
    private lateinit var windowManager: WindowManager
    private lateinit var contentView: View
    private val savedStateRegistryController
    = SavedStateRegistryController.create(this)

    override fun onCreate() {
        super.onCreate()

        windowManager= getSystemService(WINDOW_SERVICE)!! as WindowManager

        savedStateRegistryController.performRestore(null)

        contentView = ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@OverlayService)
            setViewTreeSavedStateRegistryOwner(this@OverlayService)

            setContent {
                OverlayCard()
            }

        }

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT ,
            WindowManager.LayoutParams.MATCH_PARENT ,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY ,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ,
            PixelFormat.TRANSPARENT
        )
        windowManager.addView(contentView, layoutParams)

    }



    override fun onStartCommand(intent: Intent? , flags: Int , startId: Int): Int {
        when(intent?.action){
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent , flags , startId)
    }

    private fun start(){
        val notification = NotificationCompat.Builder(this, "Overlay_Channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Overlay")
            .setContentText("Set to overlaid content")
            .build()
        startForeground(1, notification)
    }

    @Deprecated("Android Stuffs")
    override fun onStart(intent: Intent? , startId: Int) {
        super.onStart(intent , startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(contentView)
    }

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry
}