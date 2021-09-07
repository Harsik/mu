package mu.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClientApplication
//{
//	@Bean
//	fun init(fileEncoder: FileEncoder) = CommandLineRunner {
//		fileEncoder.encoder();
//	}
//}

//raspivid -o - -t 0 -w 800 -h 600 -fps 12  | cvlc -vvv stream:///dev/stdin --sout '#rtp{sdp=rtsp://:8080/}' :demux=h264
fun main(args: Array<String>) {
	runApplication<ClientApplication>(*args)
}
