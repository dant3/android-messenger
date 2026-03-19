import amber.messenger.gradle.conventions.configuration.disableAndroidUnitTestsOn
import amber.messenger.gradle.conventions.secrets.Secrets
import amber.messenger.gradle.conventions.signing.registerSigningKey
import amber.messenger.gradle.conventions.versioning.GitInfo
import amber.messenger.gradle.conventions.versioning.VersionNameSelector
import com.google.firebase.appdistribution.gradle.AppDistributionExtension
import ru.cian.rustore.publish.BuildFormat
import ru.cian.rustore.publish.MobileServicesType
import ru.cian.rustore.publish.PublishType
import ru.cian.rustore.publish.ReleaseNote
import ru.cian.rustore.publish.SeoTag
import ru.cian.huawei.publish.BuildFormat as HuaweiBuildFormat
import ru.cian.huawei.publish.DeployType as HuaweiDeployType
import ru.cian.huawei.publish.ReleaseNote as HuaweiReleaseNote
import ru.cian.huawei.publish.ReleaseNotesExtension as HuaweiReleaseNotesExtension

plugins {
    id("amber.messenger.android.application")
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebase.appdistribution)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.google.play.publisher)
    alias(libs.plugins.rustore.publisher)
    alias(libs.plugins.huawei.publisher)
}

val config: Config by lazy { Config() }

base {
    archivesName.set("amber-v${config.applicationVersion}")
}

play {
    config.secrets.googlePlayServiceAccountCredentialsFile.takeIf { it.isFile }
        .let(serviceAccountCredentials::set)

    releaseName.set(config.releaseName)
    defaultToAppBundles.set(true)
    // tracks are: internal -> alpha -> beta -> production
    track.set("internal") // we will promote it to production manually
}

val prepareGooglePlayReleaseNotes by tasks.registering {
    description = "Copies versioned release notes to internal.txt for Google Play publisher"
    doLast {
        listOf("en-US", "ru-RU").forEach { lang ->
            val source = config.releaseNotesFile(lang)
            val target = projectDir.resolve("src/main/play/release-notes/$lang/internal.txt")
            source.copyTo(target, overwrite = true)
            logger.lifecycle("Copied ${source.name} -> internal.txt for $lang")
        }
    }
}

tasks.configureEach {
    if (name == "publishReleaseBundle" || name == "publishListing") {
        dependsOn(prepareGooglePlayReleaseNotes)
    }
}

rustorePublish {
    instances.create("release") {
        credentialsPath = config.secrets.rustoreCredentialsFile.absolutePath
        buildFormat = BuildFormat.AAB
        requestTimeout = 300 // seconds
        mobileServicesType = MobileServicesType.UNKNOWN
        publishType = PublishType.INSTANTLY
        buildFile = null // means default one
        seoTags = listOf( // See https://www.rustore.ru/help/work-with-rustore-api/api-upload-publication-app/app-tag-list
            SeoTag.MESSAGING,
            SeoTag.COMMUNICATION,
            SeoTag.PERSONAL_ASSISTANTS,
        )
        releaseNotes = listOf(
            ReleaseNote(
                lang = "ru-RU",
                filePath = config.releaseNotesFile("ru-RU").path,
            ),
        )
    }
}

huaweiPublish {
    instances.create("release") {
        credentialsPath = config.secrets.huaweiCredentialsFile.absolutePath
        buildFormat = HuaweiBuildFormat.AAB
        deployType = HuaweiDeployType.PUBLISH
        buildFile = null // means default one
        publishSocketTimeoutInSeconds = 300
        releaseNotes = HuaweiReleaseNotesExtension(
            descriptions = listOf(
                HuaweiReleaseNote(
                    lang = "en-US",
                    filePath = config.releaseNotesFile("en-US").path,
                ),
            ),
        )
    }
}

android {
    namespace = "amber.messenger"

    buildFeatures {
        buildConfig = true
        resValues = true
    }

    defaultConfig {
        applicationId = "amber.messenger.android"
        versionCode = config.buildNumber
        versionName = config.applicationVersion

        resValue("string", "app_name", config.applicationName)
        resValue("string", "app_version", config.applicationVersion)

        buildConfigField("String", "BRANCH", "\"${config.gitInfo.branch}\"")
        buildConfigField("String", "COMMIT", "\"${config.gitInfo.commit}\"")
    }

    signingConfigs {
        registerSigningKey(
            name = "debug",
            storeFile = file("keys/debug.jks"),
            propertiesFile = file("keys/debug.properties"),
        )
        registerSigningKey(
            name = "release",
            storeFile = file("keys/upload.jks"),
            propertiesFile = file("keys/upload.properties"),
        )
    }
    buildTypes {
        named("debug") {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
            isPseudoLocalesEnabled = true
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            resValue("string", "app_name", config.applicationName + " α")
            buildConfigField("boolean", "DEVELOPMENT_ENVIRONMENT", "true")
            manifestPlaceholders["allowBackupValue"] = "false"
            manifestPlaceholders["profileable"] = "true"
        }
        register("beta") {
            applicationIdSuffix = ".beta"
            signingConfig = signingConfigs.getByName("debug")
            isPseudoLocalesEnabled = true
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            matchingFallbacks.add("release")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            resValue("string", "app_name", config.applicationName + " β")
            buildConfigField("boolean", "DEVELOPMENT_ENVIRONMENT", "true")
            manifestPlaceholders["allowBackupValue"] = "false"
            manifestPlaceholders["profileable"] = "true"

            configure<AppDistributionExtension> {
                artifactType = "APK"
                releaseNotes = "Beta ${config.applicationVersion} from branch ${config.gitInfo.branch}"
                groups = "android-testers"
            }
        }
        named("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            resValue("string", "app_name", config.applicationName)
            buildConfigField("boolean", "DEVELOPMENT_ENVIRONMENT", "false")
            manifestPlaceholders["allowBackupValue"] = "true"
            manifestPlaceholders["profileable"] = "false"

            configure<AppDistributionExtension> {
                artifactType = "APK"
                releaseNotes = "Release ${config.applicationVersion}"
                groups = "android-testers"
            }
        }
    }

    playConfigs {
        register("beta") {
            enabled.set(false)
        }
    }

    disableAndroidUnitTestsOn("beta")
    disableAndroidUnitTestsOn("release")
}

afterEvaluate {
    tasks.named("appDistributionUploadBeta").configure { dependsOn("processBetaGoogleServices") }
    tasks.named("appDistributionUploadRelease").configure { dependsOn("processReleaseGoogleServices") }
}

configurations {
    all {
        resolutionStrategy {
            exclude(group = "org.hamcrest", "hamcrest-core")
            exclude(group = "org.hamcrest", "hamcrest-library")
            exclude(group = "ch.qos.logback", module = "logback-classic")

            // Force concurrent-futures to 1.2.0 to resolve test dependency conflict
            force("androidx.concurrent:concurrent-futures:1.2.0")
            force("androidx.concurrent:concurrent-futures-ktx:1.2.0")
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(projects.modules.ui.uikit)
}

class Config {
    val secrets: Secrets = Secrets.loadFromEnv()

    private val env = System.getenv()

    val gitInfo: GitInfo = GitInfo.load(project)

    val buildNumber: Int = env["BUILD_NUMBER"]?.toInt() ?: 1
    val prevCommit: String = env["GIT_PREVIOUS_COMMIT"] ?: ""

    val releaseName: String = VersionNameSelector.getReleaseName(project, gitInfo)
    val applicationVersion: String = "$releaseName-$buildNumber"

    val applicationName: String = "Amber Messenger"

    fun releaseNotesFile(lang: String): File {
        val directory = projectDir.resolve("src/main/play/release-notes/$lang/")
        val specificReleaseFile = directory.resolve("$releaseName.txt")
        return when {
            specificReleaseFile.isFile -> specificReleaseFile
            else -> directory.resolve("default.txt")
        }
    }

    init {
        println("$applicationName version: $applicationVersion")
    }
}
