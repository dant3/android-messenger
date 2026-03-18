package amber.messenger.desktop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skia.Image
import java.awt.Taskbar
import javax.imageio.ImageIO

private fun loadIcon(resourcePath: String): BitmapPainter {
    val bytes = Thread.currentThread().contextClassLoader
        .getResourceAsStream(resourcePath)!!
        .use { it.readBytes() }
    return BitmapPainter(Image.makeFromEncoded(bytes).toComposeImageBitmap())
}

private fun setupMacOsDockIcon(resourcePath: String) {
    if (!Taskbar.isTaskbarSupported()) return
    val taskbar = Taskbar.getTaskbar()
    if (!taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) return
    val image = Thread.currentThread().contextClassLoader
        .getResourceAsStream(resourcePath)!!
        .use(ImageIO::read)
    taskbar.iconImage = image
}

fun main() {
    System.setProperty("apple.awt.application.name", "Amber")

    val icon = loadIcon("app-icon.png")
    setupMacOsDockIcon("app-icon.png")

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Amber Messenger",
            icon = icon,
        ) {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text("Amber Messenger Desktop")
                    }
                }
            }
        }
    }
}
