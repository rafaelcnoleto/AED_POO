// Arquivo: ConsoleUtils.java
import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String lerTexto(String mensagem) {
        System.out.print(mensagem + ": ");
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.println(">> Erro: O campo não pode ser vazio.");
            System.out.print(mensagem + ": ");
            input = scanner.nextLine().trim();
        }
        return input;
    }

    public static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem + ": ");
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(">> Erro: Digite apenas números válidos.");
            }
        }
    }

    public static void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }
}