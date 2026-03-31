package amber.imageloading

import coil3.ImageLoader
import coil3.asImage
import coil3.decode.DecodeResult
import coil3.decode.Decoder
import coil3.fetch.SourceFetchResult
import coil3.request.Options
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.Java2DFrameConverter
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image as SkiaImage

internal class JvmVideoFrameDecoder(
    private val source: SourceFetchResult,
) : Decoder {
    override suspend fun decode(): DecodeResult {
        val filePath = source.source.file().toString()
        val pngBytes = extractFrameAsPng(filePath)

        val skiaImage = SkiaImage.makeFromEncoded(pngBytes)
        val bitmap = Bitmap()
        bitmap.allocPixels(skiaImage.imageInfo)
        skiaImage.readPixels(bitmap, 0, 0)

        return DecodeResult(
            image = bitmap.asImage(),
            isSampled = false,
        )
    }

    private fun extractFrameAsPng(filePath: String): ByteArray {
        val grabber = FFmpegFrameGrabber(filePath)
        return try {
            grabber.start()
            val frame = grabber.grabImage() ?: error("No video frame found in $filePath")
            val converter = Java2DFrameConverter()
            val bufferedImage = converter.convert(frame)
            ByteArrayOutputStream().use { baos ->
                ImageIO.write(bufferedImage, "png", baos)
                baos.toByteArray()
            }
        } finally {
            grabber.stop()
        }
    }

    class Factory : Decoder.Factory {
        override fun create(
            result: SourceFetchResult,
            options: Options,
            imageLoader: ImageLoader,
        ): Decoder? {
            if (!isVideoSource(result)) return null
            return JvmVideoFrameDecoder(result)
        }
    }
}
