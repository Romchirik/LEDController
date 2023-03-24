package nsu.titov.ledcontroller.ui.utils

interface UIMapper<F, T : UIModel> {

    fun toUi(source: F): T
}