package med.voll.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import med.voll.api.domain.entity.Consulta;
import med.voll.api.domain.entity.Medico;
import med.voll.api.domain.entity.Paciente;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;

@Service
public class ConsultaService {

    private ConsultaRepository consultaRepository;

    private MedicoRepository medicoRepository;

    private PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public void agendar(DadosAgendamentoConsulta dados) {
        Consulta consulta;

        Optional<Paciente> paciente = pacienteRepository.findById(dados.idPaciente());

        if (paciente.isEmpty()) {
            throw new ValidacaoException("Paciente inexistente.");
        }

        if (dados.idMedico() != null) {
            Optional<Medico> medico = medicoRepository.findById(dados.idMedico());
            if (medico.isEmpty()) {
                throw new ValidacaoException("Médico inexistente.");
            }

            consulta = new Consulta(null, medico.get(), paciente.get(), dados.data());
        } else {
            Medico medico = escolherMedico(dados);
            consulta = new Consulta(null, medico, paciente.get(), dados.data());
        }

        consultaRepository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() != null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido.");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
