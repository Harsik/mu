package mu.server.controller

import mu.server.domain.entity.UserAccount
import mu.server.service.PostService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController(private val postService: PostService) {

    @GetMapping("")
    fun getPosts(@AuthenticationPrincipal userAccount: UserAccount?, model: Model): String {
//        model.addAttribute("posts", postService.getPosts(1))
        model.addAttribute("userAccount", userAccount)
        return "index"
    }

    @GetMapping("denied")
    fun getDeny(@AuthenticationPrincipal userAccount: UserAccount?, model: Model): String {
        return "denied"
    }

//    @GetMapping("/{page}")
//    fun getPosts(@PathVariable page: Int, model: Model): String {
//        model.addAttribute("posts", postservice.getPosts(page))
//        return "index"
//    }
}