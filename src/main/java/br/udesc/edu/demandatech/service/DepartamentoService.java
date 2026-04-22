package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.DepartamentoCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.DepartamentoEditarDTO;
import br.udesc.edu.demandatech.model.entity.Departamento;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.exception.*;
import br.udesc.edu.demandatech.repository.DepartamentoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class DepartamentoService {
    private DepartamentoRepository departamentoRepository;
    private Validator validator;

    private void validar(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ValidacaoException(violations.iterator().next().getMessage());
        }
    }

    public Departamento criar(DepartamentoCriarDTO departamentoCriarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        validar(departamentoCriarDTO);
        Departamento departamento = new Departamento();
        BeanUtils.copyProperties(departamentoCriarDTO, departamento);
        return departamentoRepository.save(departamento);
    }

    public Departamento atualizar(Long id, DepartamentoEditarDTO departamentoEditarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        validar(departamentoEditarDTO);
        Optional<Departamento> opcionalDepartamento = departamentoRepository.findById(id);
        if(opcionalDepartamento.isPresent()) {
            Departamento departamento = opcionalDepartamento.get();
            BeanUtils.copyProperties(departamentoEditarDTO, departamento);
            return departamentoRepository.save(departamento);
        }
        throw new IdNaoEncontrado("departamento", id);
    }

    public List<Departamento> buscarTudo() {
        return departamentoRepository.findAll();
    }

    public Departamento buscarPorId(Long id) {
        Optional<Departamento> opcionalDepartamento = departamentoRepository.findById(id);
        if(opcionalDepartamento.isPresent()) {
            return opcionalDepartamento.get();
        }
        throw new IdNaoEncontrado("departamento", id);
    }

    public Departamento buscarPorName(String descricao) {
        Departamento departamento = departamentoRepository.getDepartamentoByDescricao(descricao);
        if(departamento != null) {
            return departamento;
        }
        throw new ItemNaoEncontrado(descricao);
    }

    public void removerPorId(Long id, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<Departamento> optionalDepartamento = departamentoRepository.findById(id);
        if(optionalDepartamento.isPresent()) {
            try {
                departamentoRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new ReferenciaChaveEstrangeira();
            }
            return;
        }
        throw new IdNaoEncontrado("departamento", id);
    }
}
