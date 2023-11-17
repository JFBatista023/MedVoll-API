package med.voll.api.dto.medico.request;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.embeddable.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {

}
