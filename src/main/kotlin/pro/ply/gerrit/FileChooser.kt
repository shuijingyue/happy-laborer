package pro.ply.gerrit

import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import java.awt.FileDialog
import java.awt.HeadlessException
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

@Composable
fun FileChooserDialog(
    title: String,
    currentDirectionality: File = File(System.getProperty("user.dir")),
    onError: (Exception?) -> Unit = {},
    onCancel: () -> Unit = {},
    onResult: (result: File) -> Unit,
) {
    val fileChooser = JFileChooser(FileSystemView.getFileSystemView())

    fileChooser.currentDirectory = currentDirectionality
    fileChooser.dialogTitle = title
    fileChooser.fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
    fileChooser.isAcceptAllFileFilterUsed = true

    try {
        when (fileChooser.showOpenDialog(null)) {
            JFileChooser.APPROVE_OPTION -> onResult(fileChooser.selectedFile)
            JFileChooser.CANCEL_OPTION -> onCancel()
            JFileChooser.ERROR_OPTION -> onError(null)
        }
    } catch (e: HeadlessException) {
        onError(e)
    }
}

fun openFile(
    title: String,
    curDir: File = File(System.getProperty("user.dir")),
    onCancel: () -> Unit = {},
    onResult: (result: File) -> Unit,
) {
    val dialog = FileDialog(ComposeWindow())
    dialog.title = title
    dialog.directory = curDir.absolutePath
    dialog.isVisible = true
    val file = dialog.file
    val dir = dialog.directory

    if (file.isNullOrEmpty() || dir.isNullOrEmpty()) {
        onCancel()
    } else {
        onResult(File("${dir}/${file}"))
    }
}
