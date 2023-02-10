package com.kotlin.productservice.exception.not_found

import com.kotlin.productservice.exception.CustomException
import org.springframework.http.HttpStatus

class ProductNotFoundException(id : Long?) : CustomException(String.format("Product with id: %d does not exist", id)){

    private val ERROR_CODE = "EP002"

    init{
        this.errorCode = ERROR_CODE
        this.httpStatus = HttpStatus.NOT_FOUND
    }
}