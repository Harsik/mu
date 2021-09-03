package mu.server.domain.entity

import mu.server.domain.Role
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "accounts")
data class Account(
        @Id
        var id: ObjectId?,
        var username: String,
        var password: String,
        var name: String?,
        var phone: String?,
        var roles: MutableList<Role>,
        var createDt: LocalDateTime?,
        var updateDt: LocalDateTime?
)