package mu.server.payload

import mu.server.domain.entity.Account
import java.time.LocalDateTime

class PostReq(
        var id: String?,
        var subject: String,
        var content: String,
        var author: Account,
        var category: String,
        var updateDt: LocalDateTime = LocalDateTime.now()
)