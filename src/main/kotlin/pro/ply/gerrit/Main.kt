package pro.ply.gerrit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import pro.ply.gerrit.conf.ConfigurationManager
import pro.ply.gerrit.data.api.AuthorizationHelper

fun main() = application {

    ConfigurationManager.addObserver(AuthorizationHelper)
    ConfigurationManager.loadConfigurations()

    Window(
        onCloseRequest = ::exitApplication,
        undecorated = true,
        transparent = true,
        icon = painterResource("drawable/app.svg"),
    ) {
        Surface(
            shape = RoundedCornerShape(10f),
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column  {
                WindowDraggableArea {
                    Toolbar()
                }
                App()
            }
        }
    }
}
