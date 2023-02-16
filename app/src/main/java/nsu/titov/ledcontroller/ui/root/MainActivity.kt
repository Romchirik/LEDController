package ru.nsu.ledcontroller.ui.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ru.nsu.ledcontroller.ui.editor.GraphicsEditorScreen
import ru.nsu.ledcontroller.ui.main.MainScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
             GraphicsEditorScreen()
        }
    }

}