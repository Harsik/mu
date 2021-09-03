package mu.server.controller

import mu.server.domain.entity.UserAccount
import mu.server.payload.PostReq
import mu.server.service.PostService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class PostController(private val postService: PostService) {

    @GetMapping("posts")
    fun getPosts(model: Model): String {
        return "redirect:/post/recent/1"
    }

    @PutMapping("post")
    fun createPost(postReq: PostReq, @AuthenticationPrincipal userAccount: UserAccount): String {
        postService.createPost(postReq, userAccount)
        return "redirect:/post/1"
    }

    @PostMapping("posts")
    fun savePost(postReq: PostReq): String {
        postService.savePost(postReq)
        return "redirect:/post/1"
    }

    @GetMapping("posts/{Id}")
    fun getPost(@PathVariable Id: String, model: Model, @AuthenticationPrincipal userAccount: UserAccount): String {
        model.addAttribute("post", postService.getPost(Id))
        return "post/view"
    }
}
