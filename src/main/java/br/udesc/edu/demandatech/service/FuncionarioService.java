package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.FuncionarioCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.FuncionarioEditarDTO;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Usuario;
import br.udesc.edu.demandatech.model.exception.IdNotFound;
import br.udesc.edu.demandatech.repository.FuncionarioRepository;
import br.udesc.edu.demandatech.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FuncionarioService {
    private FuncionarioRepository funcionarioRepository;
    private UsuarioRepository usuarioRepository;

    public Funcionario criar(FuncionarioCriarDTO funcionarioCriarDTO, Usuario usuario){
        Funcionario funcionario = new Funcionario();
        BeanUtils.copyProperties(funcionarioCriarDTO,funcionario);
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizar(Long id, FuncionarioEditarDTO funcionarioEditarDTO, Usuario usuario){
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if(optionalFuncionario.isPresent()){
            Funcionario funcionario = optionalFuncionario.get();
            BeanUtils.copyProperties(funcionarioEditarDTO,funcionario);
            return funcionarioRepository.save(funcionario);
        }
        throw new IdNotFound("funcionario", id);
    }

    public List<Funcionario> buscarTudo(){
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarPorId(Long id){
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if(optionalFuncionario.isPresent()) {
            return optionalFuncionario.get();
        }
        throw new IdNotFound("funcionario", id);
    }

    public void removerPorId(Usuario usuario, Long id){
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id, usuario);
        if(optionalFuncionario.isPresent()) {
            funcionarioRepository.deleteById(id);
        }
        throw new IdNotFound("funcionario", id);
    }
}