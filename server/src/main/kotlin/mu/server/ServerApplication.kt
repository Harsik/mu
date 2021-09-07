package mu.server

import mu.server.jave.FileEncoder
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ServerApplication
{
	@Bean
	fun init(fileEncoder: FileEncoder) = CommandLineRunner {
		fileEncoder.encoder();
	}
}


fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}
