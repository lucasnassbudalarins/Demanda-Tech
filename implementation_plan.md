# Plano de Correção e Refatoração

## 1. Correções de CRUD

### TipoDemanda
**Causa:** A anotação `@Document(collection = "tipos_demanda")` está incorreta. No banco, a coleção chama-se `tipos_de_demanda`. Isso faz com que as operações de listar e buscar não achem nada, e o cadastro crie uma coleção nova errada.
**Solução:** Corrigir para `@Document(collection = "tipos_de_demanda")`.

### Demandas (não cadastra tipo de demanda)
**Causa:** É um efeito cascata. Quando o console pede o ID do Tipo de Demanda, ele faz uma busca, que não retorna nada e falha porque o TipoDemanda estava apontando pra coleção errada.
**Solução:** Resolvido automaticamente ao arrumar a coleção de TipoDemanda. Também garantiremos que a exibição da demanda no console mostre o Tipo de Demanda, já que atualmente foi esquecido no `System.out.println`.

### Funcionários envolvidos
**Causa/Análise:** O código que adiciona e remove a matrícula nas demandas parece logicamente correto (utilizando o array `matriculasEnvolvidos`). No entanto, a lista interna retornada pelo MongoDB pode, em algumas versões antigas, ser imutável se vazia. Além disso, as exceções geradas pela falta de `TipoDemanda` corrompiam a demanda inteira.
**Solução:** Refatorar o `FuncionarioEnvolvidoService` para garantir que `matriculasEnvolvidos` sempre seja uma coleção mutável antes de invocar `.add()` ou `.remove()`.

## 2. Correção de Relatórios com Null
**Causa:** Na hora de montar as agregações do Spring Data (Aggregation), o código agrupou por `"responsavel.matricula"`. Como `matricula` é o `@Id` do `Funcionario`, o Spring Data MongoDB o salva fisicamente como `_id` na estrutura do banco. Portanto, o campo `responsavel.matricula` não existe no JSON armazenado (ele é `responsavel._id`). Isso resulta em valores nulos no mapeamento do retorno. O mesmo vale para o id de Departamentos.
**Solução:** Corrigir os pipelines de `.group()` e `.project()` para acessar `_id` ao invés dos nomes de atributos Java (`matricula` ou `idDepartamento`).

## 3. Viabilidade da Mudança nos IDs (Sequenciais)
Para alterar os IDs de MongoDB (`667c29b...`) para numéricos sequenciais (1, 2, 3...):
- **O que precisa ser feito:** Como o MongoDB não tem auto-incremento nativo, precisamos criar uma coleção chamada `database_sequences` (para guardar o último valor). Adicionalmente, precisaremos interceptar todas as operações de "save" usando um `AbstractMongoEventListener` que consulta a sequência, incrementa e injeta na entidade antes de salvar.
- **Viabilidade:** É plenamente viável e muito recomendado para sistemas onde o usuário lida com IDs manualmente no dia a dia (ex: digitar ID de Demanda 12 ao invés de um hash).
- **Esforço:** Exigirá a criação de 2 classes novas (Sequence e Listener) e modificar todas as entidades que necessitarem de IDs amigáveis (Demandas, Tipos, Prioridades). 

> [!IMPORTANT]
> **Aguardando aprovação:** As correções de bug serão implementadas rapidamente. Para a mudança de IDs para sequenciais, por favor me confirme se você deseja que eu construa essa lógica de Auto Incremento no MongoDB, ou se mantemos os IDs atuais mas focando apenas na estabilidade dos dados.

