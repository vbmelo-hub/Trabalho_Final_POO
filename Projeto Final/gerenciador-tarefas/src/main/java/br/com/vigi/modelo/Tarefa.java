package br.com.vigi.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public abstract class Tarefa { //classe abstrata 

    private String titulo; //titulo da tarefa
    private String descricao; //descrição da tarefa
    private boolean concluida; //boolean pra dizer se a tarefa foi concluida ou não
    private LocalDate dataAgendamento; //daata que a tarefa foi cadastrada

    // Construtor da classe base (que será herdado pelas filhas), atraves de "super"
    public Tarefa(String titulo, String descricao, LocalDate dataAgendamento) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataAgendamento = dataAgendamento;
        this.concluida = false;
    }

    // Getters
    public String getTitulo() {
        return titulo; 
    }
    public String getDescricao() {
        return descricao; 
    }
    public boolean isConcluida() {
        return concluida; 
    }
    public LocalDate getDataAgendamento() {
        return dataAgendamento; 
    }

    // Método que formata as datas pro padrão brasileiro, ele está como protected porque apenas as filhas de Tarefa poderão utilizar
    protected String formatarData(LocalDate data) {
        if (data == null) return "";
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // Método pra modificar o estado da tarefa (mudar pra cocluida)
    public void marcarConcluida() {
        this.concluida = true;
    }

    // Método abstrato que força casa classe filha a immplementar o método e "personalizar" a sua maneira
    public abstract String exibirDetalhes();
}