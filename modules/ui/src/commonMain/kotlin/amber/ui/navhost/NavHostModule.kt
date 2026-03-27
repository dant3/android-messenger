package amber.ui.navhost

import org.koin.dsl.module

val NavHostModule = module {
    single {
        NavHostBuilderInfrastructure(
            components = getAll(),
        )
    }
}