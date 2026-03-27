package amber.imageloading

import coil3.PlatformContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val CoreImageLoaderModule: Module = module {
    single {
        createImageLoader(PlatformContext.INSTANCE) {
            add(IosVideoFrameDecoder.Factory())
        }
    }
}
