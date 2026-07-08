package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.FuncionarioCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.FuncionarioEditarDTO;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.exception.*;
import br.udesc.edu.demandatech.repository.FuncionarioRepository;
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
public class FuncionarioService {
    private FuncionarioRepository funcionarioRepository;
    private Validator validator;

    private void validar(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ValidacaoException(violations.iterator().next().getMessage());
        }
    }

    private void validarEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidacaoException("Campo email está em formato inválido");
        }
    }

    public Funcionario criar(FuncionarioCriarDTO dto, Funcionario funcionario) {
        if (!funcionario.getAdmin())
            throw new PermissaoNegada();
        validar(dto);
        validarEmail(dto.email());
        Funcionario novo = new Funcionario();
        BeanUtils.copyProperties(dto, novo);
        return funcionarioRepository.save(novo);
    }

    public Funcionario atualizar(String id, FuncionarioEditarDTO dto, Funcionario funcionario) {
        if (!funcionario.getAdmin())
            throw new PermissaoNegada();
        validar(dto);
        validarEmail(dto.email());
        Optional<Funcionario> opt = funcionarioRepository.findById(id);
        if (opt.isPresent()) {
            BeanUtils.copyProperties(dto, opt.get());
            return funcionarioRepository.save(opt.get());
        }
        throw new IdNaoEncontrado("funcionário", id);
    }

    public List<Funcionario> buscarTudo() {
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarPorId(String id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new IdNaoEncontrado("funcionário", id));
    }

    public void removerPorId(String id, Funcionario funcionario) {
        if (!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if (optionalFuncionario.isPresent()) {
            funcionarioRepository.deleteById(id);
            return;
        }
        throw new IdNaoEncontrado("funcionário", id);
    }

    public Funcionario login(String matricula, String senha) {
        return funcionarioRepository.findByMatriculaAndSenha(matricula, senha)
                .orElseThrow(PermissaoLogin::new);
    }
}