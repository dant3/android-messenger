package amber.imageloading

import coil3.ImageLoader
import coil3.asImage
import coil3.decode.DecodeResult
import coil3.decode.Decoder
import coil3.fetch.SourceFetchResult
import coil3.request.Options
import io.ktor.client.request.invoke
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image as SkiaImage
import platform.AVFoundation.AVAssetImageGenerator
import platform.AVFoundation.AVURLAsset
import platform.CoreMedia.CMTimeMake
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy

internal class IosVideoFrameDecoder(
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

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    private fun extractFrameAsPng(filePath: String): ByteArray = memScoped {
        val url = NSURL.fileURLWithPath(filePath)
        val asset = AVURLAsset(url, options = null)
        val generator = AVAssetImageGenerator(asset = asset)
        generator.appliesPreferredTrackTransform = true

        val time = CMTimeMake(value = 0, timescale = 1)
        val errorVar = alloc<kotlinx.cinterop.ObjCObjectVar<NSError?>>()
        val cgImage = generator.copyCGImageAtTime(
            requestedTime = time,
            actualTime = null,
            error = errorVar.ptr,
        ) ?: error("Failed to extract video frame: ${errorVar.value?.localizedDescription}")

        val uiImage = UIImage(cGImage = cgImage)
        val pngData = UIImagePNGRepresentation(uiImage)
            ?: error("Failed to create PNG representation")

        ByteArray(pngData.length.toInt()).apply {
            usePinned { pinned ->
                memcpy(pinned.addressOf(0), pngData.bytes, pngData.length)
            }
        }
    }

    class Factory : Decoder.Factory {
        override fun create(
            result: SourceFetchResult,
            options: Options,
            imageLoader: ImageLoader,
        ): Decoder? {
            if (!isVideoSource(result)) return null
            return IosVideoFrameDecoder(result)
        }
    }
}
