package com.example.imageservice



import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.util.AssertionErrors.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImageServiceApplicationTests() {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun healthCheck_OK() {
        val result = testRestTemplate.getForEntity("/actuator/health", String::class.java)
        assertNotNull("",result)
        assertEquals("",result?.statusCode, HttpStatus.OK)
        assertEquals("","{\"status\":\"UP\"}", result.body.toString())
    }

}


