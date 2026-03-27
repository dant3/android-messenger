package amber.ui.uikit.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "en", showBackground = true, locale = "en")
@Preview(name = "ru-night", showBackground = true, locale = "ru", uiMode = 0x20)
@Preview(name = "ru-largeFont", showBackground = true, locale = "ru", fontScale = 1.5f)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION,
)
annotation class UiPreview
