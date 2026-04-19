package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.TipoDemanda;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
import br.udesc.edu.demandatech.repository.TipoDemandaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TipoDemandaService {
    private TipoDemandaRepository tipoDemandaRepository;

    public TipoDemanda criar(TipoDemandaCriarDTO tipoDemandaCriarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        TipoDemanda tipoDemanda = new TipoDemanda();
        BeanUtils.copyProperties(tipoDemandaCriarDTO,tipoDemanda);

        return tipoDemanda = tipoDemandaRepository.save(tipoDemanda);
    }

    public TipoDemanda atualizar(Long id, TipoDemandaEditarDTO tipoDemandaEditarDTO, Funcionario funcionario) {
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<TipoDemanda> opcionalTipoDemanda = tipoDemandaRepository.findById(id);
        if(opcionalTipoDemanda.isPresent()) {
            TipoDemanda tipoDemanda = opcionalTipoDemanda.get();
            BeanUtils.copyProperties(tipoDemandaEditarDTO, tipoDemanda);
            return tipoDemandaRepository.save(tipoDemanda);
        }
        throw new IdNaoEncontrado("tipo Demanda", id);
    }

    public List<TipoDemanda> buscarTudo() {
        return tipoDemandaRepository.findAll();
    }

    public TipoDemanda buscarPorId(TipoDemanda tipoDemanda, Long id) {
        Optional<TipoDemanda> opcionalTipoDemanda = tipoDemandaRepository.findById(id);
        if(opcionalTipoDemanda.isPresent()) {
            return opcionalTipoDemanda.get();
        }
        throw new IdNaoEncontrado("tipo Demanda", id);
    }

    public void removerPorId(Long id, Funcionario funcionario){
        if(!funcionario.getAdmin()) {
            throw new PermissaoNegada();
        }
        Optional<TipoDemanda> opcionalTipoDemanda = tipoDemandaRepository.findById(id);
        if(opcionalTipoDemanda.isPresent()) {
            tipoDemandaRepository.deleteById(id);
        }
        throw new IdNaoEncontrado("tipo Demanda", id);
    }
}
