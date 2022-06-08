package controller

import file.IOFileJson
import models.WADProject
import staticWAD.WADStatic
import tornadofx.Controller
import views.WADCreateProjectView

import java.io.File

class WADProjectsController : Controller() {
    fun createProjectView(): Int
    {
        when (WADStatic.WADstat.createProjectStatusCode){
            0 -> {
                find<WADCreateProjectView>().openWindow(owner = null)
                WADStatic.WADstat.createProjectStatusCode = 1
            }
            1 -> println("ocp")
            2 -> println("create")
            3 -> println("error db")
            4 -> println("error dir")
            5 -> {
                println("cancel")
                WADStatic.WADstat.createProjectStatusCode = 0
            }
        }
        return -1
    }


    fun createProject(wadProject: WADProject): Int
    {
        var resultCode = 0
        val iofj = IOFileJson()
        resultCode = iofj.saveAllProjects(wadProject)
        if (resultCode == -1){
            resultCode = iofj.saveOpenProjects(wadProject)
        }
        if (resultCode == -1){
            if(File(wadProject.path).mkdirs()){
                resultCode = iofj.saveProject(wadProject)
            }
        }
        if (resultCode == -1) {
            WADStatic.WADstat.allProjectList.add(wadProject)
            WADStatic.WADstat.allProjectListName.add(wadProject.name)
            WADStatic.WADstat.openProjectList.add(wadProject)
            WADStatic.WADstat.openProjectListName.add(wadProject.name)
        }
        return resultCode
    }

    fun openProjectView(){

    }

    fun openProject(name: String): Int
    {
        return -1
    }
}