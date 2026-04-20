package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.FuncionarioCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.FuncionarioEditarDTO;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
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

    public Funcionario criar(FuncionarioCriarDTO funcionarioCriarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Funcionario novoFuncionario = new Funcionario();
        BeanUtils.copyProperties(funcionarioCriarDTO, novoFuncionario);
        return funcionarioRepository.save(novoFuncionario);
    }

    public Funcionario atualizar(Long id, FuncionarioEditarDTO funcionarioEditarDTO, Funcionario funcionario){
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if(optionalFuncionario.isPresent()){
            Funcionario atualizarFuncionario = optionalFuncionario.get();
            BeanUtils.copyProperties(funcionarioEditarDTO, atualizarFuncionario);
            return funcionarioRepository.save(atualizarFuncionario);
        }
        throw new IdNaoEncontrado("funcionário", id);
    }

    public List<Funcionario> buscarTudo(){
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarPorId(Long id){
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if(optionalFuncionario.isPresent()) {
            return optionalFuncionario.get();
        }
        throw new IdNaoEncontrado("funcionário", id);
    }

    public void removerPorId(Long id, Funcionario funcionario){
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if(optionalFuncionario.isPresent()) {
            funcionarioRepository.deleteById(id);
            return;
        }
        throw new IdNaoEncontrado("funcionário", id);
    }
}