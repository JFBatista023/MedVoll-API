package med.voll.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import med.voll.api.domain.entity.Medico;
import med.voll.api.domain.enums.Especialidade;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("select m from Medico m where m.ativo = true and m.especialidade = :especialidade and m.id not in (select c.medico.id from Consulta c where c.data = :data) order by rand()")
    List<Medico> escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data, Pageable pageable);

    @Query("""
            select m.ativo from Medico m
            where m.id = :id
            """)
    Boolean findAtivoById(Long id);

}
