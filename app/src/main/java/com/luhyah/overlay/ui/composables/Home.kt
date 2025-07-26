 package com.luhyah.overlay.ui.composables

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.RadioButtonChecked
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import com.luhyah.overlay.OverlayService
import com.luhyah.overlay.ui.viewmodel.OverlayViewModel


val fontArray = arrayOf("bitcountprop","chewy", "comicrelief", "roboto", "spacegrotesk")

@OptIn(ExperimentalMaterial3Api::class , ExperimentalLayoutApi::class)
@Composable
fun Home(
     modifier: Modifier,
     oVM: OverlayViewModel , context: Context , requestPermission: () -> Unit ,
     callService:(Intent) -> Unit
 ) {

     var fontDialog by rememberSaveable { mutableStateOf(false) }
     var bgDialog by rememberSaveable { mutableStateOf(false) }


     oVM.permissionNotGranted = !Settings.canDrawOverlays(context)

     if(oVM.permissionNotGranted){
         oVM.enabled = false
     }

     val interactionSource = remember { MutableInteractionSource() }
     Column(
         modifier = modifier
             .fillMaxWidth()
             .padding(15.dp) ,
         verticalArrangement = Arrangement.Top
     ) {
        if(oVM.permissionNotGranted){
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
                .clickable(onClick = requestPermission)){
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)){
                    Icon(Icons.Outlined.Info, contentDescription = "Allow Permission", tint = MaterialTheme.colorScheme.primary)
                    Text(text ="CLICK TO GRANT PERMISSION")
                }
            }
        }

         Card(modifier = Modifier
             .fillMaxWidth()
             .padding(bottom = 15.dp)) {
             //Radio
             Row(
                 verticalAlignment = Alignment.CenterVertically ,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(top = 10.dp , end = 10.dp , start = 10.dp)
             ) {
                 Text(text = "Enabled" , modifier = Modifier.weight(1f))
                 Switch(checked = oVM.enabled ,
                     onCheckedChange = { oVM.enabled = !oVM.enabled
                         if(oVM.enabled){callService(Intent(context, OverlayService::class.java).also {
                             it.action = OverlayService.Actions.START.toString()
                         })}else{
                             callService(Intent(context, OverlayService::class.java).also {
                                 it.action = OverlayService.Actions.STOP.toString()
                             })
                         }

                          }, enabled = !oVM.permissionNotGranted)
             }



             //fontType
             Row(
                 verticalAlignment = Alignment.CenterVertically ,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(top = 10.dp , end = 10.dp , start = 10.dp)
             ) {
                 Text(text = "Font" , modifier = Modifier.padding(end = 15.dp))
                 Row(modifier = Modifier
                     .clickable(onClick = { if(oVM.enabled){oVM.fontTypeExpanded = !oVM.fontTypeExpanded}

                     })
                     .padding(end = 10.dp)) {
                     Text(
                         text = oVM.fontTxt , modifier = Modifier
                             .weight(1f)
                             .align(Alignment.CenterVertically)
                             .onGloballyPositioned { it ->
                                 oVM.fontTypeSize = it.size.toSize()
                             }
                     )
                     Icon(Icons.Rounded.ArrowDropDown , contentDescription = "")
                 }
                 Box(modifier = Modifier.fillMaxWidth()){
                     DropdownMenu(

                         expanded = oVM.fontTypeExpanded ,
                         onDismissRequest = { oVM.fontTypeExpanded = false } ,
                         modifier = Modifier
                             .width(with(LocalDensity.current) { oVM.fontTypeSize.width.toDp() })
                             .align(Alignment.BottomCenter)
                     ) {
                         oVM.fontTypes.forEach {
                             DropdownMenuItem(
                                 text = {
                                     Text(text = fontArray[it.key])
                                 } ,
                                 onClick = {
                                     oVM.fontType = it.value
                                     oVM.fontTypeExpanded = false
                                     oVM.updateFontType(it.value)
                                     oVM.fontTxt = fontArray[it.key]
                                 }
                             )
                         }
                     }
                 }


             }

             //Font Size
             Row(
                 verticalAlignment = Alignment.CenterVertically ,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(top = 10.dp , end = 10.dp , start = 10.dp)
             ) {
                 Text(text = "Font Size" , modifier = Modifier.padding(end = 10.dp))
                 Slider(
                     enabled = oVM.enabled ,
                     value = oVM.fontSize ,
                     onValueChange = { oVM.fontSize = it
                                     oVM.updateFontSize(it)} ,
                     valueRange = 12f .. 60f ,
                     thumb = {
                         Label(
                             label = {
                                 PlainTooltip(
                                     modifier = Modifier
                                         .sizeIn(25.dp , 25.dp)
                                         .wrapContentWidth()
                                 ) {
                                     Text("%.2f".format(oVM.fontSize))
                                 }
                             } ,
                             interactionSource = interactionSource
                         ) {
                             Icon(
                                 imageVector = Icons.Rounded.RadioButtonChecked ,
                                 contentDescription = null ,
                                 modifier = Modifier.size(ButtonDefaults.IconSize) ,
                                 tint = MaterialTheme.colorScheme.primary
                             )
                         }
                     } ,
                     modifier = Modifier.weight(1f)
                 )
                 Text(text = oVM.fontSize.toInt().toString() , modifier = Modifier.padding(end = 15.dp))
             }

             //Colors
             Row(
                 verticalAlignment = Alignment.CenterVertically ,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(top = 10.dp , end = 10.dp , start = 10.dp , bottom = 10.dp)
             ) {
                 Button(
                     enabled = oVM.enabled ,
                     onClick = { fontDialog = !fontDialog } ,
                     modifier = Modifier.padding(end = 12.dp)
                 ) {
                     Text(text = "Font Color")
                 }
                 Button(enabled = oVM.enabled , onClick = { bgDialog = !bgDialog }) {
                     Text(text = "Bg Color")
                 }
             }
         }

         //Overlay Text and Timer Input
         Card(modifier = Modifier
             .fillMaxWidth()
             .padding(bottom = 15.dp)) {

             Row(
                    verticalAlignment = Alignment.CenterVertically ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp , end = 10.dp , start = 10.dp , bottom = 10.dp).imeNestedScroll()
                ) {
                    TextField(
                        enabled = oVM.enabled ,
                        value = oVM.overlayText , onValueChange = { oVM.overlayText = it
                                                                  oVM.updateOverlayText(it)} ,
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.EditNote ,
                                contentDescription = "Overlay Text" ,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        } ,
                        maxLines = 5 , modifier = Modifier.weight(1f).imePadding() ,
                        label = {
                            Text(text = "Input text here")
                        })

                }


         }

         // X and Y sliders
         Card(modifier = Modifier
             .fillMaxWidth()
             .padding(bottom = 5.dp).imeNestedScroll())
         {
             Row(
                 verticalAlignment = Alignment.CenterVertically ,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(top = 5.dp , end = 10.dp , start = 10.dp).imePadding()
             ) {
                 Text(text = "X" , modifier = Modifier.padding(end = 10.dp))
                 Slider(
                     enabled = oVM.enabled ,
                     value = oVM.xPosition ,
                     onValueChange = { oVM.xPosition = it
                                     oVM.updateXPosition(it)} ,
                     valueRange = 0f .. 100f ,
                     thumb = {
                         Label(
                             label = {
                                 PlainTooltip(
                                     modifier = Modifier
                                         .sizeIn(25.dp , 25.dp)
                                         .wrapContentWidth()
                                 ) {
                                     Text("%.2f".format(oVM.xPosition))
                                 }
                             } ,
                             interactionSource = interactionSource
                         ) {
                             Icon(
                                 imageVector = Icons.Rounded.RadioButtonChecked ,
                                 contentDescription = null ,
                                 modifier = Modifier.size(ButtonDefaults.IconSize) ,
                                 tint = MaterialTheme.colorScheme.primary
                             )
                         }
                     } ,
                     modifier = Modifier.weight(1f)
                 )
                 Text(
                     text = oVM.xPosition.toInt().toString() ,
                     modifier = Modifier.padding(end = 15.dp)
                 )
             }
             Row(
                 verticalAlignment = Alignment.CenterVertically ,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(end = 10.dp , start = 10.dp , bottom = 5.dp).imePadding()
             ) {
                 Text(text = "Y" , modifier = Modifier.padding(end = 10.dp))
                 Slider(
                     enabled = oVM.enabled ,
                     value = oVM.yPosition ,
                     onValueChange = { oVM.yPosition = it
                                     oVM.updateYPosition(it)} ,
                     valueRange = 0f .. 100f ,
                     thumb = {
                         Label(
                             label = {
                                 PlainTooltip(
                                     modifier = Modifier
                                         .sizeIn(25.dp , 25.dp)
                                         .wrapContentWidth()
                                 ) {
                                     Text("%.2f".format(oVM.fontSize))
                                 }
                             } ,
                             interactionSource = interactionSource
                         ) {
                             Icon(
                                 imageVector = Icons.Rounded.RadioButtonChecked ,
                                 contentDescription = null ,
                                 modifier = Modifier.size(ButtonDefaults.IconSize) ,
                                 tint = MaterialTheme.colorScheme.primary
                             )
                         }
                     } ,
                     modifier = Modifier.weight(1f)
                 )
                 Text(
                     text = oVM.yPosition.toInt().toString() ,
                     modifier = Modifier.padding(end = 15.dp)
                 )
             }
         }

         if (fontDialog) {
             ColorDialog(
                 interactionSource = interactionSource ,
                 onDismissRequest = { fontDialog = !fontDialog } ,
                 onConfirmRequest = {
                     oVM.fontColor = Color(oVM.redF , oVM.greenF , oVM.blueF)
                     fontDialog = !fontDialog
                     oVM.updateFontColor(oVM.fontColor)
                 } ,
                 bgColor = oVM.bgColor ,
                 fontColor = oVM.fontColor ,
                 red = oVM.redF ,
                 blue = oVM.blueF ,
                 green = oVM.greenF ,
                 onValueChangeRed = { oVM.redF = it } ,
                 onValueChangeGreen = { oVM.greenF = it } ,
                 onValueChangeBlue = { oVM.blueF = it } ,
                 type = "f"
             )
         }

         if (bgDialog) {
             ColorDialog(
                 interactionSource = interactionSource ,
                 onDismissRequest = { bgDialog = !bgDialog } ,
                 onConfirmRequest = {
                     oVM.bgColor = Color(oVM.redBg , oVM.greenBg , oVM.blueBg)
                     bgDialog = !bgDialog
                     oVM.updateBgColor(oVM.bgColor)
                 } ,
                 bgColor = oVM.bgColor ,
                 fontColor = oVM.fontColor ,
                 red = oVM.redBg ,
                 blue = oVM.blueBg ,
                 green = oVM.greenBg ,
                 onValueChangeRed = { oVM.redBg = it } ,
                 onValueChangeGreen = { oVM.greenBg = it } ,
                 onValueChangeBlue = { oVM.blueBg = it } ,
                 type = "b"
             )
         }
         
     }
 }


 @OptIn(ExperimentalMaterial3Api::class)
 @Composable
 fun ColorDialog( onDismissRequest:()-> Unit , onConfirmRequest:()-> Unit ,
                 bgColor: Color , fontColor: Color ,
                 red: Float , green: Float , blue: Float ,
                 onValueChangeRed:(Float)-> Unit , onValueChangeGreen:(Float)-> Unit , onValueChangeBlue:(Float)-> Unit ,
                 interactionSource: MutableInteractionSource ,
                 type:String){
     Dialog(onDismissRequest = onDismissRequest) {
         Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

             Card(shape = RoundedCornerShape(16.dp), modifier = Modifier
                 .fillMaxWidth(0.75f)
                 .fillMaxHeight(0.35f)) {

                 Column(modifier = Modifier
                     .fillMaxSize()
                     .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                     Row(modifier = Modifier
                         .fillMaxWidth(0.75f)
                         .fillMaxHeight(0.25f)
                         .background(
                             color = if (type == "b") {
                                 Color(red , green , blue)
                             } else {
                                 bgColor
                             }
                         )
                         .align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically,
                         horizontalArrangement = Arrangement.Center, ) {
                         Text(text = "Lorem Ipsum", color = if(type=="f"){Color(red,green,blue)}else{fontColor})
                     }
                     Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                         Text(text = "R" , modifier = Modifier.padding(end = 10.dp))
                         Slider(
                             value = red ,
                             onValueChange = onValueChangeRed ,
                             valueRange = 0f .. 1f ,
                             thumb = {
                                 Label(
                                     label = {
                                         PlainTooltip(modifier = Modifier
                                             .sizeIn(25.dp , 25.dp)
                                             .wrapContentWidth()) {
                                             Text("%.2f".format(red))
                                         }
                                     } , interactionSource = interactionSource) {
                                     Icon(
                                         imageVector = Icons.Rounded.RadioButtonChecked ,
                                         contentDescription = null ,
                                         modifier = Modifier.size(ButtonDefaults.IconSize) ,
                                         tint = Color.Red
                                     )
                                 }
                             },
                             colors = SliderDefaults.colors(activeTrackColor = Color.Red),
                             modifier = Modifier.weight(1f)
                         )
                         Text(text = (red*255).toInt().toString(), modifier = Modifier.padding(end = 15.dp))
                     }
                     Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                         Text(text = "G" , modifier = Modifier.padding(end = 10.dp))
                         Slider(
                             value = green ,
                             onValueChange = onValueChangeGreen,
                             valueRange = 0f .. 1f ,
                             thumb = {
                                 Label(
                                     label = {
                                         PlainTooltip(modifier = Modifier
                                             .sizeIn(25.dp , 25.dp)
                                             .wrapContentWidth()) {
                                             Text("%.2f".format(green))
                                         }
                                     } , interactionSource = interactionSource) {
                                     Icon(
                                         imageVector = Icons.Rounded.RadioButtonChecked ,
                                         contentDescription = null ,
                                         modifier = Modifier.size(ButtonDefaults.IconSize) ,
                                         tint = Color.Green
                                     )
                                 }
                             },
                             colors = SliderDefaults.colors(activeTrackColor = Color.Green),
                             modifier = Modifier.weight(1f)
                         )
                         Text(text = (green*255).toInt().toString(), modifier = Modifier.padding(end = 15.dp))
                     }
                     Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                         Text(text = "B" , modifier = Modifier.padding(end = 10.dp))
                         Slider(
                             value = blue ,
                             onValueChange = onValueChangeBlue,
                             valueRange = 0f .. 1f ,
                             thumb = {
                                 Label(
                                     label = {
                                         PlainTooltip(modifier = Modifier
                                             .sizeIn(25.dp , 25.dp)
                                             .wrapContentWidth()) {
                                             Text("%.2f".format(blue))
                                         }
                                     } , interactionSource = interactionSource) {
                                     Icon(
                                         imageVector = Icons.Rounded.RadioButtonChecked ,
                                         contentDescription = null ,
                                         modifier = Modifier.size(ButtonDefaults.IconSize) ,
                                         tint = Color.Blue
                                     )
                                 }
                             },
                             colors = SliderDefaults.colors(activeTrackColor = Color.Blue),
                             modifier = Modifier.weight(1f)
                         )
                         Text(text = (blue*255).toInt().toString(), modifier = Modifier.padding(end = 15.dp))
                     }

                     Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                         TextButton(onClick = onDismissRequest, modifier = Modifier.padding(8.dp)) { Text("Dismiss") }

                         TextButton(onClick = onConfirmRequest, modifier = Modifier.padding(8.dp)) { Text("Confirm") }
                     }
                 }

             }
         }
     }
 }

 @Preview(showSystemUi = true)
 @Composable
 fun HomePrev(){
//     Home()
 }
