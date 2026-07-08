package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.EstornoDemandaCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.EstornoDemandaEditarDTO;
import br.udesc.edu.demandatech.model.dto.relatorio.QtdEstornosPorResponsavel;
import br.udesc.edu.demandatech.model.entity.EstornoDemanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
import br.udesc.edu.demandatech.model.exception.ValidacaoException;
import br.udesc.edu.demandatech.repository.EstornoDemandaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class EstornoDemandaService {
    private EstornoDemandaRepository estornoDemandaRepository;
    private Validator validator;

    private void validar(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ValidacaoException(violations.iterator().next().getMessage());
        }
    }

    public EstornoDemanda criar(EstornoDemandaCriarDTO estornoDemandaCriarDTO) {
        validar(estornoDemandaCriarDTO);
        EstornoDemanda estornoDemanda = new EstornoDemanda();
        BeanUtils.copyProperties(estornoDemandaCriarDTO, estornoDemanda);
        estornoDemanda.setData(LocalDate.now());
        return estornoDemandaRepository.save(estornoDemanda);
    }

    public EstornoDemanda atualizar(
            String id, EstornoDemandaEditarDTO estornoDemandaEditarDTO, Funcionario funcionario) {
        validar(estornoDemandaEditarDTO);
        Optional<EstornoDemanda> optionalEstornoDemanda = estornoDemandaRepository.findById(id);
        if (optionalEstornoDemanda.isPresent()) {
            EstornoDemanda atualizarEstornoDemanda = optionalEstornoDemanda.get();
            if (funcionario.getAdmin() ||
                    estornoDemandaRepository.funcionarioCanEdit(id, funcionario)) {
                BeanUtils.copyProperties(estornoDemandaEditarDTO, atualizarEstornoDemanda);
                return estornoDemandaRepository.save(atualizarEstornoDemanda);
            } else {
                throw new PermissaoNegada();
            }
        }
        throw new IdNaoEncontrado("estorno demanda", id);
    }

    public List<EstornoDemanda> buscarTudo() {
        return estornoDemandaRepository.findAll();
    }

    public EstornoDemanda buscarPorId(String id) {
        Optional<EstornoDemanda> optionalEstornoDemanda = estornoDemandaRepository.findById(id);
        if (optionalEstornoDemanda.isPresent()) {
            return optionalEstornoDemanda.get();
        }
        throw new IdNaoEncontrado("estorno demanda", id);
    }

    public void removerPorId(String id) {
        Optional<EstornoDemanda> optionalEstornoDemanda = estornoDemandaRepository.findById(id);
        if (optionalEstornoDemanda.isPresent()) {
            estornoDemandaRepository.deleteById(id);
            return;
        }
        throw new IdNaoEncontrado("estorno demanda", id);
    }

    public String relatorioQtdEstornosPorResponsavel() {
        List<QtdEstornosPorResponsavel> relatorio = estornoDemandaRepository.relatorioQtdEstornosPorResponsavel();
        String cabecalho = """
                --------------------------------------------------------------------
                *** RELATÓRIO: Quantidade de estornos por responsável da demanda ***
                --------------------------------------------------------------------
                """;
        String conteudo = "";
        for (QtdEstornosPorResponsavel item : relatorio) {
            conteudo += "| ID: " + item.getIdResponsavel() + " | Responsável: " + item.getResponsavel()
                    + " | Qtd. Estornos: " + item.getQtdEstornos() + " |\n";
        }
        String footer = "--------------------------------------------------------------------";
        return cabecalho + conteudo + footer;
    }
}