package file

import com.google.gson.Gson
import json.WADProjectJson
import models.ProjectSettings
import models.WADProject
import staticWAD.WADStatic
import java.io.File
import java.io.FileReader

class IOFileJson {
    fun loadAllProjects(): Int
    {
        var resultCode = 0
        try {
            var file = File("src/main/resources/allproject.json")
            if(file.exists()){
                val gson = Gson()
                var allWADProjectJson = mutableListOf<WADProjectJson>()
                allWADProjectJson = gson.fromJson(FileReader(file), allWADProjectJson::class.java)
                for (i in 0 until allWADProjectJson.size){
                    val wadProject = wadProjectJsonToWadProject(gson.fromJson(gson.toJson(allWADProjectJson[i]),WADProjectJson::class.java))
                    WADStatic.WADstat.allProjectList.add(wadProject)
                    WADStatic.WADstat.allProjectListName.add(wadProject.name)
                }
            }
            resultCode = -1
        }
        catch (e: Exception){
            resultCode = 101
        }
        return resultCode
    }

    fun loadOpenProjects(): Int
    {
        var resultCode = 0
        try {
            var file = File("src/main/resources/openproject.json")
            if(file.exists()){
                val gson = Gson()
                var openWADProjectJson = mutableListOf<WADProjectJson>()
                openWADProjectJson = gson.fromJson(FileReader(file), openWADProjectJson::class.java)
                for (i in 0 until openWADProjectJson.size){
                    val wadProject = wadProjectJsonToWadProject(gson.fromJson(gson.toJson(openWADProjectJson[i]),WADProjectJson::class.java))
                    WADStatic.WADstat.openProjectList.add(wadProject)
                    WADStatic.WADstat.openProjectListName.add(wadProject.name)
                }
            }
            resultCode = -1
        }
        catch (e: Exception){
            resultCode = 101
        }
        return resultCode
    }


    fun wadProjectToWadProjectJson(wadProject: WADProject) : WADProjectJson
    {
        return WADProjectJson(
            wadProject.name,
            wadProject.domenName,
            wadProject.path,
            wadProject.status,
            wadProject.resumeKey,
            WADProjectJson.ProjectSettingsJson(
                wadProject.projectSettings.from,
                wadProject.projectSettings.to,
                wadProject.projectSettings.timestamp,
                wadProject.projectSettings.fileType,
                wadProject.projectSettings.fileLimit
            )
        )
    }

    private fun wadProjectJsonToWadProject(wadProjectJson: WADProjectJson): WADProject {
        var wadProjectSettings = ProjectSettings(
            wadProjectJson.projectSettings.from,
            wadProjectJson.projectSettings.to,
            wadProjectJson.projectSettings.timestamp,
            wadProjectJson.projectSettings.fileType,
            wadProjectJson.projectSettings.fileLimit
        )
        return WADProject(
            wadProjectJson.name,
            wadProjectJson.domenName,
            wadProjectJson.path,
            wadProjectJson.status,
            wadProjectJson.resumeKey,
            wadProjectSettings
        )
    }
}