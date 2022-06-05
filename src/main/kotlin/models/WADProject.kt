package models

data class WADProject(
    var name: String = "",
    var domenName: String = "",
    var path: String = "",
    var status: Int = 0,
    var resumeKey: String = "",
    var projectSettings: ProjectSettings = ProjectSettings()
)