package mu.server.domain.repository;

import mu.server.domain.entity.FileInfo
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface FileInfoRepository: MongoRepository<FileInfo, String>{
    fun findByName(name: String?): Optional<FileInfo>
    fun existsByName(name: String?): Boolean?
}
