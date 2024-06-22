package br.com.alura.LiterAlura.principal;

import br.com.alura.LiterAlura.Service.ObterDados;
import br.com.alura.LiterAlura.model.*;
import br.com.alura.LiterAlura.repository.AutorRepository;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books?search=";
    private List<Livros> livrosList = new ArrayList<>();
    private AutorRepository repository;


    public Principal(AutorRepository repository) {
        this.repository = repository;
    }

    public void exibirMenuInicial() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    _______________________________
                    Escolha o número da sua opção:
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    6 - Gerar estatística
                    7 - Obter os top 10 mais baixados
                    8 - Buscar pelo nome do autor
                    0 - Sair
                    _______________________________                    
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();


            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 6:
                    gerarEstatistica();
                    break;
                case 7:
                    obterTop10Baixados();
                    break;
                case 8:
                    buscarAutor();
                    break;
                case 0:
                    System.out.println("Até a próxima... :) ");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


    private void buscarLivroPorTitulo() {
        System.out.println("Insira o nome do livro que você deseja procurar:");
        var tituloLivro = leitura.nextLine();
        var endereco = ENDERECO + tituloLivro.replace(" ", "%20");
//        System.out.println(endereco);
        ObterDados extrairDados = new ObterDados(endereco);
        DadosAutor dadosAutor = extrairDados.obterDadosAutor();
        Autor autor = new Autor(dadosAutor);
        DadosLivro dadosLivro = extrairDados.obterDadosLivro();
        Livros livro = new Livros(dadosLivro);
        livrosList.add(livro);
        autor.setLivros(livrosList);
        repository.save(autor);
        System.out.println("----- LIVRO -----");
        System.out.println(livro);
        System.out.println("-----------------");
    }

    private void listarLivrosRegistrados() {
        List<Livros> lista = repository.listarLivrosRegistrados();
        lista.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Autor> listaAutores = repository.listarAutoresRegistrados();
        listaAutores.forEach(System.out::println);
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Digite a ano de busca desejado para listarmos os autores vivos desse ano:");
        int ano = leitura.nextInt();
        leitura.nextLine();
        List<Autor> lista = repository.listarAutoresVivosPorAno(ano);
        lista.forEach(System.out::println);
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Insira o idioma para realizar a busca: \n" +
                "pt - Português \n" +
                "en - Inglês \n" +
                "fr - Francês \n" +
                "es - Espanhol \n"
        );
        String idioma = leitura.nextLine();

        List<Livros> listaPorIdioma = repository.listarLivrosPorIdioma(Idiomas.fromString(idioma));
        if (listaPorIdioma.size() == 0) {
            System.out.println("Não temos livros cadastrados nesse idioma");
        } else {
            listaPorIdioma.forEach(System.out::println);
        }
    }

    private void gerarEstatistica() {
        List<Livros> livros = repository.listarLivrosRegistrados();

        DoubleSummaryStatistics est = livros.stream()
                .filter(l -> l.getDownloads() > 0.0)
                .collect(Collectors.summarizingDouble(Livros::getDownloads));
        System.out.println("Dados:");
        System.out.println("Média: " + Math.round(est.getAverage()));
        System.out.println("O mais baixado: " + Math.round(est.getMax()));
        System.out.println("O menos baixado: " + Math.round(est.getMin()));
        System.out.println("Total de downloads: " + Math.round(est.getSum()));
        System.out.println("Numero total de livros analisados: " + Math.round(est.getCount()));
    }

    private void obterTop10Baixados() {
        List<Livros> top10 = repository.obterTop10Baixados();
        top10.forEach(System.out::println);
    }

    private void buscarAutor() {
        System.out.println("Digite o nome do Autor para busca: ");
        String nomeAutor = leitura.nextLine().toLowerCase();

        List<Autor> listaAutores = repository.listarAutoresRegistrados();

        Optional<Autor> autorEncontrado = listaAutores.stream()
                .filter(a -> a.getNome().toLowerCase().contains(nomeAutor))
                .findFirst();

        if (autorEncontrado.isPresent()) {
            Autor autor = autorEncontrado.get();
            System.out.println(autor);
        } else {
            System.out.println("Autor não encontrado.");
        }

    }
}
