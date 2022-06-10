import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import file.IOFileJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.ProjectStatus
import models.WADProject
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import services.WADGetFileListService
import staticWAD.WADStatic
import tornadofx.onChange
import java.io.File
import java.util.*


class WADJob(private val wadProject: WADProject) {
    val name = wadProject.name
    var flag = false
    fun main() {
        WADStatic.WADstat.wadProjectRunList.onChange {
            WADStatic.WADstat.wadProjectRunList.forEach{ it ->
                if (it.projectName == name && it.statusMessage){
                    if(it.codeMessage == 1 && !WADStatic.WADstat.wadProjectList.last{it.projectName == name}.run){
                        job(wadProject)
                        flag = true
                    }
                    if (it.codeMessage == 0){
                        flag = false
                    }
                    //println(WADStatus.stat.wadProjectRunList)
                    it.statusMessage = false
                }
            }
        }
    }

    fun job(wadProject: WADProject){
        CoroutineScope(Dispatchers.IO).launch {
            WADStatic.WADstat.wadProjectList.last{it.projectName == name}.run = true
            val retrofit : Retrofit = Retrofit.Builder().baseUrl("https://web.archive.org")
                .addConverterFactory(ScalarsConverterFactory.create()).addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            val service = retrofit.create(WADGetFileListService::class.java)
            val iofj = IOFileJson()
            var wadProjectGet = Pair(0,wadProject)
            while (flag) {
                if (File("${wadProject.path}\\${wadProject.name}.wadproject").exists())
                {
                    wadProjectGet = iofj.loadProject("${wadProject.path}\\${wadProject.name}.wadproject")
                }
                var wadProject = wadProjectGet.second
                if (wadProject.status == "") {
                    WADStatic.WADstat.wadProjectList.add(ProjectStatus(wadProject.name, true, 0, "Load file list: part${wadProject.projectSettings.timestamp}"))
                    var allFiles = wadProject.projectSettings.fileLimit * wadProject.projectSettings.timestamp
                    val result = service.getFileList(
                        "${wadProject.domenName}*",
                        wadProject.projectSettings.fileLimit,
                        wadProject.resumeKey,
                        wadProject.projectSettings.from,
                        wadProject.projectSettings.to,
                        wadProject.projectSettings.fileType
                    ).await()
                    var fileList = result.split("\n").toMutableList()
                    wadProject.resumeKey = fileList[fileList.size - 2]
                    if (fileList[fileList.size - 3] == "") {
                        fileList = fileList.subList(0, fileList.size - 3)
                        allFiles += fileList.size
                        wadProject.projectSettings.timestamp += 1
                        println("prod")
                        println(allFiles)
                    } else {
                        fileList = fileList.subList(0, fileList.size - 1)
                        wadProject.resumeKey = ""
                        flag = false
                        allFiles += fileList.size
                        wadProject.status = "1"
                        println("end")
                        println("all files: " + (allFiles + 2).toString())
                    }
                    fileList.replaceAll { "$it 0" }
                    val resultSaveFile =
                        iofj.saveFileList("${wadProject.path}\\part${wadProject.projectSettings.timestamp}", fileList)
                    if (resultSaveFile != -1) {
                        flag = false
                    }
                    iofj.saveProject(wadProject)
                }
            }
            WADStatic.WADstat.wadProjectList.last{it.projectName == name}.run = false
        }
    }
}


