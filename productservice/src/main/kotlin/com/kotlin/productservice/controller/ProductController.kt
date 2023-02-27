package com.kotlin.productservice.controller

import com.kotlin.productservice.model.dto.ProductDTO
import com.kotlin.productservice.model.dto.ProductSearchRequest
import com.kotlin.productservice.model.entity.Product
import com.kotlin.productservice.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/product")
class ProductController(val productService: ProductService) {

    @PostMapping
    fun getActiveProductsIds(@RequestBody idList: MutableList<Long>): ResponseEntity<List<Long?>> {
        return ResponseEntity.ok(productService.getActiveProductsIds(idList))
    }

    @PostMapping("/info")
    fun getAllByIdList(@RequestBody idList: MutableList<Long>): ResponseEntity<List<ProductDTO>> {
        return ResponseEntity.ok(productService.getAllByIdList(idList))
    }

    @GetMapping("/access/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ProductDTO> {
        return ResponseEntity.ok(productService.getById(id))
    }

    @PostMapping("/access/search")
    fun search(@RequestBody productSearchRequest: ProductSearchRequest): ResponseEntity<Page<ProductDTO>> {
        return ResponseEntity.ok(productService.search(productSearchRequest))
    }

    @PutMapping("/{id}")
    fun updateProduct(@RequestBody product: ProductDTO, @PathVariable id: Long): ResponseEntity<Product> {
        return ResponseEntity.ok(productService.updateProduct(product, id))
    }

    @PatchMapping("/{id}")
    fun updateProductStatus(@PathVariable id: Long): ResponseEntity<Product> {
        return ResponseEntity.ok(productService.updateProductStatus(id))
    }
}