package com.kotlin.productservice.queue_broker

interface QueueBrokerSender {

    fun sendMessage(objectToSend : Any){

    }
}