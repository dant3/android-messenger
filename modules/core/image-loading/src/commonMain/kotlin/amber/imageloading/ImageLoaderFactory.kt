package amber.imageloading

import coil3.ComponentRegistry
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.svg.SvgDecoder

fun createImageLoader(
    context: PlatformContext,
    platformComponents: (ComponentRegistry.Builder.() -> Unit)? = null,
): ImageLoader =
    ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
            platformComponents?.invoke(this)
        }
        .build()
