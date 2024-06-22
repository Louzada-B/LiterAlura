package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.OptionalDouble;

@Entity
@Table(name = "livros")
public class Livros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idiomas idiomas;
    private Double downloads;
    @ManyToOne
    private Autor autor;


    public Livros() {
    }

    public Livros(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idiomas = Idiomas.fromString(dadosLivro.idiomas());
        this.downloads = OptionalDouble.of(Double.valueOf(dadosLivro.downloads())).orElse(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idiomas getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idiomas idiomas) {
        this.idiomas = idiomas;
    }

    public Double getDownloads() {
        return downloads;
    }

    public void setDownloads(Double downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return  "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNome() + "\n" +
                "Idiomas: " + idiomas + "\n" +
                "Número de downloads: " + downloads + "\n";


    }
}
