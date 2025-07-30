package br.com.vigi.modelo;

import java.time.LocalDate;

public class TarefaEstudo extends Tarefa {

    private String disciplina; //disciplina que a tarefa de estudo pertence
    private LocalDate dataEntrega; //data de entrega da tarefa de estudo

    // Construtor
    public TarefaEstudo(String titulo, String descricao, LocalDate dataAgendamento, String disciplina, LocalDate dataEntrega) {
        super(titulo, descricao, dataAgendamento); // aqui o construtor definido na classe mãe é chamado
        this.disciplina = disciplina;
        this.dataEntrega = dataEntrega;
    }

    public String getDisciplina() { 
        return disciplina; 
    }
    public LocalDate getDataEntrega() { 
        return dataEntrega; 
    }

    @Override
    public String exibirDetalhes() {
        return "[Estudo] " + getTitulo() + ": " + getDescricao() + " (Disciplina: " + this.disciplina +") - Entrega: " + formatarData(this.dataEntrega); //aqui usamos o método protected herdado da classe mãe, que faz a conversão do formato da data pra dia, mês e ano
    }
}