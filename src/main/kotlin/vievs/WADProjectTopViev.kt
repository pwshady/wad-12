package vievs

import controller.WADProjectsController
import javafx.scene.Parent
import tornadofx.*

class WADProjectTopViev() : View() {
    val  projectsController : WADProjectsController by inject()
    override val root: Parent = menubar {
        menu("File") {
            //item("New project").action { projectsController.createProjectViev() }
            //item("Open project").action { projectsController.openProjectViev() }
            menu ("Stop"){  }
            menu ("Stop and close"){  }
            item("New project")
        }

        menu("Viev"){
        }

        menu ("Help"){
            item("About")
        }

    }
}