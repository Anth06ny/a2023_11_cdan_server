package org.example.a2023_11_cdan_server.controller

import jakarta.servlet.http.HttpSession
import org.example.a2023_11_cdan_server.model.UserBean
import org.example.a2023_11_cdan_server.model.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/user")
class MyLoginController {

    //Affichage du formulaire
    //http://localhost:8080/user/login
    @GetMapping("/login")
    fun form(userBean: UserBean, session: HttpSession): String {
        println("/login ${session.id}")

        if (UserService.findBySessionId(session.id) != null) {
            //déja connecté je redirige
            return "redirect:/user/userregister"
        }

        //Spring créera une instance de StudentBean qu'il mettra dans le model
        return "login"
    }


    //Traitement du formulaire
    @PostMapping("/loginSubmit")
    fun loginSubmit(
        userBean: UserBean,
        redirect: RedirectAttributes,
        session: HttpSession
    ): String {
        println("/loginSubmit :  : $userBean")

        try {
            if (userBean.login.isBlank()) {
                throw Exception("Nom manquant")
            }
            else if (userBean.password.isBlank()) {
                throw Exception("Password manquant")
            }

            val userBdd: UserBean? = UserService.findByLogin(userBean.login)

            if (userBdd == null) {
                //User inexistant je l'ajoute
                userBean.sessionId = session.id
                UserService.save(userBean)
            }
            else {
                //Utilisateur existant
                if (userBean.password == userBdd.password) {
                    //password ok
                    //Je sauvegarde son session ID
                    userBean.sessionId = session.id
                    UserService.save(userBean)
                }
                else {
                    //password !ok
                    throw Exception("Mot de passe incorrect")
                }
            }

            //Cas qui marche
            return "redirect:/user/userregister" // Redirection sur /formResult
        }
        catch (e: Exception) {
            //Affiche le détail de l'erreur dans la console serveur
            e.printStackTrace()

            //pour garder les données entrées dans le formulaire par l'utilisateur
            redirect.addFlashAttribute("userBean", userBean)
            //Pour transmettre le message d'erreur
            redirect.addFlashAttribute("errorMessage", "Erreur : ${e.message}")
            //Cas d'erreur
            return "redirect:/user/login" //Redirige sur /form
        }
    }


    //affichage du chat
    @GetMapping("/userregister") //Affiche la page résultat
    fun userRegister(model: Model, session: HttpSession, redirect: RedirectAttributes): String {

        try {
            val user = UserService.findBySessionId(session.id)
            if (user == null) {
                throw Exception("Veuillez vous reconnecter")
            }

            model.addAttribute("userBean", user)
            model.addAttribute("userList", UserService.load())
            return "userregister" //Lance studentForm.html
        }
        catch (e: Exception) {
            e.printStackTrace()
            //Pour transmettre le message d'erreur
            redirect.addFlashAttribute("errorMessage", "Erreur : ${e.message}")
            //Cas d'erreur
            return "redirect:/user/login" //Redirige sur /form
        }
    }

    @GetMapping("/logout") //Affiche la page résultat
    fun logout(session: HttpSession): String {
        UserService.findBySessionId(session.id)?.let {
            it.sessionId = ""
            UserService.save(it)
        }

        return "redirect:/user/login"
    }

}