package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.dto.relatorio.QtdEstornosPorResponsavel;
import br.udesc.edu.demandatech.model.entity.EstornoDemanda;
import br.udesc.edu.demandatech.model.entity.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EstornoDemandaRepositoryCustomImpl implements EstornoDemandaRepositoryCustom {

        @Autowired
        private MongoTemplate mongoTemplate;

        @Override
        public boolean funcionarioCanEdit(String idEstorno, Funcionario funcionario) {
                Query query = new Query();
                query.addCriteria(Criteria.where("_id").is(idEstorno));
                query.addCriteria(new Criteria().orOperator(
                                Criteria.where("demanda.criador.matricula").is(funcionario.getMatricula()),
                                Criteria.where("demanda.responsavel.matricula").is(funcionario.getMatricula())));
                return mongoTemplate.exists(query, EstornoDemanda.class);
        }

        @Override
        public List<QtdEstornosPorResponsavel> relatorioQtdEstornosPorResponsavel() {
                Aggregation agg = Aggregation.newAggregation(
                                Aggregation.group("demanda.responsavel.matricula", "demanda.responsavel.nome")
                                                .count().as("qtdEstornos"),
                                Aggregation.project("qtdEstornos")
                                                .and("_id.demanda.responsavel.matricula").as("idResponsavel")
                                                .and("_id.demanda.responsavel.nome").as("responsavel"),
                                Aggregation.sort(Sort.Direction.DESC, "qtdEstornos"));

                AggregationResults<QtdEstornosPorResponsavel> results = mongoTemplate.aggregate(agg, "estornos",
                                QtdEstornosPorResponsavel.class);
                return results.getMappedResults();
        }
}