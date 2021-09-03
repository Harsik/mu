package mu.server.service

import mu.server.domain.entity.FileInfo
import mu.server.domain.repository.FileInfoRepository
import mu.server.exception.FileStorageException
import mu.server.exception.MyFileNotFoundException
import mu.server.exception.NotFoundException
import mu.server.property.FileStorageProperties
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.LocalDateTime


@Service
class FileStorageService (private val fileStorageProperties: FileStorageProperties,
                          private val fileInfoRepository: FileInfoRepository) {
    private val fileStorageLocation: Path = Paths.get(fileStorageProperties.uploadDir).toAbsolutePath().normalize()
    fun loadFiles(): List<FileInfo> {
        return fileInfoRepository.findAll()
    }

    fun deleteFileInfo(fileName: String) {
        val fileInfo: FileInfo? = fileInfoRepository.findByName(fileName)
            ?.orElseThrow { FileStorageException("FileInfo not found with name : $fileName") }
        if (fileInfo != null) {
            fileInfoRepository.delete(fileInfo)
        }
    }

    fun saveFileInfo(name: String, downloadUri: String, type: String, size: Long) {
        var fileInfo = FileInfo(null, name, downloadUri, type, size, LocalDateTime.now(), LocalDateTime.now())

        if (fileInfoRepository.existsByName(name) == true) {
            fileInfo = fileInfoRepository.findByName(name).orElseThrow { FileStorageException("FileInfo not found with name : $name") }
        }

        fileInfoRepository.save(fileInfo)
    }

    fun storeFile(file: MultipartFile): String {
        // Normalize file name
        val fileName = StringUtils.cleanPath(file.originalFilename?: throw NotFoundException("Upload Failed"))
        return try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw FileStorageException("Sorry! Filename contains invalid path sequence $fileName")
            }

            // Copy file to the target location (Replacing existing file with the same name)
            val targetLocation = fileStorageLocation.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            fileName
        } catch (ex: IOException) {
            throw FileStorageException("Could not store file $fileName. Please try again!", ex)
        }
    }

    fun loadFileAsResource(fileName: String): Resource {
        return try {
            val filePath = fileStorageLocation.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists()) {
                resource
            } else {
                throw MyFileNotFoundException("File not found $fileName")
            }
        } catch (ex: MalformedURLException) {
            throw MyFileNotFoundException("File not found $fileName", ex)
        }
    }

    init {
        try {
            Files.createDirectories(fileStorageLocation)
        } catch (ex: Exception) {
            throw FileStorageException(
                "Could not create the directory where the uploaded files will be stored.",
                ex
            )
        }
    }
}