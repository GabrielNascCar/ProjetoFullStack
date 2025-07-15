# Sistema de Gerenciamento de Clientes e Faturas

## Visão Geral
Sistema completo para gerenciamento de clientes e faturas desenvolvido para fintech, com:
- Backend em Spring Boot (Java 21)
- Frontend em HTML/CSS/JavaScript
- Banco de dados relacional
- Dockerização completa

## Tecnologias Utilizadas

### Backend
- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Swagger (Documentação de API)
- JUnit (Testes unitários)

### Frontend
- HTML5
- CSS3
- JavaScript 

### Infraestrutura
- Docker
- Docker Compose
- Banco de dados MySQL


### Clientes
- Cadastro de novos clientes
- Listagem de todos os clientes
- Consulta de cliente por ID
- Atualização de dados
- Listagem de clientes bloqueados
- Bloqueio automático após atraso

### Faturas
- Registro de faturas
- Listagem por cliente
- Registro de pagamentos
- Listagem de faturas atrasadas
- Atualização automática de status
- Verificação periódica (cron job)

## Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados

## Documentação da API

A API está documentada via Swagger UI e pode ser acessada em:

`http://localhost:8080/swagger-ui.html`

### Endpoints Principais

#### Clientes
| Método | Endpoint               | Descrição                     | Status Codes              |
|--------|------------------------|-------------------------------|---------------------------|
| GET    | /api/clientes /listar-todos     | Lista todos os clientes       | 200 OK                    |
| POST   | /api/clientes          | Cria novo cliente             | 201 Created, 400 Bad Request |
| GET    | /api/clientes/{id}     | Busca cliente por ID          | 200 OK, 404 Not Found     |
| PUT    | /api/clientes/{id}     | Atualiza dados do cliente     | 200 OK, 404 Not Found     |
| GET    | /api/clientes/bloqueados | Lista clientes bloqueados   | 200 OK                    |

#### Faturas
| Método | Endpoint                    | Descrição                    | Status Codes                 |
|--------|-----------------------------|------------------------------|------------------------------|
| GET    | /api/faturas/cliente/{id}   | Lista faturas por cliente    | 200 OK, 404 Not Found        |
| POST   | /api/faturas                | Cria nova fatura             | 201 Created, 400 Bad Request |
| PUT    | /api/faturas/{id}/pagamento | Registra pagamento de fatura | 200 OK, 404 Not Found        |
| GET    | /api/faturas/atrasadas      | Lista faturas atrasadas      | 200 OK                       |
| GET    | /api/faturas/fatura/{id}    | Busca fatura por id          | 200 OK                       |

## Regras de Negócio

### Status de Faturas
1. **ABERTA**:
    - Status inicial quando criada
    - Data de vencimento no futuro

2. **ATRASADA**:
    - Automática após data de vencimento
    - Verificada a cada 30 segundos (job agendado)

3. **PAGA**:
    - Após registro de pagamento
    - Data de pagamento definida automaticamente

### Bloqueio de Clientes
- Ocorre automaticamente quando:
    - Cliente possui faturas com mais de 3 dias de atraso
- Efeitos do bloqueio:
    - Status alterado para "BLOQUEADO"
    - Limite de crédito zerado (R$ 0,00)
    - Impede novas operações

### Validações
- CPF deve ser válido e único
- Data de nascimento deve ser válida
- Valor da fatura deve ser positivo
- Data de vencimento deve ser futura na criação

## Interface do Usuário

### Página de Clientes
- Tabela com lista de clientes contendo:
    - Nome completo
    - CPF (formatado)
    - Idade (calculada)
    - Status (ATIVO/BLOQUEADO)
    - Limite de crédito (formatado em BRL)
- Ações:
    - ✅ Botão "Ver Faturas" para cada cliente

### Página de Faturas
- Tabela com lista de faturas contendo:
    - Valor (formatado em BRL)
    - Data de vencimento (formato dd/MM/yyyy)
    - Status (com cores diferenciadas)
    - Data de pagamento (se aplicável)
- Ações:
    - 💳 Botão "Registrar Pagamento" para faturas não pagas
    - 🔙 Botão "Voltar" para lista de clientes

### Formulários
- Validação em tempo real
- Máscaras para campos específicos (CPF, datas, valores monetários)
- Feedback visual para ações bem sucedidas ou erros

## Próximos Passos e Melhorias

Caso houvesse mais tempo disponível para desenvolvimento, as seguintes melhorias seriam priorizadas no frontend

## Considerações Finais
O sistema implementa todas as funcionalidades requeridas seguindo as melhores práticas de desenvolvimento. A arquitetura foi pensada para permitir fácil expansão e manutenção.

**Desenvolvido por**: Gabriel do Nascimento Carvalho