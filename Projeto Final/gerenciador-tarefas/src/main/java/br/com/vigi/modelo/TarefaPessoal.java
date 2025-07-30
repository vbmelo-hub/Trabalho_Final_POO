package br.com.vigi.modelo;

import java.time.LocalDate;
import java.util.Optional;

public class TarefaPessoal extends Tarefa {

    private String categoria; //categoria que a tarefa pessoal utiliza
    private Optional<LocalDate> prazo; //prazo para conclusão da tarefa, seu preenchimento é opcional, por isso usamos optional, pq pesquisando como fazer algo ser realmente opcional, achamos essa, digamos que, "classe" do proprio java, ela faz com que um valor possa ou não existir

    // Construtor 
    public TarefaPessoal(String titulo, String descricao, LocalDate dataAgendamento, String categoria, Optional<LocalDate> prazo) {
        super(titulo, descricao, dataAgendamento); // aqui o construtor definido na classe mãe é chamado
        this.categoria = categoria;
        this.prazo = prazo;
    }
    
    // Getters
    public String getCategoria() { 
        return categoria; 
    }
    public Optional<LocalDate> getPrazo() { 
        return prazo; 
    }

    @Override
    public String exibirDetalhes() {
         String detalhes = "[Pessoal] " + getTitulo() + ": " + getDescricao() + " (Categoria: " + this.categoria + ")"; //define uma string pra armazenar a concatenação das informações, isso possibilita que no if abaixo, ela seja usada novamente, caso entre no if

        if (prazo.isPresent()) {
            LocalDate dataDoPrazo = prazo.get();
            detalhes = detalhes + " - Prazo: " + formatarData(dataDoPrazo); //adiciona o prazo, se o atributo opcional "prazo" estiver presente, à string que definimos anteriormente
        }

        return detalhes; //retorna a string de acrodo com o if
    }
}