package views

import controller.WADProjectsController
import javafx.scene.Parent
import javafx.scene.control.ListView
import javafx.scene.control.SelectionMode
import staticWAD.WADStatic
import tornadofx.*

class WADOpenProjectView : Fragment() {
    val  wadProjectsController : WADProjectsController by inject()
    override val root: Parent = vbox {
        var listViev : ListView<String> by singleAssign()
        listViev = listview(WADStatic.WADstat.openProjectListName){
            selectionModel.selectionMode = SelectionMode.SINGLE
            contextmenu {
                item("Open").action {
                    if (listViev.selectedItem != null){
                        if (wadProjectsController.openProject(listViev.selectedItem!!) == 0){
                            WADStatic.WADstat.openProjectListName
                        }
                    }
                }
                item("Delete").action {
                    if (listViev.selectedItem != null){
        //                if (wadProjectsController.deleteProject(listViev.selectedItem!!) == 0){
        //                    WADStatus.stat.openProjectListName
                        }
                    }
                }
            }

        listViev.onDoubleClick {
            if (listViev.selectedItem != null){
                if (wadProjectsController.openProject(listViev.selectedItem!!) == 0){
                    WADStatic.WADstat.openProjectListName
                }
            }
        }

    }
    override fun onUndock() {
        WADStatic.WADstat.openProjectStatusCode = 0
    }
}