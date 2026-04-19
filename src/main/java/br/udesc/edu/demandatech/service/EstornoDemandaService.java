package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.EstornoDemandaCriarDTO;
import br.udesc.edu.demandatech.model.dto.criar.FuncionarioCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.FuncionarioEditarDTO;
import br.udesc.edu.demandatech.model.dto.relatorio.QtdEstornosPorResponsavel;
import br.udesc.edu.demandatech.model.entity.EstornoDemanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
import br.udesc.edu.demandatech.repository.EstornoDemandaRepository;
import br.udesc.edu.demandatech.repository.FuncionarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EstornoDemandaService {
    private EstornoDemandaRepository estornoDemandaRepository;

    public EstornoDemanda criar(EstornoDemandaCriarDTO estornoDemandaCriarDTO) {
        EstornoDemanda estornoDemanda = new EstornoDemanda();
        BeanUtils.copyProperties(estornoDemandaCriarDTO,estornoDemanda);
        return estornoDemandaRepository.save(estornoDemanda);
    }

    public EstornoDemanda atualizar(
            Long id, EstornoDemandaCriarDTO estornoDemandaCriarDTO, Funcionario funcionario
    ) {
        Optional<EstornoDemanda> optionalEstornoDemanda = estornoDemandaRepository.findById(id);
        if(optionalEstornoDemanda.isPresent()){
            EstornoDemanda atualizarEstornoDemanda = optionalEstornoDemanda.get();
            if (funcionario.getAdmin() ||
                    estornoDemandaRepository.funcionarioCanEdit(id, funcionario))
            {
                BeanUtils.copyProperties(estornoDemandaCriarDTO,atualizarEstornoDemanda);
                return estornoDemandaRepository.save(atualizarEstornoDemanda);
            } else {
                throw new PermissaoNegada();
            }
        }
        throw new IdNaoEncontrado("estorno demanda", id);
    }

    public List<EstornoDemanda> buscarTudo(){
        return estornoDemandaRepository.findAll();
    }

    public EstornoDemanda buscarPorId(Long id){
        Optional<EstornoDemanda> optionalEstornoDemanda = estornoDemandaRepository.findById(id);
        if(optionalEstornoDemanda.isPresent()) {
            return optionalEstornoDemanda.get();
        }
        throw new IdNaoEncontrado("funcionário", id);
    }

    public void removerPorId(Long id){
        Optional<EstornoDemanda> optionalEstornoDemanda = estornoDemandaRepository.findById(id);
        if(optionalEstornoDemanda.isPresent()) {
            estornoDemandaRepository.deleteById(id);
        }
        throw new IdNaoEncontrado("funcionário", id);
    }

    public String relatorioQtdEstornosPorResponsavel(){
        List<QtdEstornosPorResponsavel> relatorio =
                estornoDemandaRepository.relatorioQtdEstornosPorResponsavel();
        String cabecalho = """
        --------------------------------------------------------------------
        *** RELATÓRIO: Quantidade de estornos por responsável da demanda ***
        --------------------------------------------------------------------
        """;
        String conteudo = "";
        for (QtdEstornosPorResponsavel item : relatorio){
            conteudo += "|" + item.getIdResponsavel() + " | " + item.getResponsavel() + " | " + item.getQtdEstornos();
        }
        String footer = "--------------------------------------------------------------------";
        return cabecalho + conteudo + footer;
    }
}