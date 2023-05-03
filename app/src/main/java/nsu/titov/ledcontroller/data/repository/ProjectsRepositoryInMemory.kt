package nsu.titov.ledcontroller.data.repository

import nsu.titov.ledcontroller.domain.model.layer.PixelatedLayer
import nsu.titov.ledcontroller.domain.model.project.Project
import nsu.titov.ledcontroller.domain.repository.ProjectsRepository
import kotlin.random.Random

object ProjectsRepositoryInMemory : ProjectsRepository {

    private val projects: MutableMap<Int, Project> = HashMap()

    override fun getProject(id: Int): Project = projects[id] ?: Project(
        id = Random(13).nextInt(),
        layer = PixelatedLayer.Default
    ).also { projects[it.id] = it }


    override fun saveProject(source: Project) {
        projects[source.id] = source
    }
}