package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.repository.DemandaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FuncionarioEnvolvidoService {
    private DemandaRepository demandaRepository;
    private FuncionarioService funcionarioService;

    /**
     * Associa um funcionario a uma demanda adicionando sua matricula ao array.
     */
    public void associar(Funcionario funcionario, Demanda demanda) {
        if (demanda.getMatriculasEnvolvidos() == null) {
            demanda.setMatriculasEnvolvidos(new ArrayList<>());
        } else if (!(demanda.getMatriculasEnvolvidos() instanceof ArrayList)) {
            demanda.setMatriculasEnvolvidos(new ArrayList<>(demanda.getMatriculasEnvolvidos()));
        }
        
        if (!demanda.getMatriculasEnvolvidos().contains(funcionario.getMatricula())) {
            demanda.getMatriculasEnvolvidos().add(funcionario.getMatricula());
            demandaRepository.save(demanda);
        }
    }

    /**
     * Retorna os Funcionarios envolvidos em uma demanda,
     * buscando cada um na colecao "funcionarios" pela matricula.
     */
    public List<Funcionario> buscarPorDemanda(Demanda demanda) {
        if (demanda.getMatriculasEnvolvidos() == null) return List.of();
        return demanda.getMatriculasEnvolvidos().stream()
                .map(funcionarioService::buscarPorId)
                .toList();
    }

    /**
     * Remove a associacao de um funcionario com uma demanda.
     */
    public void remover(Funcionario funcionario, Demanda demanda) {
        if (demanda.getMatriculasEnvolvidos() == null ||
                !demanda.getMatriculasEnvolvidos().contains(funcionario.getMatricula())) {
            throw new RuntimeException("Funcionário não está associado a esta demanda.");
        }
        
        if (!(demanda.getMatriculasEnvolvidos() instanceof ArrayList)) {
            demanda.setMatriculasEnvolvidos(new ArrayList<>(demanda.getMatriculasEnvolvidos()));
        }
        
        demanda.getMatriculasEnvolvidos().remove(funcionario.getMatricula());
        demandaRepository.save(demanda);
    }
}
