package mu.server


class SftpFileTransferLiveTest {
//	private val remoteHost = "DESKTOP-F932930"
//	private val username = "aznm12@archivsoft.com"
//	private val password = "archivsoft@#"
//	private val localFile = "src/main/resources/input.txt"
//	private val localDir = "src/main/resources/"
//	private val remoteDir = "D:\\"
//	private val remoteFile = "D:\\jschFile.txt"
////	private val knownHostsFileLoc = "C:\\Users\\Archivsoft\\.ssh\\known_hosts"
//	private val knownHostsFileLoc = "D:\\IdeaProject\\Akamware\\known_hosts"
//
//	@Throws(JSchException::class)
//	private fun setupJsch(): ChannelSftp? {
//		val jsch = JSch()
//		jsch.setKnownHosts(knownHostsFileLoc)
//		val jschSession: Session = jsch.getSession(username, remoteHost)
//		jschSession.setPassword(password)
////		jschSession.setConfig("StrictHostKeyChecking", "no");
//		jschSession.connect()
//		return jschSession.openChannel("sftp") as ChannelSftp?
//	}
//
//	@Test
//	@Throws(JSchException::class, SftpException::class)
//	fun whenUploadFileUsingJsch_thenSuccess() {
//		val channelSftp = setupJsch()
//		channelSftp?.connect()
//		channelSftp?.put(localFile, remoteDir + "jschFile1.txt")
//		channelSftp?.exit()
//	}
//
//	@Test
//	@Throws(JSchException::class, SftpException::class)
//	fun whenDownloadFileUsingJsch_thenSuccess() {
//		val channelSftp = setupJsch()
//		channelSftp!!.connect()
//		channelSftp.get(remoteFile, localDir + "jschFile2.txt")
//		channelSftp.exit()
//	}

}