package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.FuncionarioCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.FuncionarioEditarDTO;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Usuario;
import br.udesc.edu.demandatech.model.exception.IdNotFoud;
import br.udesc.edu.demandatech.repository.FuncionarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FuncionarioService {
    private FuncionarioRepository funcionarioRepository;

    public Funcionario criarDemanda(FuncionarioCriarDTO funcionarioCriarDTO){
        Funcionario funcionario = new Funcionario();
        BeanUtils.copyProperties(funcionarioCriarDTO,funcionario);
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizarDemanda(Long id, FuncionarioEditarDTO funcionarioEditarDTO){
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if(optionalFuncionario.isPresent()){
            Funcionario funcionario = optionalFuncionario.get();
            BeanUtils.copyProperties(funcionarioEditarDTO,funcionario);
            return funcionarioRepository.save(funcionario);
        }
        throw new IdNotFoud("funcionario", id);
    }

    public List<Funcionario> buscarTodasDemandas(Usuario usuario){
        Empresa empresa = usuario.getEmpresa();
        return funcionarioRepository.getFuncionarioByEmpresa(empresa);
    }

    public Funcionario buscarDemandaPorId(Usuario usuario, Long id){
        Empresa empresa = usuario.getEmpresa();
        Optional<Funcionario> optionalDemanda = funcionarioRepository.getDemandaByEmpresa(id, empresa);
        if(optionalDemanda.isPresent()) {
            return optionalDemanda.get();
        }
        throw new IdNotFoud("funcionario", id);
    }

    public void removerDemandaPorId(Usuario usuario, Long id){
        Optional<Funcionario> optionalDemanda = funcionarioRepository.getDemandasByIdAndUsuario(id, usuario);
        if(optionalDemanda.isPresent()) {
            funcionarioRepository.deleteById(id);
        }
        throw new IdNotFoud("funcionario", id);
    }

}
