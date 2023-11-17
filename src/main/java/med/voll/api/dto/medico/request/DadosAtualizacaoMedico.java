package med.voll.api.dto.medico.request;

import jakarta.validation.constraints.NotNull;
import med.voll.api.dto.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
                @NotNull Long id,
                String nome,
                String telefone,
                DadosEndereco endereco) {

}
