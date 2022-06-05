package vievs

import javafx.scene.Parent
import tornadofx.*


class WADProjectsViev() : View() {
    init {

    }
    override val root: Parent = borderpane {
        var topViev = WADProjectTopViev()
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