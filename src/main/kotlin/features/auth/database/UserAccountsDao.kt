package features.auth.database

import org.jetbrains.exposed.sql.Table

object UserAccountsDao : Table("UserAccounts") {

    val id = varchar("id", 256)
    override val primaryKey = PrimaryKey(id)
}