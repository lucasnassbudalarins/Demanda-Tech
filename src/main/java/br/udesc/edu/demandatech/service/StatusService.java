package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.StatusCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.StatusEditarDTO;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Status;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
import br.udesc.edu.demandatech.model.exception.ReferenciaChaveEstrangeira;
import br.udesc.edu.demandatech.model.exception.ValidacaoException;
import br.udesc.edu.demandatech.repository.StatusRepository;
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
public class StatusService {
    private StatusRepository statusRepository;
    private Validator validator;

    private void validar(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ValidacaoException(violations.iterator().next().getMessage());
        }
    }

    public Status criar(StatusCriarDTO statusCriarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        validar(statusCriarDTO);
        Status status = new Status();
        BeanUtils.copyProperties(statusCriarDTO, status);
        return statusRepository.save(status);
    }

    public Status atualizar(Long id, StatusEditarDTO statusEditarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        validar(statusEditarDTO);
        Optional<Status> opcionalStatus = statusRepository.findById(id);
        if(opcionalStatus.isPresent()) {
            Status status = opcionalStatus.get();
            BeanUtils.copyProperties(statusEditarDTO, status);
            return statusRepository.save(status);
        }
        throw new IdNaoEncontrado("status", id);
    }

    public List<Status> buscarTudo() {
        return statusRepository.findAll();
    }

    public Status buscarPorId(Long id) {
        Optional<Status> opcionalStatus = statusRepository.findById(id);
        if(opcionalStatus.isPresent()) {
            return opcionalStatus.get();
        }
        throw new IdNaoEncontrado("status", id);
    }

    public void removerPorId(Long id, Funcionario funcionario){
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<Status> optionalStatus = statusRepository.findById(id);
        if(optionalStatus.isPresent()) {
            try {
                statusRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new ReferenciaChaveEstrangeira();
            }
            return;
        }
        throw new IdNaoEncontrado("status", id);
    }
}
