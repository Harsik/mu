package mu.server.domain.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "files")
data class FileInfo(
    @Id
    var id: ObjectId?,
    var name: String,
    var downloadUri: String,
    var type: String,
    var size: Long,
    var createDt: LocalDateTime?,
    var updateDt: LocalDateTime?
)