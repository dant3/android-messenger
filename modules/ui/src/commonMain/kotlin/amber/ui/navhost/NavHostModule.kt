package amber.ui.navhost

import amber.navigation.navGraphComponent
import amber.ui.logout.LogoutNavGraphComponent
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val NavHostModule = module {
    navGraphComponent(singleOf(::LogoutNavGraphComponent))
    single {
        NavHostBuilderInfrastructure(
            components = getAll(),
        )
    }
}