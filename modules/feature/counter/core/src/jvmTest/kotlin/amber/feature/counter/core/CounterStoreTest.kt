package amber.feature.counter.core

import amber.feature.counter.core.db.CounterDatabase
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first

class CounterStoreTest : FunSpec() {
    init {
        test("initial count is 0") {
            val store = createStore()
            store.count.first() shouldBe 0L
        }

        test("increment increases count by 1") {
            val store = createStore()
            store.increment()
            store.count.first() shouldBe 1L
        }

        test("decrement decreases count by 1") {
            val store = createStore()
            store.decrement()
            store.count.first() shouldBe -1L
        }

        test("multiple increments accumulate") {
            val store = createStore()
            repeat(5) { store.increment() }
            store.count.first() shouldBe 5L
        }

        test("reset sets count back to 0") {
            val store = createStore()
            store.increment()
            store.increment()
            store.increment()
            store.reset()
            store.count.first() shouldBe 0L
        }

        test("increment and decrement combined") {
            val store = createStore()
            store.increment()
            store.increment()
            store.decrement()
            store.count.first() shouldBe 1L
        }
    }

    private fun createStore(): CounterStore {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        CounterDatabase.Schema.create(driver)
        val database = CounterDatabase(driver)
        return CounterStore(database.counterQueries)
    }
}
