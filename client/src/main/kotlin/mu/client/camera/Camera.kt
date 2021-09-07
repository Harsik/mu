package mu.client.camera

interface Camera {
    fun takePicture(): ByteArray
    fun takeVideo(): ByteArray?
}