package amber.imageloading

import coil3.fetch.SourceFetchResult

internal fun isVideoSource(result: SourceFetchResult): Boolean {
    val mimeType = result.mimeType
    if (mimeType != null) return mimeType.startsWith("video/")
    return false
}
