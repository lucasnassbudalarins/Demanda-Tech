package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.PrioridadeCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.PrioridadeEditarDTO;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Prioridade;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
import br.udesc.edu.demandatech.model.exception.ValidacaoException;
import br.udesc.edu.demandatech.repository.PrioridadeRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PrioridadeService {
    private PrioridadeRepository prioridadeRepository;
    private Validator validator;

    private void validar(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ValidacaoException(violations.iterator().next().getMessage());
        }
    }

    public Prioridade criar(PrioridadeCriarDTO prioridadeCriarDTO, Funcionario funcionario) {
        if (!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        validar(prioridadeCriarDTO);
        Prioridade prioridade = new Prioridade();
        BeanUtils.copyProperties(prioridadeCriarDTO, prioridade);
        return prioridadeRepository.save(prioridade);
    }

    public Prioridade atualizar(String id, PrioridadeEditarDTO prioridadeEditarDTO, Funcionario funcionario) {
        if (!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        validar(prioridadeEditarDTO);
        Optional<Prioridade> opcionalPrioridade = prioridadeRepository.findById(id);
        if (opcionalPrioridade.isPresent()) {
            Prioridade prioridade = opcionalPrioridade.get();
            BeanUtils.copyProperties(prioridadeEditarDTO, prioridade);
            return prioridadeRepository.save(prioridade);
        }
        throw new IdNaoEncontrado("prioridade", id);
    }

    public List<Prioridade> buscarTudo() {
        return prioridadeRepository.findAll();
    }

    public Prioridade buscarPorId(String id) {
        Optional<Prioridade> opcionalPrioridade = prioridadeRepository.findById(id);
        if (opcionalPrioridade.isPresent()) {
            return opcionalPrioridade.get();
        }
        throw new IdNaoEncontrado("prioridade", id);
    }

    public void removerPorId(String id, Funcionario funcionario) {
        if (!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<Prioridade> optionalPrioridade = prioridadeRepository.findById(id);
        if (optionalPrioridade.isPresent()) {
            prioridadeRepository.deleteById(id);
            return;
        }
        throw new IdNaoEncontrado("prioridade", id);
    }
}
