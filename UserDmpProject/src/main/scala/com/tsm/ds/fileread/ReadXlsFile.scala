package com.tsm.ds.fileread

import java.io.File
import com.tsm.xlstocsv._
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.openxml4j.opc.PackageAccess
import org.apache.tika.config.TikaConfig
import java.io.InputStream
import org.apache.tika.metadata.Metadata
import org.apache.tika.metadata.Metadata._
import org.apache.tika.metadata.TikaMetadataKeys
import org.apache.tika.io.TikaInputStream
import org.apache.tika.mime.MediaType
import org.apache.commons.io.FilenameUtils


object `package` { 
  def walkTree(file: File): Iterable[File] = {
    val children = new Iterable[File] {
      def iterator = if (file.isDirectory) file.listFiles.iterator else Iterator.empty
    }
    Seq(file) ++: children.flatMap(walkTree(_))
  }
  
  def filterExtensions(files: Iterable[File], extensions: List[String]): Iterable[File] = {
    files.filter { file => 
      extensions.exists(file.getName.endsWith(_))
    }
  }

  def getListOfFiles(d: File) : List[File] = {
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }
  
  def getListOfFiles(dir: File, extensions: List[String]): List[File] = {
    val filteredFileList = getListOfFiles(dir);
    filteredFileList.filter { file => 
      extensions.exists(file.getName.endsWith(_))
    }
  }
  
  def detectMime(file: File): (File, MediaType) = {
    val tika = new TikaConfig();
    val metadata = new Metadata();
    
    metadata.set(TikaMetadataKeys.RESOURCE_NAME_KEY, file.toString());
    val mime = tika.getDetector().detect(
        TikaInputStream.get(file), metadata);
    (file, mime)
  }

  def convertToCsv(fileAndMedia: (File, MediaType)) {

    println(s"${fileAndMedia._1} of type ${fileAndMedia._2}")
    if (fileAndMedia._2.toString.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
      if (fileAndMedia._1.getPath.endsWith("xlsx")) {
        // The package open is instantaneous, as it should be.
        val p = OPCPackage.open(fileAndMedia._1, PackageAccess.READ);
        val outFilename = FilenameUtils.removeExtension(fileAndMedia._1.getPath)
        
        val outFile = new File(outFilename.replace("Data Sources", "CSV"))
        outFile.mkdirs()
        
        val firstOutFile = new PerSheetOutputDispatcher(outFile.getPath);
        
        val xlsx2csv = new XlsxToCsv(p, firstOutFile, -1);
        xlsx2csv.process();
      
      } else if (fileAndMedia._1.getPath.endsWith("xls")) {
        val outFilename = FilenameUtils.removeExtension(fileAndMedia._1.getPath)
        val outFile = new File(outFilename.replace("Data Sources", "CSV"))
        outFile.mkdirs()
        val firstOutFile = new PerSheetOutputDispatcher(outFile.getPath);
        val xls2csv = new XlsToCsv(fileAndMedia._1.getPath, firstOutFile, -1);
        xls2csv.process();
      }
      
    }
 
  }
}



object ReadXlsFile {
  def main(args:Array[String]) {
    val dir = new File("/Users/stephenalba/Documents/study/bigdata/Data Sources");
    println("\nTraverse xls* and csv only");
    val extensions = List(".xls", ".csv", ".xlsx")
    val files = walkTree(dir)
    val filesXls = filterExtensions(files, extensions);
    val mime = for (f <- filesXls) yield {
      detectMime(f)
    }
    mime.foreach(convertToCsv)
 }
}