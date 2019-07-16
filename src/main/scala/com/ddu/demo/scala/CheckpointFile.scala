package com.ddu.demo.scala

import java.io._
import java.nio.charset.StandardCharsets
import java.nio.file.{FileAlreadyExistsException, Files, Paths}

import scala.collection.mutable

/**
  * rewrite of kafka source code
  * https://github.com/apache/kafka/blob/6dd4ebcea78f9c6fb8a2921cab3fd92be4978063/core/src/main/scala/kafka/server/checkpoints/CheckpointFile.scala
  */
class LogDirFailureChannel {
  def maybeAddOfflineLogDir(logDir: String, msg: String, e: Exception) =
    println(logDir + msg)
}

trait CheckPointFileFormatter[T] {
  def toLine(t: T) = t.toString
  def fromLine(line: String): T = new T()
}

trait Logging {

}

class KafkaStorageExeption(msg: String, e: Exception) extends Exception {

}

class CheckpointFile[T](val file: File,
                        version: Int,
                        formatter: CheckPointFileFormatter[T],
                        logDirFailureChannel: LogDirFailureChannel,
                        logDir: String) extends Logging {
  private val path = file.toPath.toAbsolutePath
  private val tempPath = Paths.get(path.toString + ".tmp")
  private val lock = new Object()

  try Files.createFile(file.toPath)
  catch { case _: FileAlreadyExistsException => } // NOTE...

  def write(entries: Iterable[T]): Unit = {
    lock synchronized {
      try {
        val fileOutputStream = new FileOutputStream(tempPath.toFile)
        val writter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8))
        try {
          writter.write(version)
          writter.newLine()

          writter.write(entries.size.toString)
          writter.newLine()

          entries.foreach { entry =>
            writter.write(formatter.toLine(entry))
            writter.newLine()
          }

          writter.flush()
          fileOutputStream.getFD.sync()
        } finally {
          writter.close()
        }
      } catch {
        case e: IOException =>
          val msg = s"Error while writting to checkpoint file ${file.getAbsolutePath}"
          logDirFailureChannel.maybeAddOfflineLogDir(logDir, msg, e)
          throw new KafkaStorageExeption(msg, e)
      }
    }
  }

  def read(): Seq[T] = {
    def malformedLineException(line: String) =
      new IOException(s"Malformed line in checkpoint file (${file.getAbsolutePath}): $line")
    lock synchronized {
      try {
        val reader = Files.newBufferedReader(path)
        var line: String = null
        try {
          line = reader.readLine()
          if (line == null)
            Seq.empty
          line.toInt match {
            case fileVersion if fileVersion == version =>
              line = reader.readLine()
              if (line == null)
                Seq.empty
              val expectedSize = line.toInt
              val entries = mutable.Buffer[T]()
              line = reader.readLine()
              while (line != null) {
                val entry = formatter.fromLine(line)
                entry match {
                  case Some(e) =>
                    entries += e
                    line = reader.readLine()
                  case _ => throw malformedLineException(line)
                }
              }
              if (entries.size != expectedSize)
                throw new IOException(s"Expected $expectedSize entries in checkpoint file (${file.getAbsolutePath}), " +
                  s"but found only ${entries.size}")
              entries
            case _ =>
              throw new IOException(s"Unrecognized version of the checkpoint file (${file.getAbsolutePath}): " + version)
          }
        } catch {
          case _: NumberFormatException => throw malformedLineException(line)
        }
      } catch {
        case e: IOException =>
          val msg = s"Error while reading checkpoint file ${file.getAbsolutePath}"
          logDirFailureChannel.maybeAddOfflineLogDir(logDir, msg, e)
          throw new KafkaStorageExeption(msg, e)
      }
    }
  }

}
