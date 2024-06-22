package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoDeNascimento;
    private Integer anoDeFalecimento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livros> livros = new ArrayList<>();


    public Autor() {
    }

    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome();

        try {
            this.anoDeNascimento = Integer.parseInt(dadosAutor.anoDeNascimento());
            System.out.println(this.anoDeNascimento);
        } catch (NumberFormatException ex) {
            this.anoDeNascimento = null;
        }

        try {
            this.anoDeFalecimento = Integer.parseInt(dadosAutor.anoDeFalecimento());

        } catch (NumberFormatException ex) {
            this.anoDeFalecimento = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Integer anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public Integer getAnoDeFalecimento() {
        return anoDeFalecimento;
    }

    public void setAnoDeFalecimento(Integer anoDeFalecimento) {
        this.anoDeFalecimento = anoDeFalecimento;
    }

    public List<Livros> getLivros() {
        return livros;
    }

    public void setLivros(List<Livros> livros) {
        livros.forEach((l -> l.setAutor(this)));
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "----- AUTOR -----" + "\n" +
                "Autor: " + nome + "\n" +
                " Ano de nascimento= " + anoDeNascimento + "\n" +
                " Ano de falecimento= " + anoDeFalecimento + "\n" +
                " livros= " + livros + "\n";



    }
}
