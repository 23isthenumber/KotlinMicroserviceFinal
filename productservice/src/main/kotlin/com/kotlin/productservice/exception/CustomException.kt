package com.kotlin.productservice.exception

import org.springframework.http.HttpStatus

open class CustomException(message:String) : RuntimeException(message) {

    var errorCode: String? = null
    //TODO: read
    lateinit var httpStatus: HttpStatus
    var objects: Map<Any, Any>? = null
}