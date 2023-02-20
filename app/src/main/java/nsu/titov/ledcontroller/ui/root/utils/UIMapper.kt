package nsu.titov.ledcontroller.ui.root.utils

interface UIMapper<F, T: UIModel> {

    fun toUi(source: F): T
}