package controller

import staticWAD.WADStatic
import tornadofx.Controller

class WADProjectsController : Controller() {
    fun createProjectViev()
    {
        when (WADStatic.WADstat.createProjectStatusCode){
            0 -> {
                find<WADCreateProjectViev>().openWindow(owner = null)
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
    }
}