package ustudio.task.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath


@SpringBootTest
@AutoConfigureMockMvc
internal class CountryLocalizationControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @Test
    fun check_for_connection() {
        mockMvc.perform(get("/countries/UA?lang=ru")).andDo(print()).andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Украина"))
        mockMvc.perform(get("/countries/UA?lang=uk")).andDo(print()).andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Украiна"))
        mockMvc.perform(get("/countries/RU?lang=en")).andDo(print()).andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Russia"))
    }
}
