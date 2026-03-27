package amber.navigation

import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.bind

inline fun <reified T : NavGraphComponent> Module.navGraphComponent(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>,
): KoinDefinition<T> = navGraphComponent(single(qualifier = qualifier, definition = definition))

fun <T : NavGraphComponent> navGraphComponent(beanDefinition: KoinDefinition<T>): KoinDefinition<T> =
    beanDefinition.also { it bind NavGraphComponent::class }
