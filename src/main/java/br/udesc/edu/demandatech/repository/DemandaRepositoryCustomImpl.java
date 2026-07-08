package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.dto.relatorio.DezFuncionariosMaisProdutivos;
import br.udesc.edu.demandatech.model.dto.relatorio.QtdDemandasPorDepartamento;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.core.aggregation.Fields;

@Repository
public class DemandaRepositoryCustomImpl implements DemandaRepositoryCustom {

        @Autowired
        private MongoTemplate mongoTemplate;

        @Override
        public Optional<Funcionario> findFuncionarioComMenosDemandas(String idDepartamento) {
                Aggregation agg = Aggregation.newAggregation(
                                Aggregation.match(Criteria.where("tipo.departamento._id").is(idDepartamento)),
                                Aggregation.group("responsavel._id")
                                                .first("responsavel").as("funcionario")
                                                .count().as("totalDemandas"),
                                Aggregation.sort(Sort.Direction.ASC, "totalDemandas"),
                                Aggregation.limit(1),
                                Aggregation.replaceRoot("funcionario"));

                Funcionario resultado = mongoTemplate.aggregate(agg, "demandas", Funcionario.class)
                                .getUniqueMappedResult();
                return Optional.ofNullable(resultado);
        }

        @Override
        public List<QtdDemandasPorDepartamento> relatorioQtdDemandasPorDepartamento() {
                Aggregation agg = Aggregation.newAggregation(
                                Aggregation.match(Criteria.where("status.descricao").is("Ativo")),
                                Aggregation.group(Fields.from(
                                                Fields.field("idDepartamento", "tipo.departamento._id"),
                                                Fields.field("descricao", "tipo.departamento.descricao")
                                        ))
                                                .count().as("qtdDemanda"),
                                Aggregation.project("qtdDemanda")
                                                .and("_id.idDepartamento").as("idDepartamento")
                                                .and("_id.descricao").as("departamento"));

                AggregationResults<QtdDemandasPorDepartamento> results = mongoTemplate.aggregate(agg, "demandas",
                                QtdDemandasPorDepartamento.class);
                return results.getMappedResults();
        }

        @Override
        public List<DezFuncionariosMaisProdutivos> relatorioDezFuncionariosMaisProdutivos() {
                Aggregation agg = Aggregation.newAggregation(
                                Aggregation.match(Criteria.where("status.descricao").is("Resolvido")),
                                Aggregation.group(Fields.from(
                                                Fields.field("matricula", "responsavel._id"),
                                                Fields.field("nome", "responsavel.nome")
                                        ))
                                                .count().as("qtdDemanda"),
                                Aggregation.project("qtdDemanda")
                                                .and("_id.matricula").as("matricula")
                                                .and("_id.nome").as("nome"),
                                Aggregation.sort(Sort.Direction.DESC, "qtdDemanda"),
                                Aggregation.limit(10));

                AggregationResults<DezFuncionariosMaisProdutivos> results = mongoTemplate.aggregate(agg, "demandas",
                                DezFuncionariosMaisProdutivos.class);
                return results.getMappedResults();
        }
}
