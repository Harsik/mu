package mu.server.service;

import mu.server.domain.entity.Account
import mu.server.domain.Role
import mu.server.domain.entity.UserAccount
import mu.server.domain.repository.AccountRepository
import mu.server.exception.NotFoundException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.function.Consumer
import kotlin.collections.ArrayList


@Service
class AccountService(private val accountRepository: AccountRepository,
                     private val passwordEncoder: PasswordEncoder) : UserDetailsService {
    @Transactional
    fun setAccountLastLogin(username: String) {
        val account: Account = getAccount(username)
        account.updateDt = LocalDateTime.now()
        accountRepository.save(account)
    }

    @Transactional
    fun getAccount(username: String): Account {
        return accountRepository.findByUsername(username) ?: throw NotFoundException("can not found account by username")
    }

    @Transactional
    fun saveAccount(username: String, password: String, name: String?, phone: String?): Account {
        var account = Account(null, username, this.passwordEncoder.encode(password),name, phone,mutableListOf(Role.USER), LocalDateTime.now(),null)
        return accountRepository.save(account)
    }

//    @Transactional
//    fun saveAccount(username: String, password: String): Account {
//        var account: Account = Account(null, username, this.passwordEncoder.encode(password), mutableListOf(Role.USER), LocalDateTime.now(),null)
//        return accountRepository.save(account)
//    }

    private fun getUserAuthority(roles: MutableList<Role>): MutableList<GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = mutableListOf()
        roles.forEach(Consumer<Role> { role: Role -> authorities.add(SimpleGrantedAuthority(role.value)) })
        return ArrayList(authorities)
    }

    @Transactional
//    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val account: Account = accountRepository.findByUsername(username)?: throw UsernameNotFoundException("$username Can Not Found")
        val authorities: MutableList<GrantedAuthority> = mutableListOf()
        authorities.addAll(getUserAuthority(account.roles))
        return UserAccount(null, account.username, account.password, account.name, account.phone, authorities)
//        return User(account.username, account.password, authorities)
//        val user: User = userRepository.findByEmail(email)
//        return accountRepository.findByUsername(username)?.getAuthorities()
//                ?: throw UsernameNotFoundException("$username Can Not Found")
    }
}