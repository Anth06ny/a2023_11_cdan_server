package org.example.a2023_11_cdan_server.restcontroller

import jakarta.servlet.http.HttpSession
import org.example.a2023_11_cdan_server.model.StudentBean
import org.example.a2023_11_cdan_server.model.TeacherBean
import org.example.a2023_11_cdan_server.model.TeacherService
import org.springframework.web.bind.annotation.*

@RestController
class MyRestController(val teacherService: TeacherService) {


    //http://localhost:8080/test
    @GetMapping("/test", )
    fun test(session: HttpSession): String {
        println("/test sessionId=${session.id}" )


        return "HelloWorld sessionId=${session.id}"
    }


    /* -------------------------------- */
    // TestJPA
    /* -------------------------------- */

    //http://localhost:8080/testTeacher?name=blabla
    @GetMapping("/testTeacher", )
    fun testTeacher(id:Long? = null, name:String, code : Int? = null): List<TeacherBean> {
        println("/testTeacher")

        teacherService.createTeacher(id, name, code)

        return teacherService.getAll()
    }

    /* -------------------------------- */
    // POST
    /* -------------------------------- */

    //http://localhost:8080/increment
//Json Attendu : {"name": "toto","note": 12}
    @PostMapping("/increment")
    fun increment(@RequestBody student: StudentBean): StudentBean {
        println("/increment : $student")

        student.note++

        return student
    }

    //http://localhost:8080/receiveStudent
//Json Attendu : {"name": "toto","note": 12}
    @PostMapping("/receiveStudent")
    fun receiveStudent(@RequestBody student: StudentBean) {
        println("/receiveStudent : $student")

        //traitement, mettre en base…
        //Retourner d'autre données
    }

    /* -------------------------------- */
    // GET
    /* -------------------------------- */

    //http://localhost:8080/boulangerie?nbCroissant=5&nbSandwitch=2
    @GetMapping("/boulangerie")
    fun boulangerie(nbCroissant:Int = 0, nbSandwitch:Int = 0): String {
        println("/boulangerie nbCroissant=$nbCroissant nbSandwitch=$nbSandwitch")



        return String.format("%.2f" , (nbCroissant * 0.95 + nbSandwitch * 4)).replace("," , "€")
    }


    //http://localhost:8080/getStudent
    @GetMapping("/getStudent")
    fun getStudent(): StudentBean {
        println("/getStudent")
        var student =  StudentBean("toto", 12)

        return student
    }

    //http://localhost:8080/createStudent?nom=bob&note=12
    @GetMapping("/createStudent")
    fun createStudent(@RequestParam(value = "nom", defaultValue = "") p1: String, note: Int): StudentBean {
        //p1 contiendra bob
        //note contiendra 12
        println("/createStudent : p1=$p1 note=$note")

        return StudentBean(p1, note)
    }


    //http://localhost:8080/max?p1=3&p2=12
    @GetMapping("/max")
    fun max(p1:Int?, p2:Int?): Int? {
        println("/max p1=$p1 p=$p2")

        if(p2 == null) {
            return p1
        }
        else if(p1 == null) {
            return p2
        }

       return kotlin.math.max(p1, p2)
    }

    //http://localhost:8080/max2?p1=3&p2=tata
    @GetMapping("/max2")
    fun maxPlusRapide(p1:String?, p2:String?): Int? {
        println("/max2 p1=$p1 p=$p2")

        var p1Int = p1?.toIntOrNull()
        var p2Int = p2?.toIntOrNull()

        if(p1Int == null) {
            return p2Int
        }
        else if(p2Int == null) {
            return p1Int
        }

        return kotlin.math.max(p1Int, p2Int)
    }

}