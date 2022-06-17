package pro.ply.gerrit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pro.ply.gerrit.data.bean.ChangeInfo
import pro.ply.gerrit.data.bean.GerritQueryString
import pro.ply.gerrit.data.repository.RemoteChangeRepository

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App() {

    val scope = rememberCoroutineScope()
    var changes by remember { mutableStateOf<List<ChangeInfo>>(emptyList()) }
    var start by remember { mutableStateOf(0) }
    var id by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(-1) }

    fun refresh() {
        scope.launch {
            val qs = GerritQueryString().apply { put("branch", "alpha") }
            changes = RemoteChangeRepository.queryChanges(qs, start, 25)
        }
    }
    Column {
        Row {
            Button(onClick = {
                scope.launch {
                    start += 25
                    refresh()
                }
            }) {
                Text("next")
            }

            Button(
                onClick = {
                    scope.launch {
                        start = 0.coerceAtLeast(start - 25)
                        refresh()
                    }
                },
                enabled = start > 0
            ) {
                Text("prev")
            }
        }

        Text(
            if (selected > -1) "branch: $selected" else "select branch",
            Modifier.clickable(enabled = !expanded) { expanded = !expanded })

        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
            println("dropdown")
        }, modifier = Modifier.padding(vertical = 0.dp)) {
            listOf(1, 2, 3).forEach {
                DropdownMenuItem(onClick = {
                    selected = it
                    expanded = false
                }, modifier = Modifier.height(16.dp)) { Text("$it") }
            }
        }

        SelectionContainer() {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(changes.size, key = { changes[it].id.hashCode() }) {
                    val change = changes[it]
                    Text(
                        change.id,
                        modifier = Modifier.width(if (id == changes[it].id) Dp.Infinity else 100.dp)
                            .background(if (id == change.id) Color(0xFF99EE00) else Color.Transparent)
                            .padding(5.dp)
                            .onPointerEvent(PointerEventType.Enter) { id = change.id }
                            .onPointerEvent(PointerEventType.Exit) { id = "" },
                        maxLines = 1,
                        fontWeight = FontWeight(if (id == change.id) 600 else 400),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
