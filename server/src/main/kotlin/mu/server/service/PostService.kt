package mu.server.service;

import mu.server.domain.entity.Post
import mu.server.domain.entity.UserAccount
import mu.server.domain.repository.AccountRepository
import mu.server.exception.NotFoundException
import mu.server.domain.repository.PostRepository
import mu.server.payload.PostReq
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PostService(private val postRepository: PostRepository, private val accountRepository: AccountRepository) {

    @Transactional
    fun createPost(postReq: PostReq, userAccount: UserAccount) {
        postRepository.save(Post(
                null,
                postReq.subject,
                accountRepository.findByUsername(userAccount.username)
                        ?: throw NotFoundException("can not found account by username"),
                null,
                postReq.content,
                postReq.category,
                LocalDateTime.now(),
                LocalDateTime.now()
        ))
    }

    @Transactional
    fun savePost(postReq: PostReq) {
        val post : Post = postRepository.findPostById(postReq.id.toString())?: throw NotFoundException("can not found account by username")
        post.subject = postReq.subject
        post.author = accountRepository.findByUsername(postReq.author.username)?: throw NotFoundException("can not found account by username")
        post.content = postReq.content
        post.updateDt = LocalDateTime.now()
        postRepository.save(post)
    }

    @Transactional
    fun getPosts(page: Int): Page<Post>? {
        return postRepository.findAllBy(PageRequest.of(page - 1, 5))
    }

    @Transactional
    fun getRecentPosts(page: Int): Page<Post>? {
        return postRepository.findAllByCategory("recent", PageRequest.of(page - 1, 5))
    }

    @Transactional
    fun getPosts(category: String, page: Int): Page<Post>? {
        return postRepository.findAllByCategory(category, PageRequest.of(page - 1, 5))
    }

    @Transactional
    fun getPost(id: String): Post? {
        return postRepository.findById(id).orElseThrow { NotFoundException("can not found post by id") }
    }

}
