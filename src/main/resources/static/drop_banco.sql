-- DemandaTech - Remoção das tabelas do schema demanda_tech
-- Apaga as tabelas (e os dados) em cascata, das dependentes para as principais.
-- Inclui as tabelas antigas status/prioridades, de antes da troca por ENUM.
-- Depois de rodar, recrie tudo com criacao_banco.sql

BEGIN;

DROP TABLE IF EXISTS demanda_tech.funcionarios_envolvidos CASCADE;
DROP TABLE IF EXISTS demanda_tech.estorno_demanda CASCADE;
DROP TABLE IF EXISTS demanda_tech.demandas CASCADE;
DROP TABLE IF EXISTS demanda_tech.tipos_de_demanda CASCADE;
DROP TABLE IF EXISTS demanda_tech.departamentos CASCADE;
DROP TABLE IF EXISTS demanda_tech.funcionarios CASCADE;

-- Tabelas antigas (substituídas pelos ENUMs Status e Prioridade)
DROP TABLE IF EXISTS demanda_tech.status CASCADE;
DROP TABLE IF EXISTS demanda_tech.prioridades CASCADE;

-- Sequências
DROP SEQUENCE IF EXISTS demanda_tech.funcionarios_matricula_seq;
DROP SEQUENCE IF EXISTS demanda_tech.departamentos_id_departamento_seq;
DROP SEQUENCE IF EXISTS demanda_tech.tipos_de_demanda_id_tipo_seq;
DROP SEQUENCE IF EXISTS demanda_tech.demandas_id_demanda_seq;
DROP SEQUENCE IF EXISTS demanda_tech.estorno_demanda_id_estorno_seq;
DROP SEQUENCE IF EXISTS demanda_tech.status_id_status_seq;
DROP SEQUENCE IF EXISTS demanda_tech.prioridades_id_prioridade_seq;

COMMIT;
