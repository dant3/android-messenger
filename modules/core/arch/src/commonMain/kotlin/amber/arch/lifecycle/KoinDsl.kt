package amber.arch.lifecycle

import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.bind

inline fun <reified T : LifecycleComponent> Module.lifecycleComponent(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>,
): KoinDefinition<T> = lifecycleComponent(single(qualifier = qualifier, definition = definition))

fun <T : LifecycleComponent> lifecycleComponent(beanDefinition: KoinDefinition<T>): KoinDefinition<T> =
    beanDefinition.also { it bind LifecycleComponent::class }