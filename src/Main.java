import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.Gson;

public class Main {
    static class TaxaCambio {
        double conversion_rate;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner leitura = new Scanner(System.in);
        HttpClient cliente = HttpClient.newHttpClient();
        Gson gson = new Gson();

        while (true) {
            System.out.printf("***************************************************%n" +
                    "Seja bem-vindo(a) ao Conversor de Moedas =)%n%n" +
                    "1) Dólar =>> Peso Argentino%n" +
                    "2) Peso Argentino =>> Dólar%n" +
                    "3) Dólar =>> Real Brasileiro%n" +
                    "4) Real Brasileiro =>> Dólar%n" +
                    "5) Dólar =>> Peso Colombiano%n" +
                    "6) Peso Colombiano =>> Dólar%n" +
                    "7) Sair%n" +
                    "Escolha uma opção válida:%n" +
                    "***************************************************%n");

            int buscaOpcao = leitura.nextInt();

            if (buscaOpcao == 7) {
                System.out.printf("Saindo... Até logo!%n");
                break;
            }

            System.out.printf("Digite o valor que deseja converter:%n");
            double valorBusca = leitura.nextDouble();

            String moedaOrigem = "";
            String moedaDestino = "";

            switch (buscaOpcao) {
                case 1:
                    moedaOrigem = "USD";
                    moedaDestino = "ARS";
                    break;
                case 2:
                    moedaOrigem = "ARS";
                    moedaDestino = "USD";
                    break;
                case 3:
                    moedaOrigem = "USD";
                    moedaDestino = "BRL";
                    break;
                case 4:
                    moedaOrigem = "BRL";
                    moedaDestino = "USD";
                    break;
                case 5:
                    moedaOrigem = "USD";
                    moedaDestino = "COP";
                    break;
                case 6:
                    moedaOrigem = "COP";
                    moedaDestino = "USD";
                    break;
                default:
                    System.out.printf("Opção inválida! Tente novamente.%n%n");
                    continue;
            }

            String url = "https://v6.exchangerate-api.com/v6/27d8926fc76a3140d4e0c968/pair/" + moedaOrigem + "/" + moedaDestino;
            HttpRequest requisicao = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

            TaxaCambio taxa = gson.fromJson(resposta.body(), TaxaCambio.class);

            double valorFinal = valorBusca * taxa.conversion_rate;

            System.out.printf("Valor %.2f [%s] corresponde a %.2f [%s]%n%n", valorBusca, moedaOrigem, valorFinal, moedaDestino);
        }

        leitura.close();
    }
}
