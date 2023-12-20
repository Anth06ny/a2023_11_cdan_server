package org.example.a2023_11_cdan_server.restcontroller

import org.example.a2023_11_cdan_server.MessageBean
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tchat")
class TchatAPI {

    private val list = ArrayList<MessageBean>()

    //http://localhost:8080/tchat/saveMessage
    //{ "pseudo": " ", "message": "Salut" }
    @PostMapping("/saveMessage")
    fun saveMessage(@RequestBody messageBean: MessageBean) {
        println("/saveMessage messageBean=$messageBean")
        list.add(messageBean)
    }


    //http://localhost:8080/tchat/allMessages
    @GetMapping("/allMessages")
    fun allMessages(): List<MessageBean> {
        println("/allMessages")
        return list.takeLast(10)
    }


    //http://localhost:8080/tchat/filter?pseudo=toto&filter=ca
    @GetMapping("/filter")
    fun filter(pseudo: String = "", filter: String = ""): List<MessageBean> {
        println("/filter  pseudo=#$pseudo# filter=#$filter#")
        return list.filter {
            (pseudo.isEmpty() || it.pseudo.equals(pseudo, true))
                    &&
                    (filter.isEmpty() || it.message.contains(filter, true))
        }.takeLast(10)
    }


}
