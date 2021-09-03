package mu.server.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(val resourceName: String?, val fieldName: String?, val fieldValue: Any?) :
    RuntimeException(
        String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue)
    ) {

    companion object {
        private const val serialVersionUID = 1L
    }
}