package arpitparekh.chatappproject.chat

class Message {

    var message : String? = null
    var senderId : String? = null

    constructor()
    constructor(message: String?, senderId: String?) {
        this.message = message
        this.senderId = senderId
    }

    override fun toString(): String {
        return message!!
    }


}