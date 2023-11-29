package med.voll.api.validations;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidadorHorarioFuncionamentoClinica {

    public void validar(DadosAgendamentoConsulta dados) {
        LocalDateTime data = dados.data();

        boolean isDomingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean antesDaAberturaDaClinica = data.getHour() < 7;
        boolean depoisDoEncerramentoDaClinica = data.getHour() > 18;

        if (isDomingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
            throw new ValidacaoException("Consulta fora do horário do funcionamento da clínica");
        }
    }
}
