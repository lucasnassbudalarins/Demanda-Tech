package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.DepartamentoCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.DepartamentoEditarDTO;
import br.udesc.edu.demandatech.model.entity.Departamento;
import br.udesc.edu.demandatech.model.exception.IdNotFoud;
import br.udesc.edu.demandatech.repository.DepartamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartamentoService {
    private DepartamentoRepository departamentoRepository;

    public Departamento criarDepartamento(DepartamentoCriarDTO departamentoCriarDTO) {
        Departamento departamento = new Departamento();
        BeanUtils.copyProperties(departamentoCriarDTO,departamento);

        return departamento = departamentoRepository.save(departamento);
    }

    public Departamento atualizarDepartamento(Long id, DepartamentoEditarDTO departamentoEditarDTO) {
        Optional<Departamento> opcionalDepartamento = departamentoRepository.findById(id);
        if(opcionalDepartamento.isPresent()) {
            Departamento departamento = opcionalDepartamento.get();
            BeanUtils.copyProperties(departamentoEditarDTO, departamento);
            return departamentoRepository.save(departamento);
        }
        throw new IdNotFoud("departamento", id);
    }

    public Departamento buscarDepartamentoPorId(Departamento departamento, Long id) {

    }
}
