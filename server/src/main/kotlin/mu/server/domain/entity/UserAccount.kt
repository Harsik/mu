package mu.server.domain.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User


data class UserAccount(
        val serialVersionUID : Long? = 1L, // 자바 직렬화 문제가 발생하여 시리얼 아이디를 직접 관리함
        var userName: String,
        var passWord: String,
        var name: String?,
        var phone: String?,
        var authority: Collection<GrantedAuthority?>?) : User(userName, passWord, authority) {
}