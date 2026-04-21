package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.DepartamentoCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.DepartamentoEditarDTO;
import br.udesc.edu.demandatech.model.entity.Departamento;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.ItemNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
import br.udesc.edu.demandatech.model.exception.ReferenciaChaveEstrangeira;
import br.udesc.edu.demandatech.repository.DepartamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartamentoService {
    private DepartamentoRepository departamentoRepository;

    public Departamento criar(DepartamentoCriarDTO departamentoCriarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Departamento departamento = new Departamento();
        BeanUtils.copyProperties(departamentoCriarDTO, departamento);

        return departamentoRepository.save(departamento);
    }

    public Departamento atualizar(Long id, DepartamentoEditarDTO departamentoEditarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
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
