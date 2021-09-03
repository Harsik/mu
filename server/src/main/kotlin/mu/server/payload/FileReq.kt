package mu.server.payload

import java.time.LocalDateTime

class FileReq(
        var id: String?,
        var name: String,
        var path: String,
        var updateDt: LocalDateTime = LocalDateTime.now()
)