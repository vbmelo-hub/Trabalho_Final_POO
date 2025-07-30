package br.com.vigi.servico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.vigi.modelo.Tarefa;
import br.com.vigi.modelo.TarefaEstudo;
import br.com.vigi.modelo.TarefaPessoal;
import br.com.vigi.modelo.TarefaTrabalho;
import br.com.vigi.persistencia.TarefaDao;

// Classe de Serviço, contém todas as regras que fazem o sistema funcionar, também é onde define "como" o sistema vai funcionar
public class TarefaServico {

    private TarefaDao tarefaDao = new TarefaDao(); //instancia um objeto pra ser a ponte com a persistencia 

    // --- METODOS QUE ADICIONAM TAREFAS ---

    public void adicionarTarefaTrabalho(String titulo, String descricao, LocalDate dataAgendamento, LocalDate prazo, Optional<String> projeto) {
        
        TarefaTrabalho novaTarefa = new TarefaTrabalho(titulo, descricao, dataAgendamento, prazo, projeto); // Cria um novo objeto do tipo TarefaTrabalho

        tarefaDao.salvar(novaTarefa); // Pede pra camada de persistencia salvar o objeto que acabou de ser criado
    }

    public void adicionarTarefaEstudo(String titulo, String descricao, LocalDate dataAgendamento, String disciplina, LocalDate dataEntrega) {
        
        TarefaEstudo novaTarefa = new TarefaEstudo(titulo, descricao, dataAgendamento, disciplina, dataEntrega); // Cria um novo objeto do tipo TarefaEstudo

        tarefaDao.salvar(novaTarefa); // Pede pra camada de persistencia salvar o objeto que acabou de ser criado
    }

    public void adicionarTarefaPessoal(String titulo, String descricao, LocalDate dataAgendamento, String categoria, Optional<LocalDate> prazo) {
        TarefaPessoal novaTarefa = new TarefaPessoal(titulo, descricao, dataAgendamento, categoria, prazo); // Cria um novo objeto do tipo TarefaEstudo

        tarefaDao.salvar(novaTarefa); // Pede pra camada de persistencia salvar o objeto que acabou de ser criado
    }

    // --- METODOS PARA LISTAR TAREFAS ---

    public List<Tarefa> listarTarefasPendentes() {
        
        List<Tarefa> todas = tarefaDao.listarTodas(); // Cria uma nova lista que armazena todas as tarefas existnetes na persistencia

        List<Tarefa> tarefasPendentes = new ArrayList<>(); // Cria uma nova lista pra armazenar apenas as tarefas pendentes
        
        for (Tarefa tarefa : todas) { // Percorre a lista de todas as tarefas (para cada tarefa em todas...)
            
            if (!tarefa.isConcluida()) { // Se a tarefa NÃO estiver concluída...
                
                tarefasPendentes.add(tarefa); // ...vai adicionar ela na lista de pendentes que criamos ali antes do bloco do for
            }
        }
        return tarefasPendentes; // Retorna a lista contendo apenas as tarefas pendentes
    }
    
    public List<Tarefa> listarTarefasConcluidas() {

        List<Tarefa> todas = tarefaDao.listarTodas(); // Cria uma nova lista que armazena todas as tarefas existnetes na persistencia
        List<Tarefa> tarefasConcluidas = new ArrayList<>();
        for (Tarefa tarefa : todas) {
            
            if (tarefa.isConcluida()) { // Se a tarefa ESTIVER concluída...
                
                tarefasConcluidas.add(tarefa); // ...adiciona ela na lista de concluídas
            }
        }
        return tarefasConcluidas;
    }
    
    // --- MÉTODO DE AÇÃO ---

    public boolean concluirTarefa(int indice) {
        
        List<Tarefa> pendentes = listarTarefasPendentes(); // Pega a lista atual de tarefas pendentes para saber qual tarefa o índice representa
        
        if (indice >= 0 && indice < pendentes.size()) { // Verifica se o índice que o usuário digitou é válido
            
            Tarefa tarefaParaConcluir = pendentes.get(indice); // Pega a tarefa correta da lista
            
            tarefaParaConcluir.marcarConcluida(); // Chama o método do próprio objeto para mudar seu estado para 'concluída'
            return true; // Retorna sucesso
        }
        return false; // Retorna falha se o índice for inválido
    }

    // --- MÉTODO DE BUSCA ---
    
    public List<Tarefa> buscarTarefasPorDataDeAgendamento(LocalDate data) { // Busca tarefas pela data de AGENDAMENTO que o usuario pedir
        List<Tarefa> todas = tarefaDao.listarTodas();
        List<Tarefa> tarefasEncontradas = new ArrayList<>();
        for (Tarefa tarefa : todas) {
            
            if (tarefa.getDataAgendamento() != null && tarefa.getDataAgendamento().equals(data)) { // Se a data de agendamento da tarefa for igual à data buscada...
                
                tarefasEncontradas.add(tarefa); // ...adiciona na lista de resultados
            }
        }
        return tarefasEncontradas;
    }
}