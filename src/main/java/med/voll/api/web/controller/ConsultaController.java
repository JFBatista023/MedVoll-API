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

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
    }
}
