package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.DemandaCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.DemandaEditarDTO;
import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.FuncionarioEnvolvido;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.SemFuncionarioDisponivel;
import br.udesc.edu.demandatech.repository.DemandaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DemandaService {
    private DemandaRepository demandaRepository;

    public Demanda criar(DemandaCriarDTO demandaCriarDTO){
        Demanda demanda = new Demanda();
        BeanUtils.copyProperties(demandaCriarDTO,demanda);

        Optional<Funcionario> responsavel = demandaRepository.findFuncionarioComMenosDemandas(
                demandaCriarDTO.tipo().getDepartamento().getIdDepartamento()
        );
        if(responsavel.isEmpty()) throw new SemFuncionarioDisponivel();

        demanda.setData(LocalDate.now());
        demanda.setHora(LocalTime.now());
        demanda.setResponsavel(responsavel.get());

        demanda = demandaRepository.save(demanda);

        for (Funcionario funcionario : demandaCriarDTO.usuariosEnvolvidos())
            new FuncionarioEnvolvido(funcionario, demanda);

        return demanda;
    }

    public Demanda atualizar(Long id, DemandaEditarDTO demandaEditarDTO){
        Optional<Demanda> optionalDemanda = demandaRepository.getDemandasByIdAndUsuario(id, demandaEditarDTO.criador());
        if(optionalDemanda.isPresent()){
            Demanda demanda = optionalDemanda.get();
            BeanUtils.copyProperties(demandaEditarDTO,demanda);
            return demandaRepository.save(demanda);
        }
        throw new IdNaoEncontrado("demandas", id);
    }

    public List<Demanda> buscarTudo(){
        return demandaRepository.findAll();
    }

    public Demanda buscarPorId(Long id){
        Optional<Demanda> optionalDemanda = demandaRepository.findById(id);
        if(optionalDemanda.isPresent()) {
            return optionalDemanda.get();
        }
        throw new IdNaoEncontrado("demandas", id);
    }

    public void removerPorId(Funcionario funcionario, Long id){
        Optional<Demanda> optionalDemanda = demandaRepository.getDemandasByIdAndUsuario(id, funcionario);
        if(optionalDemanda.isPresent()) {
            demandaRepository.deleteById(id);
        }
        throw new IdNaoEncontrado("demandas", id);
    }

}
