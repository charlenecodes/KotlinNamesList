package com.example.kotlinandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.hsl
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlinandroid.ui.theme.KotlinAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinAndroidTheme {
                // we add our state in here
                var nameInput by remember {
                    mutableStateOf("")
                }
                // we use by so we do not have to use name.value
                // remember is a way to keep track of the state and remain where it is at when something is re-composed (when the item that uses state is re-rendered when the value changes)
                // var is a variable with a value that can be changed (think let in JS)
                // val is a constant

                var submittedName by remember {
                    mutableStateOf("")
                }

                var showGreeting by remember {
                    mutableStateOf(false)
                }

                var editMode by remember {
                    mutableStateOf(false)
                }

                var isComplete by remember {
                    mutableStateOf(false)
                }

                val darkWhite = hsl(
                    hue = 240f,
                    saturation = 0.238f,
                    lightness = 0.959f
                )

                val names = remember {
                    mutableStateListOf<String>()
                }

                var nameIndex by remember {
                    mutableIntStateOf(0)
                }

                val backgroundColor = if (isSystemInDarkTheme()) Black else White

                val myTextColor = if (isSystemInDarkTheme()) White else colorResource(id = R.color.indigo)

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = backgroundColor
                        )
                ) {
//                    Log.d("isDarkMode", isSystemInDarkTheme().toString())

                    Text("Names List 📝",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = myTextColor
                    )
                    if (showGreeting) {
                        LazyColumn(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(color = darkWhite)
                        ) {
                            items(names) {currentName ->
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp, vertical = 3.dp)
                                ){
                                    if (isComplete) {
                                        Icon(
                                            Icons.Rounded.CheckCircle,
                                            contentDescription = "status",
                                            tint = Green
                                        )
                                    } else {
                                        Icon(
                                            // added in res/drawable -> New > VectorAsset
                                            imageVector = ImageVector.vectorResource(id = R.drawable.outline_circle_24),
                                            contentDescription = "status",
                                            tint = colorResource(id = R.color.indigo)
                                        )
                                    }
//                                  putting Greeting in the Box takes up the available space but doesn't push the icons out of view.

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = 8.dp, end = 8.dp) // Add padding for better spacing
                                    ) {
                                        Greeting(
                                            name = currentName,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    isComplete = !isComplete
                                                }
                                        )
                                    }
// a spacer doesn't seem to be necessary with the box added since it contains it already
//                                    Spacer(modifier = Modifier.weight(1f))

                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = colorResource(id = R.color.indigo),
                                        modifier = Modifier
                                            .clickable {
                                                nameInput = currentName
                                                editMode = true
//                                                names = names - currentName
                                                nameIndex = names.lastIndexOf(currentName)
                                                Log.d("INDEX", "$nameIndex")
                                            }
                                    )

                                    Icon(
                                        Icons.Outlined.Delete,
                                        contentDescription = "Delete",
                                        tint = colorResource(id = R.color.indigo),
                                        modifier = Modifier
                                            .clickable {
                                                names.remove(currentName)
                                            }
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        // rows are arranged horizontally
                        // Arrangement is the main axis while Alignment is the cross-axis
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom,
                        // we can modify how something looks in Compose by setting modifier like this, which is somewhat similar to SwiftUI
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        Column {
                            OutlinedTextField(

                                singleLine = true,
                                value = nameInput,
                                onValueChange = { text: String ->
                                    nameInput = text
                                    submittedName = text
                                },
                                placeholder = {
                                    Text("Enter your name...",
                                        color = Gray,
                                        fontSize = 20.sp,
                                    )
                                },
                                textStyle = TextStyle(
                                    color = colorResource(id = R.color.indigo),
                                    fontSize = 20.sp,
                                ),
                                shape = RoundedCornerShape(30),
                                // this was hard to figure out because there were no prompts, just from https://stackoverflow.com/questions/66453775/how-to-change-the-outline-color-of-outlinedtextfield-from-jetpack-compose#:~:text=For%20those%20looking%20to%20customize,focused%20and%20red%20when%20unfocused.
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = darkWhite,
                                    unfocusedContainerColor = darkWhite,
                                    focusedIndicatorColor = colorResource(id = R.color.indigo),
                                    unfocusedIndicatorColor = LightGray,
                                    focusedTrailingIconColor = darkWhite,
                                    cursorColor = colorResource(id = R.color.indigo)
                                ),
                                // the trailing icon is placed inside the TextField, which is super practical because it can be done in a small amount of code
                                trailingIcon = {
                                    if (nameInput.isNotBlank()) {
                                        Icon(
                                            Icons.Default.Clear,
                                            contentDescription = "cancelButton",
                                            modifier = Modifier
                                                // .clickable allows the simple way to reset the state
                                                .clickable {
                                                    nameInput = ""
                                                }
                                                .size(20.dp)
                                                .fillMaxSize()
                                                .background(
                                                    color = Gray,
                                                    shape = RoundedCornerShape(100)
                                                )
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp),
                            )
                            Button(
                                onClick = {
                                    if (nameInput.isNotBlank() && !editMode) {
                                        showGreeting = true
                                        names += nameInput
                                        nameInput= ""
                                    } else if (nameInput.isNotBlank() && editMode) {
                                        // the order just had to be this way for it to work properly
                                        names[nameIndex] = nameInput
                                        editMode = false
                                        nameInput= ""
                                    }
                                },
                                modifier = Modifier
                                    .background(
                                        shape = RoundedCornerShape(size = 10.dp),
                                        color = colorResource(id = R.color.indigo)
                                    )
                                    .padding(vertical = 1.dp)
                                    .fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.indigo)
                                ),
//                                shape = RoundedCornerShape(size = 10.dp),
                                ) {
                                Text(
                                    text = "SUBMIT",
                                    fontSize = 20.sp,
                                    color = White
                                )
                            }
                        }

                    }

                }
            }
        }
    }
}

@Composable
// having modifier like this allows the item (?) to be modified from the outside
fun Greeting(name: String, modifier: Modifier) {
    Text(
        text = name,
        modifier
    )
}