package mu.server.domain.repository;

import org.springframework.data.domain.Page
import mu.server.domain.entity.Post
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository: MongoRepository<Post, String>{
//    fun findById(id: String): Optional<Post>
    fun findPostById(id: String): Post?
    fun findAllBy(pageable: Pageable): Page<Post>?
    fun findAllByCategory(category:String, pageable: Pageable): Page<Post>?
}
