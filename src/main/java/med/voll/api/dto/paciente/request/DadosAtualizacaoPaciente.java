package med.voll.api.dto.paciente.request;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.embeddable.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}