package mu.server.controller

import mu.server.payload.SignupReq
import mu.server.service.AccountService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("user")
class AccountController(val accountService: AccountService) {

    @GetMapping("success")
    fun success(): String {
        return "success"
    }

    @GetMapping("login")
    fun login(): String {
        return "login"
    }

    @PostMapping("login")
    fun loginPagePost(): String? {
        return "login"
    }

    @GetMapping("signup")
    fun signup(): String {
        return "signup"
    }

    @PostMapping("signup")
    fun signup(signupReq: SignupReq): String {
        accountService.saveAccount(signupReq.username, signupReq.password, null, null)
        return "login"
    }
}