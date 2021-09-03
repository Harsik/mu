package mu.server.domain.repository;

import mu.server.domain.entity.Account
import org.springframework.data.mongodb.repository.MongoRepository

interface AccountRepository: MongoRepository<Account, String> {
    fun findByUsername(Username: String): Account?
}