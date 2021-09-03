package mu.server.domain.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "commnets")
data class Comment (

        @Id
        var id: ObjectId?,

        @DBRef
        var author: Account,

        @DBRef
        var post : Post,
        var content: String,

        var createDt: LocalDateTime?,
        var updateDt: LocalDateTime?
)