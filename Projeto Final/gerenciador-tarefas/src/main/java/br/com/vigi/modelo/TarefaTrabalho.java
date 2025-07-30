package br.com.vigi.modelo;

import java.time.LocalDate;
import java.util.Optional;

public class TarefaTrabalho extends Tarefa {

    private LocalDate prazo; // prazo de entrega da tarefa do trabalho
    private Optional<String> projeto; // projeto o qual a tarefa pode ou não estar vinculada, nem toda tarefa tem um projeto vinculado, mas algumas tem

    // Construtor 
    public TarefaTrabalho(String titulo, String descricao, LocalDate dataAgendamento, LocalDate prazo, Optional<String> projeto) {
        super(titulo, descricao, dataAgendamento); // aqui o construtor definido na classe mãe é chamado
        this.prazo = prazo;
        this.projeto = projeto;
    }

    // Getters
    public LocalDate getPrazo() { 
        return prazo; 
    }
    public Optional<String> getProjeto() { 
        return projeto; 
    }

    @Override
    public String exibirDetalhes() {
        String detalhes = "[Trabalho] " + getTitulo() + " - Prazo: " + formatarData(prazo); //define uma string pra armazenar a concatenação das informações, isso possibilita que no if abaixo, ela seja usada novamente, caso entre no if. Além disso, utiliza o método protected de Tarefa.java para formatar a data

        if (projeto.isPresent()) {
            detalhes = detalhes + " (Projeto: " + projeto.get() + ")";
        } //adiciona o prazo, se o atributo opcional "projeto" estiver presente, à string que definimos anteriormente

        return detalhes; //retorna a string de acordo com o if acima
    }
}