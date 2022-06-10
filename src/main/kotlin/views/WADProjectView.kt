package views

import WADJob
import controller.WADProjectController
import javafx.application.Platform
import javafx.collections.ObservableList
import javafx.geometry.Side
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import models.WADProject
import staticWAD.WADStatic
import tornadofx.*
import kotlin.concurrent.thread

class WADProjectView(wadProject: WADProject) : Fragment() {
    val  wadProjectController : WADProjectController by inject()
    override val root: Parent = vbox {
        var textFieldDomenName : TextField by singleAssign()
        var statusLabel : Label by singleAssign()
        var buttonStart : Button by singleAssign()
        var buttonStop : Button by singleAssign()
        var taskStatus = TaskStatus()
        var wadJob = WADJob(wadProject)
        wadJob.main()
        hbox {
            label("Domen name: "){
                this.style{
                    //this.backgroundColor += c("red")
                }
            }
            textFieldDomenName = textfield {
                this.disableProperty().set(true)
            }
            textFieldDomenName.text = wadProject.domenName
            checkbox("Only url") {  }
            buttonStart = button("Start"){
                action {
                    wadProjectController.sendMessageProject(wadProject.name,true,1)
                    buttonStart.disableProperty().set(true)
                    buttonStop.disableProperty().set(false)
                }
            }
            buttonStop = button("Stop"){
                action {
                    wadProjectController.sendMessageProject(wadProject.name,true,0)
                    buttonStart.disableProperty().set(false)
                    buttonStop.disableProperty().set(true)
                }
            }
            button("ttt") {
                action {
                    println(WADStatic.WADstat.wadProjectList)

                }
            }

            //progressbar {
            //    thread {
            //        var i = 0
            //        while (true) {
            //            Platform.runLater { progress = i.toDouble() / 10 }
            //            Thread.sleep(1000)
            //            if (WADStatic.WADstat.wadProjectList.lastOrNull { it.projectName == wadProject.name }?.run == true) {
            //                i++
            //            }
            //            if (i == 10) {
            //                i = 0
            //            }
            //        }
            //    }
            //}
            statusLabel = label {
                text = "ggggg"+wadProjectController.getStatus(wadProject.name)
            }
            WADStatic.WADstat.wadProjectList.onChange {
                if (WADStatic.WADstat.wadProjectList.lastOrNull { it.projectName == wadProject.name } != null){
                    var statusStr = WADStatic.WADstat.wadProjectList.lastOrNull { it.projectName == wadProject.name }!!.statusText
                    Platform.runLater { statusLabel.text = "Status: $statusStr" }
                }
            }
        }

        drawer (side = Side.BOTTOM, multiselect = false) {
            minWidth = 1200.0
            minHeight = 800.0
            item("files", expanded = true){

            }

            item("parsing"){

            }
            item("test") {

                    vbox {
                        class Branch(val id : Int, val code : String, val city : String, val state : String)
                        class Region(val id : Int, val name : String, val country : String, val branches : ObservableList<Branch>)

                        val  regions = listOf(Region(1,"Pac Nor", "USA", listOf(Branch(1,"d", "seatl","WA")).observable())).observable()
                        tableview(regions) {

                        }
                    }

            }
        }

    }
}


