package med.voll.api.dto.medico.response;

import med.voll.api.domain.embeddable.Endereco;
import med.voll.api.domain.entity.Medico;
import med.voll.api.domain.enums.Especialidade;

public record DadosDetalhamentoMedico(
        Long id,
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especialidade,
        Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(),
                medico.getEspecialidade(),
                medico.getEndereco());
    }

}
