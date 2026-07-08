package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.TipoDemandaCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.TipoDemandaEditarDTO;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.TipoDemanda;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
import br.udesc.edu.demandatech.model.exception.ValidacaoException;
import br.udesc.edu.demandatech.repository.TipoDemandaRepository;
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
public class TipoDemandaService {
    private TipoDemandaRepository tipoDemandaRepository;
    private Validator validator;

    private void validar(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ValidacaoException(violations.iterator().next().getMessage());
        }
    }

    public TipoDemanda criar(TipoDemandaCriarDTO tipoDemandaCriarDTO, Funcionario funcionario) {
        if (!funcionario.getAdmin()) { throw new PermissaoNegada(); }
        validar(tipoDemandaCriarDTO);
        TipoDemanda tipoDemanda = new TipoDemanda();
        BeanUtils.copyProperties(tipoDemandaCriarDTO, tipoDemanda);
        return tipoDemandaRepository.save(tipoDemanda);
    }

    public TipoDemanda atualizar(String id, TipoDemandaEditarDTO tipoDemandaEditarDTO, Funcionario funcionario) {
        if (!funcionario.getAdmin()) { throw new PermissaoNegada(); }
        validar(tipoDemandaEditarDTO);
        Optional<TipoDemanda> opt = tipoDemandaRepository.findById(id);
        if (opt.isPresent()) {
            TipoDemanda t = opt.get();
            BeanUtils.copyProperties(tipoDemandaEditarDTO, t);
            return tipoDemandaRepository.save(t);
        }
        throw new IdNaoEncontrado("tipo demanda", id);
    }

    public List<TipoDemanda> buscarTudo() { return tipoDemandaRepository.findAll(); }

    public TipoDemanda buscarPorId(String id) {
        Optional<TipoDemanda> opt = tipoDemandaRepository.findById(id);
        if (opt.isPresent()) { return opt.get(); }
        throw new IdNaoEncontrado("tipo demanda", id);
    }

    public void removerPorId(String id, Funcionario funcionario) {
        if (!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<TipoDemanda> opcionalTipoDemanda = tipoDemandaRepository.findById(id);
        if (opcionalTipoDemanda.isPresent()) {
            tipoDemandaRepository.deleteById(id);
            return;
        }
        throw new IdNaoEncontrado("tipo demanda", id);
    }
}
