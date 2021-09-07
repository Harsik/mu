package mu.server.jave

import com.coremedia.iso.boxes.Container
import com.googlecode.mp4parser.FileDataSourceImpl
import com.googlecode.mp4parser.authoring.Movie
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder
import com.googlecode.mp4parser.authoring.tracks.AACTrackImpl
import com.googlecode.mp4parser.authoring.tracks.h264.H264TrackImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream


@Component
class FileEncoder {
    @Value("\${file.path}")
    lateinit var filePath: String

    fun encoder() {
        val h264Track = H264TrackImpl(FileDataSourceImpl(filePath+"/server/h264.h264"))
        val aacTrack = AACTrackImpl(FileDataSourceImpl("audio.aac"))
        val movie = Movie()
        movie.addTrack(h264Track)
//        movie.addTrack(aacTrack)
        val mp4file: Container? = DefaultMp4Builder().build(movie)
        val fc = FileOutputStream(File(filePath+"/server/output.mp4")).channel
        mp4file?.writeContainer(fc)
        fc.close()

//        audio.setCodec("libmp3lame")
//        audio.setBitRate(128000)
//        audio.setChannels(2)
////        audio.setSamplingRate(44100)
//        val video = VideoAttributes()
//        video.setCodec("mp4")
//        video.setTag("mp4")
//        video.setBitRate(5000)
//        video.setFrameRate(30)
//        video.setSize(VideoSize(400, 300))
//        val attrs = EncodingAttributes()
//        attrs.setFormat("mp4")
//        attrs.setAudioAttributes(audio)
//        attrs.setVideoAttributes(video)
//        val encoder = Encoder()
//        encoder.encode(source, target, attrs)
    }
}