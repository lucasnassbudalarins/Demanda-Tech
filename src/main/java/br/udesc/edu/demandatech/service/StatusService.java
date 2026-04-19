package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.StatusCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.StatusEditarDTO;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Status;
import br.udesc.edu.demandatech.model.exception.IdNotFound;
import br.udesc.edu.demandatech.model.exception.PermissionDenied;
import br.udesc.edu.demandatech.repository.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatusService {
    private StatusRepository statusRepository;

    public Status criar(StatusCriarDTO statusCriarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissionDenied();
        }
        Status status = new Status();
        BeanUtils.copyProperties(statusCriarDTO,status);

        return status = statusRepository.save(status);
    }

    public Status atualizar(Long id, StatusEditarDTO statusEditarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissionDenied();
        }
        Optional<Status> opcionalStatus = statusRepository.findById(id);
        if(opcionalStatus.isPresent()) {
            Status status = opcionalStatus.get();
            BeanUtils.copyProperties(statusEditarDTO, status);
            return statusRepository.save(status);
        }
        throw new IdNotFound("status", id);
    }

    public List<Status> buscarTudo() {
        return statusRepository.findAll();
    }

    public Status buscarPorId(Status status, Long id) {
        Optional<Status> opcionalStatus = statusRepository.findById(id);
        if(opcionalStatus.isPresent()) {
            return opcionalStatus.get();
        }
        throw new IdNotFound("status", id);
    }

    public void removerPorId(Long id, Funcionario funcionario){
        if(!funcionario.getAdmin()) {
            throw new PermissionDenied();
        }
        Optional<Status> optionalStatus = statusRepository.findById(id);
        if(optionalStatus.isPresent()) {
            statusRepository.deleteById(id);
        }
        throw new IdNotFound("status", id);
    }
}
