-- Arquivo de Backup - DemandaTech (Infraestrutura e Dados de Teste)

-- ==========================================
-- DDL - INFRAESTRUTURA
-- ==========================================
CREATE SCHEMA IF NOT EXISTS demanda_tech;

-- Sequências
CREATE SEQUENCE IF NOT EXISTS demanda_tech.status_id_status_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.prioridades_id_prioridade_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.departamentos_id_departamento_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.funcionarios_matricula_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.tipos_de_demanda_id_tipo_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.demandas_id_demanda_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.estorno_demanda_id_estorno_seq START WITH 1 INCREMENT BY 1;

-- Criação de Tabelas sem FK para evitar problemas de dependência circular imediata
CREATE TABLE IF NOT EXISTS demanda_tech.status (
    id_status BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.status_id_status_seq'),
    descricao VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS demanda_tech.prioridades (
    id_prioridade BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.prioridades_id_prioridade_seq'),
    descricao VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS demanda_tech.funcionarios (
    matricula BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.funcionarios_matricula_seq'),
    nome VARCHAR(255),
    email VARCHAR(255),
    admin BOOLEAN DEFAULT false,
    id_departamento BIGINT
);

CREATE TABLE IF NOT EXISTS demanda_tech.departamentos (
    id_departamento BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.departamentos_id_departamento_seq'),
    descricao VARCHAR(50) NOT NULL,
    gerente BIGINT REFERENCES demanda_tech.funcionarios(matricula)
);

-- Adicionando FK entre funcionário e departamento agora que ambas existem
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints WHERE constraint_name='fk_func_depto' AND table_name='funcionarios' AND table_schema='demanda_tech') THEN
        ALTER TABLE demanda_tech.funcionarios ADD CONSTRAINT fk_func_depto FOREIGN KEY (id_departamento) REFERENCES demanda_tech.departamentos(id_departamento);
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS demanda_tech.tipos_de_demanda (
    id_tipo BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.tipos_de_demanda_id_tipo_seq'),
    descricao VARCHAR(255),
    id_departamento BIGINT REFERENCES demanda_tech.departamentos(id_departamento)
);

CREATE TABLE IF NOT EXISTS demanda_tech.demandas (
    id_demanda BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.demandas_id_demanda_seq'),
    titulo VARCHAR(30) NOT NULL,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    descricao VARCHAR(250),
    id_prioridade BIGINT NOT NULL DEFAULT 0 REFERENCES demanda_tech.prioridades(id_prioridade),
    id_tipo BIGINT NOT NULL REFERENCES demanda_tech.tipos_de_demanda(id_tipo),
    responsavel BIGINT REFERENCES demanda_tech.funcionarios(matricula),
    criador BIGINT NOT NULL REFERENCES demanda_tech.funcionarios(matricula),
    id_status BIGINT NOT NULL REFERENCES demanda_tech.status(id_status)
);

CREATE TABLE IF NOT EXISTS demanda_tech.estorno_demanda (
    id_estorno BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.estorno_demanda_id_estorno_seq'),
    descricao VARCHAR(255),
    data DATE,
    id_demanda BIGINT REFERENCES demanda_tech.demandas(id_demanda)
);

CREATE TABLE IF NOT EXISTS demanda_tech.funcionarios_envolvidos (
    matricula_funcionario BIGINT REFERENCES demanda_tech.funcionarios(matricula),
    id_demanda BIGINT REFERENCES demanda_tech.demandas(id_demanda),
    PRIMARY KEY (matricula_funcionario, id_demanda)
);

-- Limpeza da base para inserção de testes limpos
TRUNCATE TABLE demanda_tech.funcionarios_envolvidos CASCADE;
TRUNCATE TABLE demanda_tech.estorno_demanda CASCADE;
TRUNCATE TABLE demanda_tech.demandas CASCADE;
TRUNCATE TABLE demanda_tech.tipos_de_demanda CASCADE;
ALTER TABLE demanda_tech.funcionarios DROP CONSTRAINT IF EXISTS fk_func_depto;
TRUNCATE TABLE demanda_tech.departamentos CASCADE;
TRUNCATE TABLE demanda_tech.funcionarios CASCADE;
ALTER TABLE demanda_tech.funcionarios ADD CONSTRAINT fk_func_depto FOREIGN KEY (id_departamento) REFERENCES demanda_tech.departamentos(id_departamento);
TRUNCATE TABLE demanda_tech.prioridades CASCADE;
TRUNCATE TABLE demanda_tech.status CASCADE;

-- ==========================================
-- DML - DADOS DE TESTE
-- ==========================================

-- 1. Status
INSERT INTO demanda_tech.status (id_status, descricao) VALUES 
(1, 'Ativo'), (2, 'Resolvido'), (3, 'Cancelado');

-- 2. Prioridades
INSERT INTO demanda_tech.prioridades (id_prioridade, descricao) VALUES 
(1, 'Baixa'), (2, 'Média'), (3, 'Alta'), (4, 'Urgente');

-- 3. Funcionários (sem departamento inicialmente para evitar conflito FK)
INSERT INTO demanda_tech.funcionarios (matricula, nome, email, admin) VALUES 
(1, 'Admin Sistema', 'admin@demandatech.com.br', true),
(2, 'João Silva', 'joao.silva@demandatech.com.br', false),
(3, 'Maria Souza', 'maria.souza@demandatech.com.br', false),
(4, 'Pedro Santos', 'pedro.santos@demandatech.com.br', true);

-- 4. Departamentos
INSERT INTO demanda_tech.departamentos (id_departamento, descricao, gerente) VALUES 
(1, 'Tecnologia da Informação', 1),
(2, 'Recursos Humanos', 4),
(3, 'Financeiro', 3);

-- Atualizando funcionários com departamentos
UPDATE demanda_tech.funcionarios SET id_departamento = 1 WHERE matricula = 1;
UPDATE demanda_tech.funcionarios SET id_departamento = 1 WHERE matricula = 2;
UPDATE demanda_tech.funcionarios SET id_departamento = 3 WHERE matricula = 3;
UPDATE demanda_tech.funcionarios SET id_departamento = 2 WHERE matricula = 4;

-- 5. Tipos de Demanda
INSERT INTO demanda_tech.tipos_de_demanda (id_tipo, descricao, id_departamento) VALUES 
(1, 'Manutenção de Computador', 1),
(2, 'Acesso a Sistemas', 1),
(3, 'Dúvida Pagamento', 3),
(4, 'Contratação', 2);

-- 6. Demandas
INSERT INTO demanda_tech.demandas (id_demanda, titulo, data, hora, descricao, id_prioridade, id_tipo, responsavel, criador, id_status) VALUES 
(1, 'Computador não liga', '2026-04-20', '08:30:00', 'Meu PC está com tela preta desde ontem.', 3, 1, 1, 3, 2),
(2, 'Acesso ao ERP', '2026-04-20', '09:15:00', 'Preciso de acesso ao sistema de pagamentos.', 2, 2, 2, 4, 1),
(3, 'Contratar Estagiário', '2026-04-19', '14:00:00', 'Precisamos de um estagiário para TI.', 2, 4, 4, 1, 3);

-- 7. Funcionários Envolvidos
INSERT INTO demanda_tech.funcionarios_envolvidos (matricula_funcionario, id_demanda) VALUES 
(1, 1), (2, 1), 
(4, 3), (1, 3);

-- 8. Estornos
INSERT INTO demanda_tech.estorno_demanda (id_estorno, descricao, data, id_demanda) VALUES 
(1, 'Acesso negado pelo gestor', '2026-04-20', 2);

-- Atualizando sequências para evitar conflitos ao adicionar novos registros pelo Java
SELECT setval('demanda_tech.status_id_status_seq', 4);
SELECT setval('demanda_tech.prioridades_id_prioridade_seq', 4);
SELECT setval('demanda_tech.departamentos_id_departamento_seq', 3);
SELECT setval('demanda_tech.funcionarios_matricula_seq', 4);
SELECT setval('demanda_tech.tipos_de_demanda_id_tipo_seq', 4);
SELECT setval('demanda_tech.demandas_id_demanda_seq', 3);
SELECT setval('demanda_tech.estorno_demanda_id_estorno_seq', 1);