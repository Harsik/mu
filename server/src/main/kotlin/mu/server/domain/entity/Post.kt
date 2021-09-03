package mu.server.domain.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "posts")
data class Post (

        @Id
        var id: ObjectId?,
        var subject: String,

        @DBRef
        var author: Account,

        @DBRef
        var checker : List<Account>?,

        var content: String,
        var category: String,
        var createDt: LocalDateTime?,
        var updateDt: LocalDateTime?

)