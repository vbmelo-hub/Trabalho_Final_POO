# Gerenciador de Tarefas - Projeto Final em Java

Este é um sistema de gerenciamento de tarefas desenvolvido em Java, com interface de console. O programa permite adicionar, listar, buscar e concluir tarefas categorizadas como **Trabalho**, **Estudo** ou **Pessoal**.

## 📁 Estrutura do Projeto

O projeto está estruturado como um projeto Maven, com classes Java organizadas por tipo de tarefa e funcionalidades do sistema. As principais classes são:

- `Main.java`: ponto de entrada do sistema.
- `MenuUsuario.java`: gerencia o menu e a interação com o usuário.
- `Tarefa.java`: classe abstrata base para todas as tarefas.
- `TarefaTrabalho`, `TarefaEstudo`, `TarefaPessoal`: subclasses específicas.
- `TarefaServico.java`: lógica de negócio.
- `TarefaDao.java`: repositório em memória para tarefas.

> ⚠️ **Pré-requisitos**:
> - Java JDK 24
> - Apache Maven 3.9.11 ou superior
> - Variáveis de ambiente `JAVA_HOME` e `MAVEN_HOME` corretamente configuradas

---

## ▶️ Como Executar o Projeto

### 1. Extraia os arquivos

Extraia o conteúdo do arquivo `Projeto Final.rar` para uma pasta de sua preferência.

### 2. Acesse a pasta do projeto no terminal

```bash
cd /caminho/para/pasta/ProjetoFinal
```

### 3. Compile o projeto com Maven

```bash
mvn clean compile
```

### 4. Execute a aplicação

```bash
mvn exec:java -Dexec.mainClass="br.com.vigi.aplicacao.Main"
```

> Substitua o nome da classe principal, se necessário, de acordo com a estrutura do seu projeto.

---

## 📌 Funcionalidades Disponíveis

- Adicionar nova tarefa (Trabalho, Estudo ou Pessoal)
- Listar tarefas pendentes
- Marcar tarefa como concluída
- Listar tarefas concluídas
- Buscar tarefas por data de agendamento

---

## 📅 Formato de Data

As datas devem ser digitadas no formato **DD/MM/AAAA**, conforme o padrão brasileiro.

---

## 👥 Autores

- Giovanna Souza Correia
- Vínicius Barros de Melo
