package med.voll.api.dto.paciente.response;

import med.voll.api.domain.embeddable.Endereco;
import med.voll.api.domain.entity.Paciente;

public record DadosDetalhamentoPaciente(
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {
    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getEndereco());
    }
}
