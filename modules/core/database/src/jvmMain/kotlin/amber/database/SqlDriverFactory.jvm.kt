package amber.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.exists

actual class SqlDriverFactory {
    actual fun create(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver {
        val databasePath = Path(name)
        val driver = JdbcSqliteDriver("jdbc:sqlite:$name")
        if (!databasePath.exists()) {
            schema.create(driver)
        }
        return driver
    }
}
