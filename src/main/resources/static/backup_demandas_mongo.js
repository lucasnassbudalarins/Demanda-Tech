// Script de Backup e Carga Inicial - DemandaTech (MongoDB)
// Pode ser executado via MongoDB Compass, mongosh ou copiado para o terminal do Mongo.

// Define o banco de dados a ser utilizado
db = db.getSiblingDB('demandatech');

// Limpa coleções existentes para evitar duplicidade ao reexecutar
db.status.drop();
db.prioridades.drop();
db.departamentos.drop();
db.funcionarios.drop();
db.tipos_de_demanda.drop();
db.demandas.drop();
db.estornos.drop();

// ==========================================
// 1. INSERÇÃO DE STATUS
// ==========================================
db.status.insertMany([
  { _id: "1", descricao: "Ativo" },
  { _id: "2", descricao: "Resolvido" },
  { _id: "3", descricao: "Cancelado" }
]);

// ==========================================
// 2. INSERÇÃO DE PRIORIDADES
// ==========================================
db.prioridades.insertMany([
  { _id: "1", descricao: "Baixa" },
  { _id: "2", descricao: "Média" },
  { _id: "3", descricao: "Alta" }
]);

// ==========================================
// 3. INSERÇÃO DE DEPARTAMENTOS E FUNCIONÁRIOS
// ==========================================

// Para evitar problemas de dependência circular no MongoDB, criamos primeiro as estruturas básicas:
db.departamentos.insertMany([
  { _id: "1", descricao: "TI - Suporte", gerente: null },
  { _id: "2", descricao: "Recursos Humanos", gerente: null },
  { _id: "3", descricao: "Financeiro", gerente: null }
]);

db.funcionarios.insertMany([
  {
    _id: "1000",
    nome: "Administrador Geral",
    email: "admin@demandatech.com",
    senha: "admin",
    admin: true,
    departamento: { _id: "1", descricao: "TI - Suporte" }
  },
  {
    _id: "1001",
    nome: "João da Silva",
    email: "joao.silva@demandatech.com",
    senha: "senha123",
    admin: false,
    departamento: { _id: "1", descricao: "TI - Suporte" }
  },
  {
    _id: "1002",
    nome: "Maria de Souza",
    email: "maria.souza@demandatech.com",
    senha: "senha123",
    admin: false,
    departamento: { _id: "2", descricao: "Recursos Humanos" }
  },
  {
    _id: "1003",
    nome: "Pedro Santos",
    email: "pedro.santos@demandatech.com",
    senha: "senha123",
    admin: false,
    departamento: { _id: "3", descricao: "Financeiro" }
  }
]);

// Agora que os funcionários foram criados, atualizamos os gerentes dos departamentos
db.departamentos.updateOne({ _id: "1" }, { $set: { gerente: { _id: "1000", nome: "Administrador Geral", email: "admin@demandatech.com", admin: true } } });
db.departamentos.updateOne({ _id: "2" }, { $set: { gerente: { _id: "1002", nome: "Maria de Souza", email: "maria.souza@demandatech.com", admin: false } } });
db.departamentos.updateOne({ _id: "3" }, { $set: { gerente: { _id: "1003", nome: "Pedro Santos", email: "pedro.santos@demandatech.com", admin: false } } });

// ==========================================
// 4. INSERÇÃO DE TIPOS DE DEMANDA
// ==========================================
db.tipos_de_demanda.insertMany([
  {
    _id: "1",
    descricao: "Problema com Hardware",
    departamento: { _id: "1", descricao: "TI - Suporte" }
  },
  {
    _id: "2",
    descricao: "Dúvida sobre Folha de Pagamento",
    departamento: { _id: "2", descricao: "Recursos Humanos" }
  },
  {
    _id: "3",
    descricao: "Reembolso de Despesas",
    departamento: { _id: "3", descricao: "Financeiro" }
  }
]);

// ==========================================
// 5. INSERÇÃO DE DEMANDAS DE TESTE
// ==========================================
db.demandas.insertMany([
  {
    _id: "668aa001c9e88d228f4a7c01",
    titulo: "Notebook não liga",
    data: "2026-07-07",
    hora: "10:15:30",
    descricao: "O notebook Dell Inspiron do RH parou de funcionar inesperadamente.",
    prioridade: { _id: "3", descricao: "Alta" },
    tipo: {
      _id: "1",
      descricao: "Problema com Hardware",
      departamento: { _id: "1", descricao: "TI - Suporte" }
    },
    criador: {
      _id: "1002",
      nome: "Maria de Souza",
      email: "maria.souza@demandatech.com",
      admin: false
    },
    responsavel: {
      _id: "1001",
      nome: "João da Silva",
      email: "joao.silva@demandatech.com",
      admin: false
    },
    status: { _id: "1", descricao: "Ativo" },
    matriculasEnvolvidos: ["1002", "1001"]
  },
  {
    _id: "668aa001c9e88d228f4a7c02",
    titulo: "Dúvida férias proporcionais",
    data: "2026-07-06",
    hora: "14:20:00",
    descricao: "Solicitação de esclarecimentos sobre o cálculo proporcional das férias.",
    prioridade: { _id: "1", descricao: "Baixa" },
    tipo: {
      _id: "2",
      descricao: "Dúvida sobre Folha de Pagamento",
      departamento: { _id: "2", descricao: "Recursos Humanos" }
    },
    criador: {
      _id: "1003",
      nome: "Pedro Santos",
      email: "pedro.santos@demandatech.com",
      admin: false
    },
    responsavel: {
      _id: "1002",
      nome: "Maria de Souza",
      email: "maria.souza@demandatech.com",
      admin: false
    },
    status: { _id: "2", descricao: "Resolvido" },
    matriculasEnvolvidos: ["1003"]
  }
]);

// ==========================================
// 6. INSERÇÃO DE ESTORNOS DE TESTE
// ==========================================
db.estornos.insertMany([
  {
    _id: "668aa55bc9e88d228f4a7d01",
    descricao: "O notebook apresentou tela azul logo após ser entregue.",
    data: "2026-07-07",
    demanda: {
      _id: "668aa001c9e88d228f4a7c01",
      titulo: "Notebook não liga",
      responsavel: {
        _id: "1001",
        nome: "João da Silva",
        email: "joao.silva@demandatech.com"
      }
    }
  }
]);

print("Carga e backup MongoDB concluídos com sucesso no database 'demandatech'!");
