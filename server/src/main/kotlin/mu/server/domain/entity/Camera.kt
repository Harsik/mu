package mu.server.domain.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "cameras")
data class Camera (
    @Id
    var id: ObjectId?,
    var name: String,
    var ip: String,
    var port: String,
    var username: String,
    var password: String,
    var createDt: LocalDateTime?,
    var updateDt: LocalDateTime?
)