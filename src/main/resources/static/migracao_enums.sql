-- Migração: tabelas status/prioridades -> colunas texto em demandas (ENUM no Java)
-- Rodar uma única vez em bancos criados com a versão antiga do backup_demandas.sql

BEGIN;

ALTER TABLE demanda_tech.demandas
    ADD COLUMN status     VARCHAR(20),
    ADD COLUMN prioridade VARCHAR(20);

UPDATE demanda_tech.demandas d
SET status = CASE s.descricao
        WHEN 'Ativo'     THEN 'ABERTA'
        WHEN 'Resolvido' THEN 'RESOLVIDA'
        WHEN 'Cancelado' THEN 'CANCELADA'
    END,
    prioridade = CASE p.descricao
        WHEN 'Baixa'   THEN 'BAIXA'
        WHEN 'Média'   THEN 'MEDIA'
        WHEN 'Alta'    THEN 'ALTA'
        WHEN 'Urgente' THEN 'URGENTE'
    END
FROM demanda_tech.status s, demanda_tech.prioridades p
WHERE s.id_status = d.id_status
  AND p.id_prioridade = d.id_prioridade;

-- Falha aqui se existir algum status/prioridade cadastrado fora da lista acima
ALTER TABLE demanda_tech.demandas
    ALTER COLUMN status SET NOT NULL,
    ALTER COLUMN prioridade SET NOT NULL,
    ALTER COLUMN prioridade SET DEFAULT 'BAIXA',
    ADD CONSTRAINT ck_demandas_status CHECK (status IN ('ABERTA', 'AGUARDANDO', 'RESOLVIDA', 'CANCELADA')),
    ADD CONSTRAINT ck_demandas_prioridade CHECK (prioridade IN ('BAIXA', 'MEDIA', 'ALTA', 'URGENTE'));

ALTER TABLE demanda_tech.demandas
    DROP COLUMN id_status,
    DROP COLUMN id_prioridade;

DROP TABLE demanda_tech.status;
DROP TABLE demanda_tech.prioridades;
DROP SEQUENCE IF EXISTS demanda_tech.status_id_status_seq;
DROP SEQUENCE IF EXISTS demanda_tech.prioridades_id_prioridade_seq;

COMMIT;
