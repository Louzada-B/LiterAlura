package br.com.alura.LiterAlura.repository;

import br.com.alura.LiterAlura.model.Autor;
import br.com.alura.LiterAlura.model.Idiomas;
import br.com.alura.LiterAlura.model.Livros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeContainingIgnoreCase(String nomeAutor);

    @Query("SELECT l FROM Livros l")
    List<Livros> listarLivrosRegistrados();

    @Query("SELECT a FROM Autor a")
    List<Autor> listarAutoresRegistrados();

    @Query("SELECT a FROM Autor a WHERE :ano BETWEEN a.anoDeNascimento AND a.anoDeFalecimento")
    List<Autor> listarAutoresVivosPorAno(int ano);

    @Query("select l from Livros l where l.idiomas = :idioma")
    List<Livros> listarLivrosPorIdioma(Idiomas idioma);

    @Query("Select l from Livros l order by l.downloads desc limit 10")
    List<Livros> obterTop10Baixados();
}