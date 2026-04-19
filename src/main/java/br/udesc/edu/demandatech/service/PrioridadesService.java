package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.DepartamentoCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.DepartamentoEditarDTO;
import br.udesc.edu.demandatech.model.entity.Departamento;
import br.udesc.edu.demandatech.model.entity.Prioridade;
import br.udesc.edu.demandatech.model.exception.IdNotFound;
import br.udesc.edu.demandatech.repository.DepartamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrioridadesService {
    private DepartamentoRepository departamentoRepository;

    public Prioridade criar(DepartamentoCriarDTO departamentoCriarDTO) {
        Prioridade prioridade = new Departamento();
        BeanUtils.copyProperties(departamentoCriarDTO,prioridade);

        return prioridade = departamentoRepository.save(prioridade);
    }

    public Prioridade atualizar(Long id, DepartamentoEditarDTO departamentoEditarDTO) {
        Optional<Departamento> opcionalPrioridade = departamentoRepository.findById(id);
        if(opcionalPrioridade.isPresent()) {
            Prioridade departamento = opcionalPrioridade.get();
            BeanUtils.copyProperties(departamentoEditarDTO, departamento);
            return departamentoRepository.save(departamento);
        }
        throw new IdNotFound("departamento", id);
    }

    public List<Departamento> buscarTudo() {
        return departamentoRepository.findAll();
    }

    public Prioridade buscarPorId(Prioridade departamento, Long id) {
        Optional<Departamento> opcionalPrioridade = departamentoRepository.findById(id);
        if(opcionalPrioridade.isPresent()) {
            return opcionalPrioridade.get();
        }
        throw new IdNotFound("prioridade", id);
    }

    public void removerPorId(Long id){
        Optional<Prioridade> optionalPrioridade = departamentoRepository.findById(id);
        if(optionalPrioridade.isPresent()) {
            departamentoRepository.deleteById(id);
        }
        throw new IdNotFound("prioridade", id);
    }
}
