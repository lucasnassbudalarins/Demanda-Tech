# DemandaTech - Sistema de Gestão de Demandas

Bem-vindo(a) ao **DemandaTech**, um sistema interno de gestão de demandas desenvolvido em Java com Spring Boot 3 e JPA/Hibernate. Este projeto não possui interface web; toda a interação é realizada via um menu iterativo no **Console (Terminal)**.

## 🚀 Como Executar o Sistema

Como o projeto utiliza o **Maven Wrapper**, você não precisa ter o Maven instalado globalmente na sua máquina. Siga os passos abaixo:

### Opção 1: Via Linha de Comando (Terminal)
1. Abra o terminal na pasta raiz do projeto (onde está o arquivo `pom.xml`).
2. Execute o comando:
   - No Windows: `.\mvnw spring-boot:run`
   - No Linux/Mac: `./mvnw spring-boot:run`

### Opção 2: Via IDE (IntelliJ, Eclipse, VS Code)
1. Importe o projeto como um projeto Maven.
2. Localize a classe principal: `src/main/java/br/udesc/edu/demandatech/DemandaTechApplication.java`
3. Execute-a (Run).
4. **Importante:** Abra a aba de Console da sua IDE para poder interagir com o sistema.

---

## 📁 Arquivo de Backup

O arquivo de backup está localiado em `src/main/resources/static/backup_demandas_mongo.js`. 
Para inserir ele no seu banco MongoDB é necessário criar uma conexão com o banco, atualizar o aplication.properties - se necessário - e em seguida executar o script no mongoDB Shell.
---

## ⚙️ Como o Sistema Funciona

Ao rodar a aplicação, um menu interativo será exibido diretamente no Console. O sistema provê **CRUD completo** para todas as entidades fundamentais do negócio, e é estruturado nas seguintes opções:

1. **Prioridades, Status, Departamentos, Tipos de Demanda**: Gestão dos cadastros auxiliares do sistema.
2. **Funcionários**: Gestão dos usuários do sistema. Note que o sistema diferencia ações administrativas (onde a "matrícula de admin" é exigida) para criar/editar/excluir registros.
3. **Demandas**: O coração do sistema. Permite registrar novas demandas, designar prioridades, status, tipos e responsáveis.
4. **Estornos de Demanda**: Permite registrar o histórico e justificativa de demandas que foram estornadas/devolvidas.
5. **Funcionários Envolvidos (Processo)**: Uma tabela associativa onde você pode vincular diversos funcionários a uma única demanda (relação N:N).
6. **Relatórios**: Funcionalidade analítica contendo três consultas específicas requisitadas:
   - Quantidade de Demandas por Departamento.
   - Top 10 Funcionários Mais Produtivos.
   - Quantidade de Estornos por Responsável.

### 💡 Dicas de Uso para Testes
- A navegação é toda feita digitando o número correspondente à opção desejada e apertando `Enter`.
- Para cadastros complexos (como Demandas), certifique-se de **cadastrar primeiro as dependências** (Ex: crie pelo menos um Departamento, depois um Funcionário (como criador/admin), uma Prioridade e um Tipo de Demanda, para só então criar a Demanda).
- Nas ações que exigem validação (como cadastrar um Departamento), o sistema pedirá a sua **matrícula de administrador**. Certifique-se de usar a matrícula de um funcionário previamente cadastrado que tenha privilégios.

Desenvolvido para fins acadêmicos - Projeto de Banco de Dados II.