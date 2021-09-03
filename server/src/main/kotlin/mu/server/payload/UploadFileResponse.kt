package mu.server.payload

class UploadFileResponse (
    var fileName: String?,
    var fileDownloadUri: String?,
    var fileType: String?,
    var size: Long = 0
)