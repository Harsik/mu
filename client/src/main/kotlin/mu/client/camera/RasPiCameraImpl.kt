package mu.client.camera

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.*
import java.lang.Exception

private val log = LoggerFactory.getLogger(RasPiCameraImpl::class.java)

@Component
class RasPiCameraImpl : Camera {
    override fun takeVideo(): ByteArray {
        log.debug("Starting taking video")
        val processBuilder = ProcessBuilder(
            RAS_PI_VID,
            VIDEO_LENGTH, "1800000",
            VIDEO_WIDTH, "640",
            VIDEO_HEIGHT, "480",
            VIDEO_FPS, "30",
            BITRATE, "1200000",
            PREVIEW, "0,0,640,480",
            OUTPUT_FILENAME, "pivideo.h264"
        )
        val video: ByteArray
        try {
            val process = processBuilder.start()
            log.debug("Started Process, wait for ending")
            val bsr = ByteStreamReader(process.inputStream)
            bsr.start()
            val exitValue = process.waitFor()
            if (exitValue != 0) {
                log.error(getCameraErrors(process.errorStream))
                throw CameraException("Error Occurred")
            }
            bsr.join()
            video = bsr.bytes
            process.destroy()
        } catch (ex: IOException) {
            log.error("IO Failure: " + ex.message, ex)
            throw CameraException(ex.message, ex)
        } catch (ex: InterruptedException) {
            log.error("Interrupted Failure: " + ex.message, ex)
            throw CameraException(ex.message, ex)
        } catch (ex: Exception) {
            log.error("General Failure: " + ex.message, ex)
            throw CameraException(ex.message, ex)
        }
        return video
    }

    override fun takePicture(): ByteArray {
        log.debug("Starting taking picture")
        val processBuilder = ProcessBuilder(
            RAS_PI_STILL,
            DELAY_MS, "1",
            IMAGE_DESTINATION, IMAGE_DESTINATION_STD_OUT,
            IMAGE_ENCODING, "jpg",
            EXPOSURE_MODE, "auto",
            JPEG_QUALITY, "75"
        )
        val image: ByteArray
        try {
            val process = processBuilder.start()
            log.debug("Started Process, wait for ending")
            val bsr = ByteStreamReader(process.inputStream)
            bsr.start()
            val exitValue = process.waitFor()
            if (exitValue != 0) {
                log.error(getCameraErrors(process.errorStream))
                throw CameraException("Error Occurred")
            }
            bsr.join()
            image = bsr.bytes
            process.destroy()
        } catch (ex: IOException) {
            log.error("IO Failure: " + ex.message, ex)
            throw CameraException(ex.message, ex)
        } catch (ex: InterruptedException) {
            log.error("Interrupted Failure: " + ex.message, ex)
            throw CameraException(ex.message, ex)
        } catch (ex: Exception) {
            log.error("General Failure: " + ex.message, ex)
            throw CameraException(ex.message, ex)
        }
        return image
    }

    companion object {
        private const val RAS_PI_STILL = "/usr/bin/raspistill"
        private const val DELAY_MS = "-t"
        private const val IMAGE_DESTINATION = "-o"
        private const val IMAGE_DESTINATION_STD_OUT = "-"
        private const val IMAGE_ENCODING = "-e"
        private const val EXPOSURE_MODE = "-ex"
        private const val JPEG_QUALITY = "-q"

        private const val RAS_PI_VID = "/usr/bin/raspivid"
        private const val VIDEO_LENGTH = "-t"
        private const val VIDEO_WIDTH = "-w"
        private const val VIDEO_HEIGHT = "-h"
        private const val VIDEO_FPS = "-f"
        private const val BITRATE = "-b"
        private const val PREVIEW = "-p"
        private const val OUTPUT_FILENAME = "-o"



        private fun getCameraErrors(errStream: InputStream): String {
            val sb = StringBuilder()
            var br: BufferedReader? = null
            try {
                br = BufferedReader(InputStreamReader(errStream))
                var line: String? = null
                while (br.readLine().also { line = it } != null) {
                    sb.append(line + System.getProperty("line.separator"))
                }
            } catch (e: IOException) {
//                log.error(e.message)
            } finally {
                try {
                    br?.close()
                } catch (ignore: Throwable) {
                }
            }
            return sb.toString()
        }
    }
}

internal class ByteStreamReader(private val `is`: InputStream) : Thread() {
    lateinit var bytes: ByteArray
        private set

    override fun run() {
        try {
            val bytes = ByteArray(1024)
            val bos = ByteArrayOutputStream()
            var len = 0
            log.debug("Starting reading...")
            while (`is`.read(bytes).also { len = it } > 0) {
                bos.write(bytes, 0, len)
            }
            log.debug("Completed reading")
            this.bytes = bos.toByteArray()
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }
}