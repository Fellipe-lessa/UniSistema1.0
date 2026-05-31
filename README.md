# Sistema de Gestão Universitária

Sistema web desenvolvido em Java com Spring Boot para gerenciamento de alunos, matérias e turmas de uma universidade.

---

##  Sobre o Projeto

Este projeto foi desenvolvido como projeto final da disciplina de Desenvolvimento Web, simulando um ambiente universitário com três módulos principais: cadastro e consulta de **Alunos**, **Matérias** e **Turmas**.

---

##  Funcionalidades

###  Módulo Alunos
- Cadastro de alunos com nome, endereço, matrícula e data de ingresso
- Consulta por matrícula
- Visualização das matérias em que o aluno está matriculado
- Exibição das notas e média por matéria
- Status de aprovação ou reprovação

###  Módulo Matérias
- Cadastro de matérias com código, nome e ementa
- Consulta por código da matéria

###  Módulo Turmas
- Cadastro de turmas vinculadas a uma matéria
- Consulta por código da turma
- Matrícula de alunos nas turmas
- Lançamento de 3 notas por aluno
- Cálculo automático de média
- Status de aprovação (média ≥ 6.0)

---

##  Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.2.5 | Framework web |
| Thymeleaf | 3.1.5 | Motor de templates HTML |
| PostgreSQL | 18 | Banco de dados |
| Maven | 3.x | Gerenciador de dependências |
| HTML/CSS | - | Interface do usuário |

---

##  Arquitetura do Projeto

O projeto segue o padrão **MVC** com camada **DAO** para acesso ao banco de dados:

```
src/main/java/com/universidade/
├── controller/          → Recebe requisições HTTP
│   ├── HomeController
│   ├── AlunoController
│   ├── MateriaController
│   └── TurmaController
├── dao/                 → Acesso ao banco de dados (padrão DAO)
│   ├── ConexaoDB        → Gerencia conexões JDBC
│   ├── TabelasDAO       → Cria as tabelas automaticamente
│   ├── AlunoDAO         → CRUD de alunos
│   ├── MateriaDAO       → CRUD de matérias
│   └── TurmaDAO         → CRUD de turmas + matrículas + notas
├── model/               → Classes de domínio
│   ├── Aluno
│   ├── Materia
│   ├── Turma
│   └── Matricula
└── UniversidadeApplication.java

src/main/resources/
├── templates/           → Páginas HTML (Thymeleaf)
│   ├── index.html
│   ├── aluno/
│   ├── materia/
│   └── turma/
└── application.properties
```

---

##  Banco de Dados

As tabelas são criadas automaticamente ao iniciar a aplicação:

```sql
tb_aluno       → id, nome, endereco, matricula, data_ingresso
tb_materia     → id, codigo, nome, ementa
tb_turma       → id, codigo, descricao, materia_id
tb_matricula   → id, aluno_id, turma_id, nota1, nota2, nota3
```

---

##  Como Executar

### Pré-requisitos
- Java 17+
- PostgreSQL 18
- Maven 3.x

### Configuração do Banco

1. Crie um banco de dados chamado `universidade` no PostgreSQL
2. Atualize as credenciais no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/universidade
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA
server.port=8081
```

### Executando o Projeto

```bash
# Clone o repositório
git clone https://github.com/Fellipe-lessa/universidade1.0.git

# Entre na pasta
cd universidade1.0

# Execute
./mvnw spring-boot:run
```

Acesse em: **http://localhost:8081**

---

##  Equipe

| Nome | GitHub |
|------|--------|
| Fellipe Lessa Mesquita | [@Fellipe-lessa](https://github.com/Fellipe-lessa) |

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos.
