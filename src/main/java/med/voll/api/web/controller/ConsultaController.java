package med.voll.api.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.dto.consulta.DadosDetalhamentoConsulta;
import med.voll.api.service.ConsultaService;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        consultaService.agendar(dados);
        return ResponseEntity.ok().build();
    }

}
