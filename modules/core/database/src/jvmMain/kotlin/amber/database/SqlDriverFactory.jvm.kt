package amber.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class SqlDriverFactory {
    actual fun create(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:$name")
        schema.create(driver)
        return driver
    }
}
