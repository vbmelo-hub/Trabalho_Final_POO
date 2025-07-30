package br.com.vigi.persistencia;

import java.util.ArrayList;
import java.util.List;

import br.com.vigi.modelo.Tarefa;

// Centraliza o acesso aos dados que serão inseridos à nossa lista em memória (importante lembrar que isso não é um banco de dados, os dados não são permanentes na memória, utilizamos um array list pra armazenar os dados, então eles duram enquanto o programa estiver em execução)
public class TarefaDao {

    private static final List<Tarefa> tarefas = new ArrayList<>();

    
    public void salvar(Tarefa tarefa) { // Salva uma tarefa na lista
        tarefas.add(tarefa);
    }

    
    public List<Tarefa> listarTodas() { // Retorna a lista com todas as tarefas
        return tarefas;
    }
}