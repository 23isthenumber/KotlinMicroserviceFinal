package com.kotlin.productservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.productservice.exception.not_found.ProductNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
internal class ProductControllerIntegrationTests(
    @Autowired val mockMvc: MockMvc
    ) {

    private val BASE_URL : String = "/api/product"
    private val objectMapper: ObjectMapper = ObjectMapper()
    private val getAllByIdList_response : String = File("./src/test/resources/testdata/getProductsFromIdList/getAllByIdList_response.json").readText(Charsets.UTF_8)
    private val getProductById_response : String = File("./src/test/resources/testdata/getProductById/getProductById_response.json").readText()
    private val getAllBodyPageInfo_request : String = File("./src/test/resources/testdata/searchProductsWithFilters/getAllBodyPageInfo_request.json").readText()
    private val getAllProductPageInfo_response : String = File("./src/test/resources/testdata/searchProductsWithFilters/getAllProductPageInfo_response.json").readText()
    private val getAllWithNameAndPageInfo_request : String = File("./src/test/resources/testdata/searchProductsWithFilters/getAllWithNameAndPageInfo_request.json").readText()
    private val getAllWithNameAndPageInfo_response : String = File("./src/test/resources/testdata/searchProductsWithFilters/getAllWithNameAndPageInfo_response.json").readText()
    private val updateProductStatus_response : String = File("./src/test/resources/testdata/updateProductStatus/updateProductStatus_response.json").readText()
    private val updateProduct_request : String = File("./src/test/resources/testdata/updateProduct/updateProduct_request.json").readText()
    private val updateProduct_response : String = File("./src/test/resources/testdata/updateProduct/updateProduct_response.json").readText()

    @Test
    @Sql(scripts = ["classpath:data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun getActiveProductsIds_whenActiveProducts_shouldReturnThem() {
        //given:
        val contentToBeSent: MutableList<Int> = mutableListOf(1, 2, 3, 4)
        val expectedContent: String = "[1, 2, 3]"

        //when:
        mockMvc.perform(
            post(BASE_URL)
                .content(objectMapper.writeValueAsString(contentToBeSent))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            //then:
            .andExpect(status().isOk)
            .andExpect(content().json(expectedContent))
    }

    @Test
    @Sql(scripts = ["classpath:data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun getActiveProductsIds_noActiveIdsRequest_shouldReturnEmptyList() {
        //given:
        val contentToBeSent: MutableList<Int> = mutableListOf(4)
        val expectedContent: String = "[]"

        //when:
        mockMvc.perform(
            post(BASE_URL)
                .content(objectMapper.writeValueAsString(contentToBeSent))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

            //then:
            .andExpect(status().isOk)
            .andExpect(content().json(expectedContent))
    }

    @Test
    @Sql(scripts = ["classpath:data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun getAllByIdList_shouldReturnProductsWithGivenIds() {
        //given:
        val contentToBeSent: MutableList<Int> = mutableListOf(1, 3)
        val expectedContent: String = getAllByIdList_response

        //when:
        mockMvc.perform(
            post(BASE_URL + "/info")
                .content(objectMapper.writeValueAsString(contentToBeSent))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            //then:
            .andExpect(status().isOk)
            .andExpect(content().json(expectedContent))
    }

    @Test
    @Sql(scripts = ["classpath:data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun getAllByIdList_requestWithNonExistingId_throwsProductNotFoundException() {
        //given:
        val contentToBeSent: MutableList<Int> = mutableListOf(1, 100)
        //when:
        mockMvc.perform(
            post(BASE_URL + "/info")
                .content(objectMapper.writeValueAsString(contentToBeSent))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

            //then:
            .andExpect(status().isNotFound)
            .andExpect { result -> assertTrue(result.resolvedException is ProductNotFoundException) }
            .andExpect { result ->
                assertEquals(
                    "Product with id: 100 does not exist",
                    Objects.requireNonNull(result.resolvedException?.message)
                )
            }
    }

    @Test
    @Sql(scripts = ["classpath:data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun getById_existingId_shouldReturnProduct() {
        //given:
        val id : Long = 1
        val expectedContent: String = getProductById_response

        //when:
        mockMvc.perform(
            get(BASE_URL + "/access/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            //then:
            .andExpect(status().isOk)
            .andExpect(content().json(expectedContent))
    }

    @Test
    @Sql(scripts = ["classpath:data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun getById_nonExistingId_throwProductNotFoundException() {
        //given:
        val id : Long = 100

        //when:
        mockMvc.perform(
            get(BASE_URL + "/access/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            //then:
            .andExpect(status().isNotFound)
            .andExpect { result -> assertTrue(result.resolvedException is ProductNotFoundException) }
            .andExpect { result ->
                assertEquals(
                    "Product with id: 100 does not exist",
                    Objects.requireNonNull(result.resolvedException?.message)
                )
            }
    }

    @Test
    @Sql(scripts = ["classpath:data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun updateProduct_existingId_shouldUpdateProduct() {
        //given:
        val id : Long = 1
        val contentToBeSent : String = updateProduct_request
        val expectedContent: String = updateProduct_response

        //when:
        mockMvc.perform(
            put(BASE_URL + "/{id}", id)
                .content(contentToBeSent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            //then:
            .andExpect(status().isOk)
            .andExpect(content().json(expectedContent))
    }

    @Test
    @Sql(scripts = ["classpath:data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun updateProduct_nonExistingId_throwProductNotFoundException() {
        //given:
        val id : Long = 100
        val contentToBeSent : String = updateProduct_request
        //when:
        mockMvc.perform(
            put(BASE_URL + "//{id}", id)
                .content(contentToBeSent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            //then:
            .andExpect(status().isNotFound)
            .andExpect { result -> assertTrue(result.resolvedException is ProductNotFoundException) }
            .andExpect { result ->
                assertEquals(
                    "Product with id: 100 does not exist",
                    Objects.requireNonNull(result.resolvedException?.message)
                )
            }
    }

    @Test
    @Sql(scripts = ["classpath:data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun updateProductStatus_existingId_shouldChangeStatus() {
        //given:
        val id : Long = 2
        val expectedContent: String = updateProductStatus_response

        //when:
        mockMvc.perform(
            patch(BASE_URL + "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            //then:
            .andExpect(status().isOk)
            .andExpect(content().json(expectedContent))
    }

    @Test
    @Sql(scripts = ["classpath:data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun updateProductStatus_nonExistingId_throwProductNotFoundException() {
        //given:
        val id : Long = 100
        //when:
        mockMvc.perform(
            patch(BASE_URL + "//{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            //then:
            .andExpect(status().isNotFound)
            .andExpect { result -> assertTrue(result.resolvedException is ProductNotFoundException) }
            .andExpect { result ->
                assertEquals(
                    "Product with id: 100 does not exist",
                    Objects.requireNonNull(result.resolvedException?.message)
                )
            }
    }
    //search all products when empty criteria
    //search some by criteria
}