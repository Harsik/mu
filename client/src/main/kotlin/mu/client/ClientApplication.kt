package mu.client

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.io.File


@SpringBootApplication
class ClientApplication
{
	private val log = LoggerFactory.getLogger(ClientApplication::class.java)
//	@Value("\${spring.apm-web.url}")
//	val serverUrl: String? = null

	@Value("\${file.path}")
	lateinit var filePath: String
	@Bean
	fun init() = CommandLineRunner {
		val serverUrl = "http://localhost:8080/api/file/uploadVideo"
		val cameraName : String = "camera1"
//		val file = FileSystemResource(filePath+"/h264.h264")
		val restTemplate = RestTemplate()
		val headers = HttpHeaders()
//		headers.contentType = MediaType.APPLICATION_JSON
		headers.contentType = MediaType.MULTIPART_FORM_DATA
		val body: LinkedMultiValueMap<String, Any> = LinkedMultiValueMap()
			body.add("file", FileSystemResource(filePath+"/h264.h264"))
		body.add("cameraName", cameraName)
		val requestEntity = HttpEntity<LinkedMultiValueMap<String, Any>>(
			body, headers
		)
//		val requestEntity: HttpEntity<LinkedMultiValueMap<String, Any>?> = HttpEntity<LinkedMultiValueMap<String, Any>?>(body, headers)
		val response = restTemplate.postForEntity(serverUrl, requestEntity, String::class.java)
	}
}

private fun <K, V> LinkedMultiValueMap<K, V>.add(k: K, file: File) {

}

//raspivid -o - -t 0 -w 800 -h 600 -fps 12  | cvlc -vvv stream:///dev/stdin --sout '#rtp{sdp=rtsp://:8080/}' :demux=h264
fun main(args: Array<String>) {
	runApplication<ClientApplication>(*args)
}
