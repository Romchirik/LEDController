package nsu.titov.ledcontroller.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nsu.titov.ledcontroller.ui.editor.FrameEditorScreen
import nsu.titov.ledcontroller.ui.effects.EffectsScreen

@Composable
@Preview
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "canvas_editor") {
        composable("canvas_editor") {
            FrameEditorScreen {
                navController.navigate("effects_applier") {
                    popUpTo("effects_applier")
                }
            }
        }
        composable("effects_applier") { EffectsScreen() }
    }
}