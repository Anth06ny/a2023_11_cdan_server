package org.example.a2023_11_cdan_server.controller

import org.example.a2023_11_cdan_server.StudentBean
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MyController {

    //http://localhost:8080/hello
    @GetMapping("/hello")
    fun testHello(model :Model): String {
        println("/hello")

        val student = StudentBean("Toto", 12)
        val list =  arrayListOf(
            StudentBean("Bob", 10),
            StudentBean("Bobby", 18),
            StudentBean("Charles", 20)
        )

        model.addAttribute("text", "Bonjour")
        model.addAttribute("student", student)
        model.addAttribute("listStudent", list)



        //Nom du fichier HTML que l'on souhaite afficher
        return "welcome"
    }
}