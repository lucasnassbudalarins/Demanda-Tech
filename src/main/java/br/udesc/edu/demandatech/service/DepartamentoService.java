package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.DepartamentoCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.DepartamentoEditarDTO;
import br.udesc.edu.demandatech.model.entity.Departamento;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.exception.IdNotFound;
import br.udesc.edu.demandatech.model.exception.PermissionDenied;
import br.udesc.edu.demandatech.repository.DepartamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartamentoService {
    private DepartamentoRepository departamentoRepository;

    public Departamento criar(DepartamentoCriarDTO departamentoCriarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissionDenied();
        }
        Departamento departamento = new Departamento();
        BeanUtils.copyProperties(departamentoCriarDTO,departamento);

        return departamentoRepository.save(departamento);
    }

    public Departamento atualizar(Long id, DepartamentoEditarDTO departamentoEditarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissionDenied();
        }
        Optional<Departamento> opcionalDepartamento = departamentoRepository.findById(id);
        if(opcionalDepartamento.isPresent()) {
            Departamento departamento = opcionalDepartamento.get();
            BeanUtils.copyProperties(departamentoEditarDTO, departamento);
            return departamentoRepository.save(departamento);
        }
        throw new IdNotFound("departamento", id);
    }

    public List<Departamento> buscarTudo() {
        return departamentoRepository.findAll();
    }

    public Departamento buscarPorId(Long id) {
        Optional<Departamento> opcionalDepartamento = departamentoRepository.findById(id);
        if(opcionalDepartamento.isPresent()) {
            return opcionalDepartamento.get();
        }
        throw new IdNotFound("departamento", id);
    }

    public Departamento buscarPorName(String descricao) {
        Optional<Departamento> opcionalDepartamento = departamentoRepository.getDepartamentoByDescricao(descricao);
        if(opcionalDepartamento.isPresent()) {
            return opcionalDepartamento.get();
        }
        throw new IdNotFound("departamento", id);
    }

    public void removerPorId(Long id, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissionDenied();
        }
        Optional<Departamento> optionalDepartamento = departamentoRepository.findById(id);
        if(optionalDepartamento.isPresent()) {
            departamentoRepository.deleteById(id);
        }
        throw new IdNotFound("departamento", id);
    }
}
