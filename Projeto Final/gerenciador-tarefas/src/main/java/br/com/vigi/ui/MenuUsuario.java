package br.com.vigi.ui;

import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import br.com.vigi.modelo.Tarefa;
import br.com.vigi.servico.TarefaServico;

public class MenuUsuario {

    private TarefaServico tarefaServico = new TarefaServico(); // cria um objeto do serviço pra poder chamar as regras de negócio
    private Console console = System.console(); // cria o objeto que lê o que o usuário digita
    private DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // cria a regra pra formatar a data pro nosso padrão

    // Método que inicia o programa e mantém o menu rodando
    public void iniciar() {
        if (console == null) { // checa se o console existe
            System.err.println("Erro: Não foi possível obter o console. Rode o programa em um terminal");
            return; // encerra o método se não tiver console
        }

        while (true) { // loop infinito pra manter o programa rodando
            exibirMenuPrincipal();
            try {
                String entrada = console.readLine(); // lê o que o usuário digitou como texto
                if (entrada == null) { // se o usuário fechar o terminal
                    break; // quebra o loop
                }
                int opcao = Integer.parseInt(entrada.trim()); // converte o texto lido pra número

                switch (opcao) { // escolhe o que fazer baseado no número
                    case 1: adicionarNovaTarefaUI(); break; // chama o método de adicionar tarefa
                    case 2: listarTarefasPendentesUI(); break; // chama o método de listar pendentes
                    case 3: listarTarefasConcluidasUI(); break; // chama o método de listar concluídas
                    case 4: concluirTarefaUI(); break; // chama o método de concluir
                    case 5: buscarPorDataUI(); break; // chama o método de busca
                    case 0:
                        System.out.println("Saindo do sistema... Até logo!");
                        return; // sai do método e encerra o programa
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) { // se a conversão pra número der erro
                System.out.println("Erro: Por favor, digite apenas números para as opções.");
            }
        }
    }

    // Apenas imprime o menu de opções na tela
    private void exibirMenuPrincipal() {
        System.out.println("\n===== Gerenciador de Tarefas =====\n");
        System.out.println("1. Adicionar Nova Tarefa");
        System.out.println("2. Listar Tarefas Pendentes");
        System.out.println("3. Listar Tarefas Concluídas");
        System.out.println("4. Marcar Tarefa como Concluída");
        System.out.println("5. Buscar Tarefas por Data de Agendamento");
        System.out.println("0. Sair\n");
        System.out.print("Escolha uma opção: ");
    }

    // Método reutilizável que fica num loop até o usuário digitar uma data válida
    private LocalDate lerData() {
        while (true) { // loop infinito
            try {
                return LocalDate.parse(console.readLine(), formatadorData); // tenta converter o texto pra data, se conseguir, retorna a data e sai do loop
            } catch (DateTimeParseException e) { // se der erro na conversão
                System.out.print("Formato de data inválido. Tente novamente (DD/MM/AAAA): "); // avisa o usuário e o loop continua
            }
        }
    }

    // Pede as informações que faltam pra uma Tarefa de Trabalho
    private void adicionarTarefaTrabalhoUI(String titulo, String descricao, LocalDate dataAgendamento) {
        System.out.print("Prazo (formato DD/MM/AAAA): ");
        LocalDate prazo = lerData(); // chama o método que lê e valida a data
        Optional<String> projetoOpcional = Optional.empty(); // cria um optional de projeto vazio por padrão
        System.out.print("Deseja associar a um projeto? (S/N): ");
        if (console.readLine().equalsIgnoreCase("S")) { // se o usuário digitar 'S' ou 's'
            System.out.print("Nome do projeto: ");
            projetoOpcional = Optional.of(console.readLine()); // coloca o nome do projeto dentro do optional
        }
        tarefaServico.adicionarTarefaTrabalho(titulo, descricao, dataAgendamento, prazo, projetoOpcional); // manda tudo pro serviço salvar
        System.out.println("Tarefa de trabalho adicionada com sucesso para " + dataAgendamento.format(formatadorData) + "!");
    }
    
    // Pede as informações que faltam pra uma Tarefa de Estudo
    private void adicionarTarefaEstudoUI(String titulo, String descricao, LocalDate dataAgendamento) {
        System.out.print("Disciplina: ");
        String disciplina = console.readLine();
        System.out.print("Data de Entrega (formato DD/MM/AAAA): ");
        LocalDate dataEntrega = lerData();
        tarefaServico.adicionarTarefaEstudo(titulo, descricao, dataAgendamento, disciplina, dataEntrega);
        System.out.println("Tarefa de estudo adicionada com sucesso para " + dataAgendamento.format(formatadorData) + "!");
    }

    // Pede as informações que faltam pra uma Tarefa Pessoal
    private void adicionarTarefaPessoalUI(String titulo, String descricao, LocalDate dataAgendamento) {
        System.out.print("Categoria: ");
        String categoria = console.readLine();
        Optional<LocalDate> prazoOpcional = Optional.empty(); // cria um optional de prazo vazio por padrão
        System.out.print("Deseja adicionar um prazo? (S/N): ");
        if (console.readLine().equalsIgnoreCase("S")) { // se a resposta for sim
            System.out.print("Prazo (formato DD/MM/AAAA): ");
            prazoOpcional = Optional.of(lerData()); // coloca a data do prazo dentro do optional
        }
        tarefaServico.adicionarTarefaPessoal(titulo, descricao, dataAgendamento, categoria, prazoOpcional);
        System.out.println("Tarefa pessoal adicionada com sucesso para " + dataAgendamento.format(formatadorData) + "!");
    }
    
    // Guia o usuário pra adicionar uma tarefa nova
    private void adicionarNovaTarefaUI() {
        System.out.println("\n=== Adicionar Nova Tarefa ===");
        System.out.print("Para qual data deseja agendar esta tarefa? (DD/MM/AAAA, deixe em branco para hoje): ");
        String dataInput = console.readLine(); // lê a data como texto
        LocalDate dataAgendamento;

        if (dataInput == null || dataInput.trim().isEmpty()) { // se o usuário não digitar nada
            dataAgendamento = LocalDate.now(); // usa a data de hoje
        } else {
            try {
                dataAgendamento = LocalDate.parse(dataInput, formatadorData); // tenta converter o texto pra data com nosso formato
            } catch (DateTimeParseException e) { // se der erro na conversão
                System.out.println("Formato de data inválido. Usando a data de hoje.");
                dataAgendamento = LocalDate.now(); // usa a data de hoje como padrão
            }
        }

        System.out.println("Qual o tipo de tarefa?");
        System.out.println("1. Trabalho");
        System.out.println("2. Estudo");
        System.out.println("3. Pessoal");
        System.out.print("Escolha o tipo: ");
        int tipo = Integer.parseInt(console.readLine()); // lê o tipo

        System.out.print("Título: ");
        String titulo = console.readLine(); // lê o título
        System.out.print("Descrição: ");
        String descricao = console.readLine(); // lê a descrição

        switch (tipo) { // dependendo do tipo escolhido
            case 1:
                adicionarTarefaTrabalhoUI(titulo, descricao, dataAgendamento); // chama o método específico de trabalho
                break;
            case 2:
                adicionarTarefaEstudoUI(titulo, descricao, dataAgendamento); // chama o método específico de estudo
                break;
            case 3:
                adicionarTarefaPessoalUI(titulo, descricao, dataAgendamento); // chama o método específico pessoal
                break;
            default:
                System.out.println("Tipo de tarefa inválido.");
        }
    }
    
    // Mostra as tarefas pendentes
    private void listarTarefasPendentesUI() {
        System.out.println("\n=== Tarefas Pendentes ===");
        List<Tarefa> pendentes = tarefaServico.listarTarefasPendentes(); // pede a lista de pendentes pro serviço
        if (pendentes.isEmpty()) { // se a lista estiver vazia
            System.out.println("Nenhuma tarefa pendente.");
            return; // encerra o método
        }
        for (int i = 0; i < pendentes.size(); i++) { // percorre a lista de pendentes
            System.out.println((i) + ". " + pendentes.get(i).exibirDetalhes()); // imprime cada tarefa com um número na frente
        }
    }

    // Mostra as tarefas que já foram concluídas
    private void listarTarefasConcluidasUI() {
        System.out.println("\n=== Tarefas Concluídas ===");
        List<Tarefa> concluidas = tarefaServico.listarTarefasConcluidas(); // pede a lista de concluídas pro serviço
        if (concluidas.isEmpty()) {
            System.out.println("Nenhuma tarefa foi concluída ainda.");
            return;
        }
        for (Tarefa tarefa : concluidas) { // percorre a lista
            System.out.println("- " + tarefa.exibirDetalhes()); // imprime cada tarefa com um traço na frente
        }
    }

    // Processo pra marcar uma tarefa como concluída
    private void concluirTarefaUI() {
        listarTarefasPendentesUI(); // primeiro, mostra as tarefas pendentes pra pessoa saber o número
        if (tarefaServico.listarTarefasPendentes().isEmpty()) { // se não tiver nada pra concluir
            return; // nem continua
        }
        System.out.print("\nDigite o número da tarefa que deseja concluir: ");
        try {
            int indice = Integer.parseInt(console.readLine()); // lê o número que o usuário digitou como indice a ser usado 
            if (tarefaServico.concluirTarefa(indice)) { // tenta concluir a tarefa lá no serviço
                System.out.println("Tarefa marcada como concluída com sucesso!"); // se o serviço retornar true, deu certo
            } else {
                System.out.println("Erro: Número de tarefa inválido."); // se retornar false, o número não existia
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
        }
    }

    // Busca e mostra as tarefas de uma data de agendamento específica
    private void buscarPorDataUI() {
        System.out.print("Digite a data de agendamento que deseja buscar (DD/MM/AAAA): ");
        LocalDate dataBusca = lerData(); // usa nosso método pra ler uma data válida

        System.out.println("\n=== Tarefas agendadas para " + dataBusca.format(formatadorData) + " ===");
        List<Tarefa> encontradas = tarefaServico.buscarTarefasPorDataDeAgendamento(dataBusca); // pede pro serviço buscar as tarefas daquela data

        if (encontradas.isEmpty()) { // se não achar nada
            System.out.println("Nenhuma tarefa encontrada para esta data.");
            return;
        }

        for (Tarefa tarefa : encontradas) { // percorre a lista de tarefas encontradas
            String status = tarefa.isConcluida() ? "[CONCLUÍDA]" : "[PENDENTE] "; // checa se tá concluída ou pendente
            System.out.println(status + tarefa.exibirDetalhes()); // imprime o status e os detalhes
        }
    }
}