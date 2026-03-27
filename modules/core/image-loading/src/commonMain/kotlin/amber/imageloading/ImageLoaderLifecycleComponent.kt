package amber.imageloading

import amber.arch.lifecycle.LifecycleComponent
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.annotation.DelicateCoilApi

class ImageLoaderLifecycleComponent(
    private val imageLoader: ImageLoader,
): LifecycleComponent {
    @OptIn(DelicateCoilApi::class)
    override fun init() {
        SingletonImageLoader.setUnsafe(imageLoader)
    }
}