package br.com.alura.LiterAlura.model;

public enum Idiomas {
    PT("pt"),
    EN("en"),
    FR("fr"),
    ES("es");

    private String categoriaGutendex;



    Idiomas(String categoriaGutendex) {
        this.categoriaGutendex = categoriaGutendex;

    }

    public static Idiomas fromString(String text) {
        for (Idiomas categoria : Idiomas.values()) {
            if (categoria.categoriaGutendex.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
