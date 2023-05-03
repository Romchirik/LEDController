package nsu.titov.ledcontroller.domain.repository

import nsu.titov.ledcontroller.domain.model.project.Project

interface ProjectsRepository {

    fun getProject(id: Int): Project

    fun saveProject(source: Project)
}