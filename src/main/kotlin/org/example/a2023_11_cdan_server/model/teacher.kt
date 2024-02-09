package org.example.a2023_11_cdan_server.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Entity
@Table(name = "teacher")
data class TeacherBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null,
    var name:String = "",
    var code : Int? = null
)

@Repository                       //<Bean, Typage Id>
interface TeacherRepository : JpaRepository<TeacherBean, Long> {

}

@Service
class TeacherService(val teacherRepository: TeacherRepository) {

    fun createTeacher(id:Long? = null, name:String?, code:Int?) : TeacherBean {
        if(name.isNullOrBlank()){
            throw Exception("Name missing")
        }
        else if(code != null && code !in 1..10){
            throw Exception("Code incorrecte")
        }
        val teacher = TeacherBean(id, name, code)
        teacherRepository.save(teacher)
        return teacher
    }

    fun getAll() = teacherRepository.findAll()
}

