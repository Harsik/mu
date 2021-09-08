package mu.server.property

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
//@ConfigurationProperties(prefix = "file")
class FileStorageProperties {
    @Value("\${file.upload-dir}")
    lateinit var uploadDir: String
    @Value("\${file.path}")
    lateinit var filePath: String
}