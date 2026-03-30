package amber.imageloading

import amber.arch.lifecycle.lifecycleComponent
import android.content.Context
import coil3.gif.AnimatedImageDecoder
import coil3.video.VideoFrameDecoder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val CoreImageLoaderModule: Module = module {
    single {
        createImageLoader(get<Context>()) {
            add(AnimatedImageDecoder.Factory())
            add(VideoFrameDecoder.Factory())
        }
    }
    lifecycleComponent { ImageLoaderLifecycleComponent(get()) }
}
