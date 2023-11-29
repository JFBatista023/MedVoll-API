package med.voll.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import med.voll.api.domain.entity.Consulta;
import med.voll.api.domain.entity.Medico;
import med.voll.api.domain.entity.Paciente;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.dto.consulta.DadosDetalhamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.interfaces.ValidadorAgendamentoDeConsultas;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;

@Service
public class ConsultaService {

    private ConsultaRepository consultaRepository;

    private MedicoRepository medicoRepository;

    private PacienteRepository pacienteRepository;

    private List<ValidadorAgendamentoDeConsultas> validadores;

    public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository, List<ValidadorAgendamentoDeConsultas> validadores) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadores = validadores;
    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
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

        validadores.forEach(validador -> validador.validar(dados));

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() != null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido.");
        }

        Pageable pageable = PageRequest.of(0, 1);
        List<Medico> medicos = medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data(),
                pageable);

        if (medicos.isEmpty()) {
            throw new ValidacaoException("Não tem nenhum médico da especialidade com horário disponível.");
        }

        return medicos.get(0);
    }
}
