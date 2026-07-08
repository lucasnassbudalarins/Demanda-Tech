package br.udesc.edu.demandatech.service;

import br.udesc.edu.demandatech.model.dto.criar.DemandaCriarDTO;
import br.udesc.edu.demandatech.model.dto.editar.DemandaEditarDTO;
import br.udesc.edu.demandatech.model.dto.relatorio.DezFuncionariosMaisProdutivos;
import br.udesc.edu.demandatech.model.dto.relatorio.QtdDemandasPorDepartamento;
import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.exception.IdNaoEncontrado;
import br.udesc.edu.demandatech.model.exception.PermissaoNegada;
import br.udesc.edu.demandatech.model.exception.SemFuncionarioDisponivel;
import br.udesc.edu.demandatech.model.exception.ValidacaoException;
import br.udesc.edu.demandatech.repository.DemandaRepository;
import br.udesc.edu.demandatech.repository.EstornoDemandaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class DemandaService {
    private DemandaRepository demandaRepository;
    private StatusService statusService;
    private EstornoDemandaRepository estornoDemandaRepository;
    private Validator validator;

    private void validar(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ValidacaoException(violations.iterator().next().getMessage());
        }
    }

    public Demanda criar(DemandaCriarDTO demandaCriarDTO) {
        validar(demandaCriarDTO);
        Demanda demanda = new Demanda();
        BeanUtils.copyProperties(demandaCriarDTO, demanda);

        Optional<Funcionario> responsavel = demandaRepository.findFuncionarioComMenosDemandas(
                demandaCriarDTO.tipo().getDepartamento().getIdDepartamento());
        if (responsavel.isEmpty())
            throw new SemFuncionarioDisponivel();

        demanda.setData(LocalDate.now());
        demanda.setHora(LocalTime.now());
        demanda.setResponsavel(responsavel.get());
        demanda.setStatus(statusService.buscarPorId("1"));

        // Armazena apenas as matriculas dos funcionarios envolvidos (referencia por array)
        List<String> matriculas = new ArrayList<>();
        if (demandaCriarDTO.usuariosEnvolvidos() != null) {
            for (Funcionario f : demandaCriarDTO.usuariosEnvolvidos()) {
                matriculas.add(f.getMatricula());
            }
        }
        demanda.setMatriculasEnvolvidos(matriculas);

        return demandaRepository.save(demanda);
    }

    public Demanda atualizar(String id, DemandaEditarDTO demandaEditarDTO, Funcionario funcionario) {
        validar(demandaEditarDTO);
        Optional<Demanda> optionalDemanda = demandaRepository.findById(id);
        if (optionalDemanda.isPresent()) {
            if (!optionalDemanda.get().getCriador().getMatricula().equals(funcionario.getMatricula()) &&
                    !optionalDemanda.get().getResponsavel().getMatricula().equals(funcionario.getMatricula()) &&
                    !funcionario.getAdmin())
                throw new PermissaoNegada();
            Demanda demanda = optionalDemanda.get();
            BeanUtils.copyProperties(demandaEditarDTO, demanda);
            return demandaRepository.save(demanda);
        }
        throw new IdNaoEncontrado("demandas", id);
    }

    public List<Demanda> buscarTudo() {
        return demandaRepository.findAll();
    }

    public Demanda buscarPorId(String id) {
        Optional<Demanda> optionalDemanda = demandaRepository.findById(id);
        if (optionalDemanda.isPresent()) {
            return optionalDemanda.get();
        }
        throw new IdNaoEncontrado("demandas", id);
    }

    public void removerPorId(Funcionario funcionario, String id) {
        Optional<Demanda> optionalDemanda = demandaRepository.findById(id);
        if (optionalDemanda.isPresent()) {
            if (!optionalDemanda.get().getCriador().getMatricula().equals(funcionario.getMatricula()) &&
                    !optionalDemanda.get().getResponsavel().getMatricula().equals(funcionario.getMatricula()) &&
                    !funcionario.getAdmin())
                throw new PermissaoNegada();
            Demanda demanda = optionalDemanda.get();
            // Remove os estornos associados (documentos separados)
            estornoDemandaRepository.deleteAll(estornoDemandaRepository.findByDemanda(demanda));
            // As matriculas envolvidas sao apenas strings dentro da Demanda, removidas automaticamente
            demandaRepository.deleteById(id);
            return;
        }
        throw new IdNaoEncontrado("demandas", id);
    }

    public String relatorioQtdDemandasPorDepartamento() {
        List<QtdDemandasPorDepartamento> relatorio = demandaRepository.relatorioQtdDemandasPorDepartamento();
        String cabecalho = """
                --------------------------------------------------------------------
                *** RELATÓRIO: Quantidade de demandas ativas por departamento ***
                --------------------------------------------------------------------
                """;
        String conteudo = "";
        for (QtdDemandasPorDepartamento item : relatorio) {
            conteudo += "| ID: " + item.getIdDepartamento() + " | Departamento: " + item.getDepartamento()
                    + " | Qtd. Demandas: " + item.getQtdDemanda() + " |\n";
        }
        String footer = "--------------------------------------------------------------------";
        return cabecalho + conteudo + footer;
    }

    public String relatorioDezFuncionariosMaisProdutivos() {
        List<DezFuncionariosMaisProdutivos> relatorio = demandaRepository.relatorioDezFuncionariosMaisProdutivos();
        String cabecalho = """
                --------------------------------------------------------------------
                *** RELATÓRIO: Top 10 funcionários mais produtivos ***
                --------------------------------------------------------------------
                """;
        String conteudo = "";
        for (DezFuncionariosMaisProdutivos item : relatorio) {
            conteudo += "| Matrícula: " + item.getMatricula() + " | Nome: " + item.getNome() + " | Qtd. Demandas: "
                    + item.getQtdDemanda() + " |\n";
        }
        String footer = "--------------------------------------------------------------------";
        return cabecalho + conteudo + footer;
    }
}
