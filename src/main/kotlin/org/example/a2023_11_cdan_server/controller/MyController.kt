package org.example.a2023_11_cdan_server.controller

import org.example.a2023_11_cdan_server.model.StudentBean
import org.example.a2023_11_cdan_server.model.StudentRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class MyController {

    /* -------------------------------- */
    // Formulaire
    /* -------------------------------- */
    //http://localhost:8080/form
    @GetMapping("/form")
    fun form(studentBean: StudentBean): String {

        println("/form")
        //Spring créera une instance de StudentBean qu'il mettra dans le model
        return "studentForm"
    }

    @PostMapping("/formSubmit")
    fun formResponse(studentBean: StudentBean, redirect: RedirectAttributes): String {
        println("/formSubmit :  : $studentBean")

        try  {
            if (studentBean.name.isBlank()) {
                throw Exception("Nom manquant")
            }
            else if (studentBean.note < 0) {
                throw Exception("Note positive attendu")
            }

            redirect.addFlashAttribute("studentBean", studentBean)
            //Cas qui marche
            return "redirect:formResult" // Redirection sur /formResult
        } catch (e:Exception) {
            //Affiche le détail de l'erreur dans la console serveur
            e.printStackTrace()

            //pour garder les données entrées dans le formulaire par l'utilisateur
            redirect.addFlashAttribute("studentBean", studentBean)
            //Pour transmettre le message d'erreur
            redirect.addFlashAttribute("errorMessage", "Erreur : ${e.message}")
            //Cas d'erreur
            return "redirect:form" //Redirige sur /form
        }
    }

    @GetMapping("/formResult") //Affiche la page résultat
    fun formResult(): String {
        return "studentFormResult" //Lance studentForm.html
    }

    /* -------------------------------- */
    // Autre exo
    /* -------------------------------- */

    //http://localhost:8080/filter?name=toto&note=12
    @GetMapping("/filter")
    fun filter(name: String? = null, note: Int? = null, model: Model): String {
        println("/filter name$$name note=$note")


        val list = StudentRepository.load().filter {
            //Il n'y a pas de filtre de nom ou le filtre correspond
            (name == null || it.name?.lowercase() == name.lowercase())
                    &&
                    //idem note
                    (note == null || note == it.note)
        }

        model.addAttribute("text", "Recherche : name=$name note=$note")
        model.addAttribute("student", null)
        model.addAttribute("listStudent", list)

        //Nom du fichier HTML que l'on souhaite afficher
        return "welcome"
    }

    //http://localhost:8080/add?name=toto&note=12
    @GetMapping("/add")
    fun add(name: String, note: Int, model: Model): String {
        println("/add name$$name note=$note")

        val student = StudentBean(name, note)
        StudentRepository.save(student)

        val list = StudentRepository.load()

        model.addAttribute("text", "Ajout de")
        model.addAttribute("student", student)
        model.addAttribute("listStudent", list)

        //Nom du fichier HTML que l'on souhaite afficher
        return "welcome"
    }


    //http://localhost:8080/hello
    @GetMapping("/hello")
    fun testHello(model: Model): String {
        println("/hello")

        val student = StudentBean("Toto", 12)
        val list = arrayListOf(
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