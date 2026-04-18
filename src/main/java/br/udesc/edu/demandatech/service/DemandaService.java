package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.repository.DemandaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DemandaService {
    private DemandaRepository demandaRepository;

    public Demanda criarDemanda(DemandaDTO demandaDTO){

    }
}
