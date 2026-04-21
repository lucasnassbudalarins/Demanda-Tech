package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.TipoDemandaCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.TipoDemandaEditarDTO;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.TipoDemanda;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
import br.udesc.edu.demandatech.model.exception.ReferenciaChaveEstrangeira;
import br.udesc.edu.demandatech.repository.TipoDemandaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TipoDemandaService {
    private TipoDemandaRepository tipoDemandaRepository;

    public TipoDemanda criar(TipoDemandaCriarDTO tipoDemandaCriarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) { throw new PermissaoNegada(); }
        TipoDemanda tipoDemanda = new TipoDemanda();
        BeanUtils.copyProperties(tipoDemandaCriarDTO, tipoDemanda);
        return tipoDemandaRepository.save(tipoDemanda);
    }

    public TipoDemanda atualizar(Long id, TipoDemandaEditarDTO tipoDemandaEditarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) { throw new PermissaoNegada(); }
        Optional<TipoDemanda> opt = tipoDemandaRepository.findById(id);
        if(opt.isPresent()) {
            TipoDemanda t = opt.get();
            BeanUtils.copyProperties(tipoDemandaEditarDTO, t);
            return tipoDemandaRepository.save(t);
        }
        throw new IdNaoEncontrado("tipo demanda", id);
    }

    public List<TipoDemanda> buscarTudo() { return tipoDemandaRepository.findAll(); }

    public TipoDemanda buscarPorId(Long id) {
        Optional<TipoDemanda> opt = tipoDemandaRepository.findById(id);
        if(opt.isPresent()) { return opt.get(); }
        throw new IdNaoEncontrado("tipo demanda", id);
    }

    public void removerPorId(Long id, Funcionario funcionario){
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<TipoDemanda> opcionalTipoDemanda = tipoDemandaRepository.findById(id);
        if(opcionalTipoDemanda.isPresent()) {
            try {
                tipoDemandaRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new ReferenciaChaveEstrangeira();
            }
            return;
        }
        throw new IdNaoEncontrado("tipo demanda", id);
    }
}
