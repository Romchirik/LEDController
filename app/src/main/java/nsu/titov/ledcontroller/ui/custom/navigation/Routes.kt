package nsu.titov.ledcontroller.ui.custom.navigation

object Routes {

    const val EditCanvas = "base_canvas_edit/{${ArgsKey.Key}}"

    const val EditLayer = "layer_edit/{${ArgsKey.Key}}"
    const val EditScene = "scene_edit/{${ArgsKey.Key}}"
}

object ArgsKey {

    const val Key = "key"
}