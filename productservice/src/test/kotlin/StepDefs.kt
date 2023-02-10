import com.fasterxml.jackson.databind.ObjectMapper
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.io.File

class StepDefs (@Autowired val mockMvc: MockMvc): SpringContextConfiguration() {
    private val BASE_URL : String = "/api/product"
    private val objectMapper: ObjectMapper = ObjectMapper()

    @Given("list of product ids both with active and inactive status")
    fun listOfProductIdsBothWithActiveAndInactiveStatus() : MutableList<Int> {
        val contentToBeSent: MutableList<Int> = mutableListOf(1, 2, 3, 4)
        return contentToBeSent
    }

    @When("I make a request")
    fun iMakeARequest(): MvcResult {
        return mockMvc.perform(
            post(BASE_URL)
                .content(objectMapper.writeValueAsString(listOfProductIdsBothWithActiveAndInactiveStatus()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andReturn()
    }

    @Then("response status is ok and request returns correct list of active products ids")
    fun responseStatusIsOkAndRequestReturnsCorrectListOfActiveProductsIds() {
         assert(iMakeARequest().response.status == 200)
         assert(iMakeARequest().response.contentAsString == "[1, 2, 3]")
    }
}