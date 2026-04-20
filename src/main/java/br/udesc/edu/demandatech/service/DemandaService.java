package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.DemandaCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.DemandaEditarDTO;
import br.udesc.edu.demandatech.model.dto.relatorio.DezFuncionariosMaisProdutivos;
import br.udesc.edu.demandatech.model.dto.relatorio.QtdDemandasPorDepartamento;
import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.FuncionarioEnvolvido;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.SemFuncionarioDisponivel;
import br.udesc.edu.demandatech.repository.DemandaRepository;
import br.udesc.edu.demandatech.repository.FuncionarioEnvolvidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DemandaService {
    private DemandaRepository demandaRepository;
    private FuncionarioEnvolvidoRepository funcionarioEnvolvidoRepository;

    public Demanda criar(DemandaCriarDTO demandaCriarDTO){
        Demanda demanda = new Demanda();
        BeanUtils.copyProperties(demandaCriarDTO, demanda);

        Optional<Funcionario> responsavel = demandaRepository.findFuncionarioComMenosDemandas(
                demandaCriarDTO.tipo().getDepartamento().getIdDepartamento()
        );
        if(responsavel.isEmpty()) throw new SemFuncionarioDisponivel();

        demanda.setData(LocalDate.now());
        demanda.setHora(LocalTime.now());
        demanda.setResponsavel(responsavel.get());

        demanda = demandaRepository.save(demanda);

        for (Funcionario funcionario : demandaCriarDTO.usuariosEnvolvidos()) {
            FuncionarioEnvolvido fe = new FuncionarioEnvolvido(funcionario, demanda);
            funcionarioEnvolvidoRepository.save(fe);
        }

        return demanda;
    }

    public Demanda atualizar(Long id, DemandaEditarDTO demandaEditarDTO){
        Optional<Demanda> optionalDemanda = demandaRepository.getDemandasByIdAndUsuario(id, demandaEditarDTO.criador());
        if(optionalDemanda.isPresent()){
            Demanda demanda = optionalDemanda.get();
            BeanUtils.copyProperties(demandaEditarDTO, demanda, "criador");
            return demandaRepository.save(demanda);
        }
        throw new IdNaoEncontrado("demandas", id);
    }

    public List<Demanda> buscarTudo(){
        return demandaRepository.findAll();
    }

    public Demanda buscarPorId(Long id){
        Optional<Demanda> optionalDemanda = demandaRepository.findById(id);
        if(optionalDemanda.isPresent()) {
            return optionalDemanda.get();
        }
        throw new IdNaoEncontrado("demandas", id);
    }

    public void removerPorId(Funcionario funcionario, Long id){
        Optional<Demanda> optionalDemanda = demandaRepository.getDemandasByIdAndUsuario(id, funcionario);
        if(optionalDemanda.isPresent()) {
            demandaRepository.deleteById(id);
            return;
        }
        throw new IdNaoEncontrado("demandas", id);
    }

    public String relatorioQtdDemandasPorDepartamento(){
        List<QtdDemandasPorDepartamento> relatorio =
                demandaRepository.relatorioQtdDemandasPorDepartamento();
        String cabecalho = """
        --------------------------------------------------------------------
        *** RELATÓRIO: Quantidade de demandas por departamento ***
        --------------------------------------------------------------------
        """;
        String conteudo = "";
        for (QtdDemandasPorDepartamento item : relatorio){
            conteudo += "| " + item.getIdDepartamento() + " | " + item.getDepartamento() + " | " + item.getQtdDemanda() + " |\n";
        }
        String footer = "--------------------------------------------------------------------";
        return cabecalho + conteudo + footer;
    }

    public String relatorioDezFuncionariosMaisProdutivos(){
        List<DezFuncionariosMaisProdutivos> relatorio =
                demandaRepository.relatorioDezFuncionariosMaisProdutivos();
        String cabecalho = """
        --------------------------------------------------------------------
        *** RELATÓRIO: Top 10 funcionários mais produtivos ***
        --------------------------------------------------------------------
        """;
        String conteudo = "";
        for (DezFuncionariosMaisProdutivos item : relatorio){
            conteudo += "| " + item.getMatricula() + " | " + item.getNome() + " | " + item.getQtdDemanda() + " |\n";
        }
        String footer = "--------------------------------------------------------------------";
        return cabecalho + conteudo + footer;
    }
}
