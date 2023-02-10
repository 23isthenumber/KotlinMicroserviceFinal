import com.kotlin.productservice.ProductserviceApplication
import org.springframework.boot.test.context.SpringBootTest
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc

@SpringBootTest(
    classes = [ProductserviceApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@CucumberContextConfiguration
class SpringContextConfiguration