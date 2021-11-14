package uk.co.santander.marketprice.apicontext


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title
import uk.co.santander.marketprice.controller.PriceController

@Title("Application Specification")
@Narrative("Specification which beans are expected")
@SpringBootTest
class LoadContextTest extends Specification {

    @Autowired(required = false)
    private PriceController priceController


    def "when context is loaded then all expected beans are created"() {
        expect: "the PriceController is created"
        priceController
    }
}
