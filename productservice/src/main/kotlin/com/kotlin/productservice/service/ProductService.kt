package com.kotlin.productservice.service

import com.kotlin.productservice.model.dto.ProductDTO
import com.kotlin.productservice.model.dto.ProductSearchRequest
import com.kotlin.productservice.model.entity.Product
import org.springframework.data.domain.Page

interface ProductService {
    //TODO : mutability
    fun getActiveProductsIds(idList : MutableList<Long>) : List<Long?>
    fun getAllByIdList(idList : MutableList<Long>) : List<ProductDTO>
    fun getById(id : Long) : ProductDTO
    fun search(productSearchRequest : ProductSearchRequest) : Page<ProductDTO>
    fun updateProduct(product : ProductDTO, id : Long) : Product
    fun updateProductStatus(id : Long) : Product
}
