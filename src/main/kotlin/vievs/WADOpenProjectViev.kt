package vievs

import Controllers.WADProjectsController
import Static.WADStatus
import javafx.scene.Parent
import javafx.scene.control.ListView
import javafx.scene.control.SelectionMode
import tornadofx.*

class WADOpenProjectViev : Fragment() {
    val  wadProjectsController : WADProjectsController by inject()
    override val root: Parent = vbox {
        var listViev : ListView<String> by singleAssign()
        listViev = listview(WADStatus.stat.openProjectListName){
            selectionModel.selectionMode = SelectionMode.SINGLE
            contextmenu {
                item("Open").action {
                    if (listViev.selectedItem != null){
                        if (wadProjectsController.openProject(listViev.selectedItem!!) == 0){
                            WADStatus.stat.openProjectListName
                        }
                    }
                }
                item("Delete").action {
                    if (listViev.selectedItem != null){
        //                if (wadProjectsController.deleteProject(listViev.selectedItem!!) == 0){
        //                    WADStatus.stat.openProjectListName
        //                }
                    }
                }
            }
        }
        listViev.onDoubleClick {
            if (listViev.selectedItem != null){
                if (wadProjectsController.openProject(listViev.selectedItem!!) == 0){
                    WADStatus.stat.openProjectListName
                }
            }
        }

        button("hh"){
            action {
        //        WADStatus.stat.openProjectListName.add("foo")
            }
        }
    }
    override fun onUndock() {
    //    WADStatus.stat.openProjectStatusCode = 0
    }
}