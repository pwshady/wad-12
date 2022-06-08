package views

import javafx.scene.Parent
import tornadofx.*


class WADProjectsView() : View() {
    init {

    }
    override val root: Parent = borderpane {
        var topViev = WADProjectTopView()
        top(topViev::class)

        button("t1"){
            action {

            }
        }
        //center(WADProjectCenterViev::class)
        //var rightViev = WADProjectRightViev()
        //right(rightViev::class)

    }
}