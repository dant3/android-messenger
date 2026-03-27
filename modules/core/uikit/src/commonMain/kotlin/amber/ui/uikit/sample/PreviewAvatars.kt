package amber.ui.uikit.sample

import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.sample_avatar_01
import amber.modules.core.uikit.generated.resources.sample_avatar_02
import amber.modules.core.uikit.generated.resources.sample_avatar_03
import amber.modules.core.uikit.generated.resources.sample_avatar_04
import amber.modules.core.uikit.generated.resources.sample_avatar_05
import amber.modules.core.uikit.generated.resources.sample_avatar_06
import amber.modules.core.uikit.generated.resources.sample_avatar_07
import amber.modules.core.uikit.generated.resources.sample_avatar_08
import amber.modules.core.uikit.generated.resources.sample_avatar_09
import amber.modules.core.uikit.generated.resources.sample_avatar_10
import amber.modules.core.uikit.generated.resources.sample_avatar_11
import amber.modules.core.uikit.generated.resources.sample_avatar_12
import org.jetbrains.compose.resources.DrawableResource
import kotlin.math.abs
import kotlin.random.Random

object PreviewAvatars {
    val all: List<DrawableResource> = listOf(
        Res.drawable.sample_avatar_01,
        Res.drawable.sample_avatar_02,
        Res.drawable.sample_avatar_03,
        Res.drawable.sample_avatar_04,
        Res.drawable.sample_avatar_05,
        Res.drawable.sample_avatar_06,
        Res.drawable.sample_avatar_07,
        Res.drawable.sample_avatar_08,
        Res.drawable.sample_avatar_09,
        Res.drawable.sample_avatar_10,
        Res.drawable.sample_avatar_11,
        Res.drawable.sample_avatar_12,
    )

    fun byIndex(index: Int): DrawableResource {
        return all[abs(index) % all.size]
    }

    fun random(): DrawableResource = all.random()

    fun maybeRandom(): DrawableResource? {
        val chance = Random.nextInt(1, 4)
        return when {
            chance > 1 -> random()
            else -> null
        }
    }
}