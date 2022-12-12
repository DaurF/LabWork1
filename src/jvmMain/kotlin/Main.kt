// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

val lowerCase = arrayOf("a", "b", "c", "d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z")
val upperCase = arrayOf("A","B","C","D","E","F","G","H","I", "J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")

@Composable
@Preview
fun App() {
    var sourceTextState by remember { mutableStateOf("") }
    var encryptedTextState by remember { mutableStateOf("") }
    var decipheredTextState by remember { mutableStateOf("") }
    var key by remember { mutableStateOf("") }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center).then(Modifier.width(300.dp))) {
                Text("Caesar Method",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(50.dp))
                TextField(
                    value = sourceTextState,
                    onValueChange = { sourceTextState = it },
                    modifier = Modifier.width(500.dp),
                    label = { Text("source text") },
                    placeholder = { Text("Enter your text") }
                )
                Spacer(modifier = Modifier.height(25.dp))
                TextField(
                    value = encryptedTextState,
                    onValueChange ={
                        encryptedTextState = it
                    },
                    modifier = Modifier.width(500.dp),
                    label = { Text("encrypted text") }
                )
                Spacer(modifier = Modifier.height(25.dp))
                TextField(
                    value = decipheredTextState,
                    onValueChange ={
                        decipheredTextState = it
                    },
                    modifier = Modifier.width(500.dp),
                    label = { Text("deciphered text") }
                )
                Spacer(modifier = Modifier.height(50.dp))
                TextField(
                    value = key,
                    onValueChange = {
                        key = it
                    },
                    modifier = Modifier.width(300.dp),
                    label = { Text("k") }
                )
                Spacer(modifier = Modifier.height(50.dp))
                Row {
                    Button(
                        content = {
                            Text("encrypt", fontSize = 24.sp)
                        },
                        onClick = {
                            val sourceText = sourceTextState
                            val k = Integer.parseInt(key)
                            encryptedTextState = encrypt(sourceText, k)
                        }
                    )
                    Spacer(Modifier.weight(3f))
                    Button(
                        content = {
                            Text("decrypt", fontSize = 24.sp)
                        },
                        onClick = {
                            val encryptedText = encryptedTextState
                            val k = Integer.parseInt(key)
                            decipheredTextState = decipher(encryptedText, k)
                        }
                    )
                }
            }
        }

    }
}

fun decipher(text: String, k: Int): String {
    var result = ""
    for(s in text) {
        if (s == ' ') result += " "
        if (upperCase.contains(s.toString())) {
            if ((upperCase.indexOf(s.toString()) - k) % 26 < 0) {
                result += upperCase[upperCase.size - (-1) * (upperCase.indexOf(s.toString()) - k) % 26]
                continue
            }
            result += upperCase[(upperCase.indexOf(s.toString()) - k) % 26]
        }

        if (lowerCase.contains(s.toString())) {
            if ((lowerCase.indexOf(s.toString()) - k) % 26 < 0) {
                result += lowerCase[lowerCase.size - (-1) * (lowerCase.indexOf(s.toString()) - k) % 25]
                continue
            }
            result += lowerCase[(lowerCase.indexOf(s.toString()) - k) % 26]
        }

    }
    return result
}

fun encrypt(text: String, k: Int): String {
    var result = ""
    for (s in text) {
        if (s == ' ') result += " "
        if (upperCase.contains(s.toString()))
            result += upperCase[(upperCase.indexOf(s.toString()) + k) % 26]
        if (lowerCase.contains(s.toString()))
            result += lowerCase[(lowerCase.indexOf(s.toString()) + k) % 26]
    }
    return result
}

fun main() = application {
    Window(title = "Laboratory Work 1", onCloseRequest = ::exitApplication) {
        App()
    }
}
