package med.voll.api.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.entity.Usuario;
import med.voll.api.dto.token.DadosTokenJWT;
import med.voll.api.dto.usuario.DadosAutenticacao;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private AuthenticationManager manager;

    private TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacao dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var auth = manager.authenticate(authToken);
        var tokenJWT = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
