package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.FuncionarioEnvolvido;
import br.udesc.edu.demandatech.model.entity.FuncionarioEnvolvidoId;
import br.udesc.edu.demandatech.repository.FuncionarioEnvolvidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FuncionarioEnvolvidoService {
    private FuncionarioEnvolvidoRepository funcionarioEnvolvidoRepository;

    public FuncionarioEnvolvido associar(Funcionario funcionario, Demanda demanda) {
        FuncionarioEnvolvido fe = new FuncionarioEnvolvido(funcionario, demanda);
        return funcionarioEnvolvidoRepository.save(fe);
    }

    public List<FuncionarioEnvolvido> buscarPorDemanda(Demanda demanda) {
        return funcionarioEnvolvidoRepository.findByDemanda(demanda);
    }

    public List<FuncionarioEnvolvido> buscarTudo() {
        return funcionarioEnvolvidoRepository.findAll();
    }

    public void remover(Funcionario funcionario, Demanda demanda) {
        FuncionarioEnvolvidoId id = new FuncionarioEnvolvidoId(
                funcionario.getMatricula(), demanda.getIdDemanda());
        if (!funcionarioEnvolvidoRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado no processo.");
        }
        funcionarioEnvolvidoRepository.deleteById(id);
    }
}
