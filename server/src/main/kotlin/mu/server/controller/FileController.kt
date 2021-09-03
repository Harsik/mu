package mu.server.controller

import mu.server.domain.entity.FileInfo
import mu.server.exception.NotFoundException
import mu.server.payload.UploadFileResponse
import mu.server.service.AccountService
import mu.server.service.FileStorageService
import mu.server.service.PostService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import javax.servlet.http.HttpServletRequest

//@CrossOrigin
@RestController
@RequestMapping("/api/file")
class FileController(
    val postService: PostService,
    val fileStorageService: FileStorageService,
    val accountService: AccountService
) {
//        @GetMapping("/loadAvatar")
//        @PreAuthorize("hasRole('USER')")
//        fun loadAvatar(@RequestParam(value = "email") email: String?): AvatarFileInfo? {
//            return accountService.loadAvatarByEmail(email)
//        }

//        @PostMapping("/uploadAvatar")
//        fun uploadAvatar(
//            @RequestParam("file") file: MultipartFile,
//            @RequestParam("email") email: String?
//        ): UploadFileResponse? {
//            val fileName: String = fileStorageService.storeFile(file)
//            val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/downloadFile/")
//                .path(fileName).toUriString()
//            accountService.saveAvatar(email, fileName, fileDownloadUri, file.contentType, file.size)
//            return UploadFileResponse(fileName, fileDownloadUri, file.contentType, file.size)
//        }

    @PostMapping("/loadFiles")
    fun loadFiles(): List<FileInfo?>? {
        return fileStorageService.loadFiles()
    }

    // @GetMapping("/deleteFile")
    // public UploadFileResponse deleteFile(@RequestParam(value = "fileName") String fileName) {
    //     fileStorageService.deleteFileInfo(fileName);
    //     return new UploadFileResponse(fileName, "", "", '0');
    // }

    // @GetMapping("/deleteFile")
    // public UploadFileResponse deleteFile(@RequestParam(value = "fileName") String fileName) {
    //     fileStorageService.deleteFileInfo(fileName);
    //     return new UploadFileResponse(fileName, "", "", '0');
    // }netsh interface portproxy delete v4tov4 listenport=6100 listenaddress=192.168.48.134
//    netsh interface portproxy add v4tov4 listenport=6100 listenaddress=192.168.48.134 connectport=6100 connectaddress=192.168.48.10
    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam("file") file: MultipartFile): UploadFileResponse? {
        val fileName: String = fileStorageService.storeFile(file)
        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/downloadFile/")
            .path(fileName).toUriString()
        fileStorageService.saveFileInfo(
            fileName,
            fileDownloadUri,
            file.contentType ?: throw NotFoundException("Missing contentType in $fileName"),
            file.size
        )
        return UploadFileResponse(fileName, fileDownloadUri, file.contentType, file.size)
    }

//    @PostMapping("/uploadMultipleFiles")
//    fun uploadMultipleFiles(@RequestParam("files") files: Array<MultipartFile?>): List<UploadFileResponse?>? {
//        return Arrays.asList<MultipartFile>(*files).stream().map<Any?> { file: MultipartFile ->
//            uploadFile(
//                file
//            )
//        }.collect(Collectors.toList())
//    }

    @GetMapping("/downloadFile/{fileName:.+}")
    fun downloadFile(@PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource?>? {
        // // Load file as Resource
        val resource: Resource = fileStorageService.loadFileAsResource(fileName)

        // Try to determine file's content type
        var contentType: String? = null
        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)
        } catch (ex: IOException) {
//            logger.info("Could not determine file type.")
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream"
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename + "\"")
            .body(resource)
    }
}