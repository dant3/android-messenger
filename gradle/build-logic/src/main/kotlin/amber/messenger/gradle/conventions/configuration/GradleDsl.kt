package amber.messenger.gradle.conventions.configuration

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.HasUnitTestBuilder
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

typealias CommonAndroidExtension = CommonExtension

internal fun Project.androidCommonConfiguration(action: CommonAndroidExtension.() -> Unit) {
    project.plugins.withType(AppPlugin::class.java) {
        project.extensions.configure(ApplicationExtension::class.java) {
            action(this)
        }
    }
    project.plugins.withType(LibraryPlugin::class.java) {
        project.extensions.configure(LibraryExtension::class.java) {
            action(this)
        }
    }
}

fun Project.disableAndroidUnitTestsOn(buildType: String) {
    androidComponents {
        beforeVariants(selector().withBuildType(buildType)) { variantBuilder ->
            (variantBuilder as? HasUnitTestBuilder)?.apply {
                enableUnitTest = false
            }
        }
    }
}

internal fun Project.androidComponents(action: AndroidComponentsExtension<*, *, *>.() -> Unit) {
    extensions.configure(AndroidComponentsExtension::class.java, action)
}

internal fun Project.androidApplicationComponents(action: ApplicationAndroidComponentsExtension.() -> Unit) {
    extensions.configure(ApplicationAndroidComponentsExtension::class.java, action)
}

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.composeCompilerPluginId: String
    get() = libs.findPlugin("kotlin.compose.compiler").get().get().pluginId
