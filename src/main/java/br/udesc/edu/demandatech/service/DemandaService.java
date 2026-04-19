package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.DemandaCriarDTO;
import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.exception.IdNotFoud;
import br.udesc.edu.demandatech.repository.DemandaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DemandaService {
    private DemandaRepository demandaRepository;

    public Demanda criarDemanda(DemandaCriarDTO demandaCriarDTO){
        Demanda demanda = new Demanda();
        BeanUtils.copyProperties(demandaCriarDTO,demanda);
//      Adicionar na tabela associativa Demanda Pessoas
//        Adicionar Prioridades
//        Adicionar Tipo
//        Adicionar usuário
//        Aidicionar Funcionário (regra de negócio)

        return demandaRepository.save(demanda);
    }

    public Demanda atualizarDemanda(Long id, DemandaCriarDTO demandaCriarDTO){
        Optional<Demanda> optionalDemanda = demandaRepository.findById(id);
        if(optionalDemanda.isPresent()){
            Demanda demanda = optionalDemanda.get();
            BeanUtils.copyProperties(demandaCriarDTO,demanda);
            return demandaRepository.save(demanda);
        }
        throw new IdNotFoud("demandas", id);
    }

    public List<Demanda> buscarTodasDemandas(){
        return demandaRepository.findAll();
    }

    public Optional<Demanda> buscarDemandaPorId(Long id){
        return demandaRepository.findById(id);
    }

    public void removerDemandaPorId(Long id){
        demandaRepository.deleteById(id);
    }

}
