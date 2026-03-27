package amber.messenger.desktop

import amber.arch.splash.SplashController
import amber.runtime.AppModules
import amber.ui.AppUi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skia.Image
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import java.awt.Taskbar
import javax.imageio.ImageIO

private fun loadIcon(resourcePath: String): BitmapPainter {
    val bytes = Thread.currentThread().contextClassLoader
        .getResourceAsStream(resourcePath)!!
        .use { it.readBytes() }
    return BitmapPainter(Image.makeFromEncoded(bytes).toComposeImageBitmap())
}

private fun setupTaskbarIcon(resourcePath: String) {
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

    startKoin { modules(AppModules) }
    getKoin().get<SplashController>().markReady()

    val icon = loadIcon("app-icon.png")
    setupTaskbarIcon("app-icon.png")

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Amber Messenger",
            icon = icon,
        ) {
            AppUi(modifier = Modifier.fillMaxSize())
        }
    }
}
