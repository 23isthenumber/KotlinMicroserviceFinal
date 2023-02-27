package com.kotlin.productservice.service.impl

import com.kotlin.productservice.exception.not_found.ProductNotFoundException
import com.kotlin.productservice.model.dto.ProductDTO
import com.kotlin.productservice.model.dto.ProductSearchRequest
import com.kotlin.productservice.model.dto.toDTO
import com.kotlin.productservice.model.entity.Product
import com.kotlin.productservice.model.enums.ProductStatus
import com.kotlin.productservice.queue_broker.QueueBrokerSender
import com.kotlin.productservice.repository.ProductRepository
import com.kotlin.productservice.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(val productRepository: ProductRepository, val queueBrokerSender: QueueBrokerSender) : ProductService  {

    override fun getActiveProductsIds(idList: MutableList<Long>): List<Long?> {
        return getAllByIdList(idList)
            .filter { product -> ProductStatus.ACTIVE == product.status }
            .map(ProductDTO::id)
            .toList()
    }

    override fun getAllByIdList(idList: MutableList<Long>): List<ProductDTO> {

        val allProducts : List<ProductDTO> = productRepository.findAllById(idList)
            .map { it.toDTO() }
            .toMutableList()

        val idListEmptyCheck : List<Long?> = idList - allProducts.map { it.id }.toMutableList().toSet()

        if(idListEmptyCheck.isNotEmpty()){
            throw ProductNotFoundException(idListEmptyCheck.first())
        }
        return allProducts;
    }

    override fun getById(id: Long): ProductDTO {
        val productDto = findProductById(id).toDTO()
        queueBrokerSender.sendMessage(productDto)
        return productDto
    }

    override fun search(productSearchRequest: ProductSearchRequest): Page<ProductDTO> {
        TODO("Not yet implemented")
    }

    override fun updateProduct(product: ProductDTO, id: Long): Product {
        val productToUpdate : Product = findProductById(id)
        setProductFields(product, productToUpdate)
        return productRepository.save(productToUpdate)
    }

    override fun updateProductStatus(id: Long): Product {
        val productToUpdate = findProductById(id)
        if (productToUpdate.status == ProductStatus.ACTIVE) {
            productToUpdate.status = ProductStatus.INACTIVE
        } else {
            productToUpdate.status = ProductStatus.ACTIVE
        }
        return productRepository.save(productToUpdate)
    }

    private fun findProductById(id: Long): Product {
        return productRepository.findById(id)
            .orElseThrow { ProductNotFoundException(id) }
    }

    private fun setProductFields(productDTO: ProductDTO, productToUpdate: Product) {
        productDTO.id = productToUpdate.id
        productToUpdate.name = productDTO.name
        productToUpdate.description = productDTO.description
        productToUpdate.price.add(productDTO.price)
        productToUpdate.property.clear()
        productToUpdate.property.addAll(productDTO.property)
    }
}