package com.kotlin.productservice.service.impl

import com.kotlin.productservice.exception.not_found.ProductNotFoundException
import com.kotlin.productservice.model.dto.ProductDTO
import com.kotlin.productservice.model.dto.ProductSearchRequest
import com.kotlin.productservice.model.dto.toDTO
import com.kotlin.productservice.model.entity.Product
import com.kotlin.productservice.model.enums.ProductStatus
import com.kotlin.productservice.repository.ProductRepository
import com.kotlin.productservice.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(val productRepository: ProductRepository) : ProductService  {

    //TODO: logger

    override fun getActiveProductsIds(idList: MutableList<Long>): List<Long?> {
       //log.info(">> Getting IDs of active products");
        return getAllByIdList(idList)
            .filter { product -> ProductStatus.ACTIVE == product.status }
            .map(ProductDTO::id)
            .toList()
    }

    override fun getAllByIdList(idList: MutableList<Long>): List<ProductDTO> {

        //TODO: var, val, mutable, null
        // log.info(">> Getting all products with IDs from the list: {}", idList);

        var allProducts : List<ProductDTO> = productRepository.findAllById(idList)
            .map { it.toDTO() }
            .toMutableList()

        val idListEmptyCheck : List<Long?> = idList - allProducts.map { it.id }.toMutableList().toSet()


        if(idListEmptyCheck.isNotEmpty()){
            throw ProductNotFoundException(idListEmptyCheck.first())
        }

        // log.info("<< Products mapped to ProductDTOs: {} ", allProducts);
        return allProducts;
    }

    override fun getById(id: Long): ProductDTO {
      //  log.info(">> Getting ProductDTO by ID: {}", id);
        return findProductById(id).toDTO()
    }

    override fun search(productSearchRequest: ProductSearchRequest): Page<ProductDTO> {
        TODO("Not yet implemented")
    }

    override fun updateProduct(product: ProductDTO, id: Long): Product {
       // log.info(">> Updating product with ID: {}", id)
        var productToUpdate : Product = findProductById(id)
        setProductFields(product, productToUpdate)
       // log.info("<< Product after update: {}", productToUpdate)
        return productRepository.save(productToUpdate)
    }

    override fun updateProductStatus(id: Long): Product {
      //  log.info(">> Updating status of product with ID: {}", id)
        val productToUpdate = findProductById(id)
        if (productToUpdate.status == ProductStatus.ACTIVE) {
            productToUpdate.status = ProductStatus.INACTIVE
        } else {
            productToUpdate.status = ProductStatus.ACTIVE
        }
       // log.info("<< Status of product with ID: {} changed to: {}", id, productToUpdate.getStatus())
        return productRepository.save(productToUpdate)
    }

    private fun findProductById(id: Long): Product {
        //  log.info(">> Getting Product by ID: {}", id)
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