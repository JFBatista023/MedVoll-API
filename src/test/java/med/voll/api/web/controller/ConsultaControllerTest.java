package med.voll.api.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.enums.Especialidade;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.dto.consulta.DadosDetalhamentoConsulta;
import med.voll.api.service.ConsultaService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosACJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDCJson;

    @MockBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informações estão inválidas.")
    @WithMockUser
    void testAgendarCenario1() throws Exception {
        var response = mvc.perform(post("/consultas/agendar").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informações estão válidas.")
    @WithMockUser
    void testAgendarCenario2() throws Exception {
        LocalDateTime data = LocalDateTime.now().plusHours(1);
        Especialidade especialidade = Especialidade.CARDIOLOGIA;

        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);

        when(consultaService.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mvc
                .perform(post("/consultas/agendar").contentType(MediaType.APPLICATION_JSON)
                        .content(
                                dadosACJson.write(new DadosAgendamentoConsulta(2l, 5l, data, especialidade)).getJson()))
                .andReturn().getResponse();

        var json = dadosDCJson.write(dadosDetalhamento).getJson();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), json);
    }
}
