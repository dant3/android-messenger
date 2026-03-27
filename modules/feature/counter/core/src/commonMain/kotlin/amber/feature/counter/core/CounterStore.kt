package amber.feature.counter.core

import amber.database.CounterQueries
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class CounterStore(private val queries: CounterQueries) {
    init {
        queries.initializeIfEmpty()
    }

    val count: Flow<Long> = queries.getValue().asFlow().mapToOne(Dispatchers.Default)

    fun increment() {
        queries.increment()
    }

    fun decrement() {
        queries.decrement()
    }

    fun reset() {
        queries.reset()
    }
}
