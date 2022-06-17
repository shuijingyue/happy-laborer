package pro.ply.gerrit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope

@Composable
fun ApplicationScope.Toolbar() {
    Row(modifier = Modifier.height(36.dp).fillMaxWidth().drawBehind {
        drawLine(WHITE_20, Offset(0f, size.height), Offset(size.width, size.height))
    }) {
        Text("关闭", modifier = Modifier.clickable { exitApplication() })
    }
}
