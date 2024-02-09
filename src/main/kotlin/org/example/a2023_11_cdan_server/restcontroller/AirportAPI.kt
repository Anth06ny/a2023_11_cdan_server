package org.example.a2023_11_cdan_server.restcontroller

import org.example.a2023_11_cdan_server.model.PlaneBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/airport")
class AirportAPI {

    var tab = Array<PlaneBean?>(5) { null}

    //http://localhost:8080/airport/nextplace
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


    //http://localhost:8080/airport/park?position=2
    //{ "name":"toto", "id":"12" }
    @PostMapping("/park")
    fun park(@RequestBody plane: PlaneBean, position:Int?): Int {

        println("/park position=$position plane=$plane" )

        if(plane.id.isBlank() || plane.name.isBlank()){
            return 214
        }
        else if(position == null || position !in tab.indices) {
            return 216
        }
        else if(tab[position] != null) {
            return 215
        }

        tab[position] = plane

        return 200

    }

    //http://localhost:8080/airport/takeoff
    @GetMapping("/takeoff")
    fun  takeoff (position:Int?): Int {
        println("/takeoff position=$position")

        if(position == null || position !in tab.indices) {
            return 216
        }
        else if(tab[position] == null) {
            return 215
        }

        tab[position] = null
        return 200
    }

    //Méthode qui permet de réinitialiser les données entre 2 tests
    //http://localhost:8080/airport/reset
    @GetMapping("/reset")
    fun reset(){
        println("/reset")
        tab = Array(5) { null}
    }

    @GetMapping("/state")
    fun state() = tab
}

