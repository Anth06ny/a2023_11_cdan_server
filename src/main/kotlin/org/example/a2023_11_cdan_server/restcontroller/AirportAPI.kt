package org.example.a2023_11_cdan_server.restcontroller

import org.example.a2023_11_cdan_server.PlaneBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/airport")
class AirportAPI {

    var tab = Array<PlaneBean?>(5) { null}

    @GetMapping("/nextplace")
    fun  nextplace(): Int {
        println("/nextplace")

//        for (i in tab.indices){
//            if(tab[i] == null){
//                 return i
//            }
//        }
//        return -1

        return tab.indexOfFirst { it == null }
    }

    //Méthode qui permet de réinitialiser les données entre 2 tests
    //http://localhost:8080/airport/reset
    @GetMapping("/reset")
    fun reset(){
        println("/reset")
        tab = Array(5) { null}
    }
}

