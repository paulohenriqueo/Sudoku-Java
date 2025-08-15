package br.com.dio;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.dio.model.Board;
import br.com.dio.model.Space;
import br.com.dio.util.BoardTemplate;

public class App {

    private final static Scanner scanner = new Scanner(System.in);

    private static Board board;

    private final static int BOARD_LIMIT = 9;
    public static void main(String[] args) throws Exception {
        
        final var positions = Stream.of(args)
                .collect(Collectors.toMap(
                    k -> k.split(";")[0],
                    v -> v.split(";")[1] 
                ));

        var option = -1;
        while (true) {
            System.out.println("Selecione uma das opções abaixo:");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar o Sudoku");
            System.out.println("5 - Verificar o Status do Sudoku");
            System.out.println("6 - Limpar Sudoku");
            System.out.println("7 - Finalizar o jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option) {
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showSudoku();
                case 5 -> checkSudokuStatus();
                case 6 -> clearSudoku();
                case 7 -> finishGame();
                case 8 ->   System.exit(0);
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }

    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("Já existe um jogo em andamento. Finalize o jogo atual antes de iniciar um novo.");
        }

        List <List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s, %s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("Jogo iniciado com sucesso!");

    }

    private static void inputNumber() {
        if (isNull(board)) {
            System.out.println("Nenhum jogo iniciado. Por favor, inicie um novo jogo.");
            return;
        }

        System.out.println("Digite a coluna em que o numero será colocado (0-8):");
        var column = runUntilGetValidNumber(0, 8);
        System.out.println("Digite a linha em que o numero será colocado (0-8):");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número que deseja colocar na posição (%s, %s): \n", row, column);
        var value = runUntilGetValidNumber(1, 9);

        if (!board.changeValue(column, row, value)) {
            System.out.printf("A posição (%s, %s) tem um valor fixo.\n", row, column);
        }
        
    }

    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println("Nenhum jogo iniciado. Por favor, inicie um novo jogo.");
            return;
        }

        System.out.println("Digite a coluna em que o numero será colocado (0-8):");
        var column = runUntilGetValidNumber(0, 8);
        System.out.println("Digite a linha em que o numero será colocado (0-8):");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número que deseja colocar na posição (%s, %s): \n", row, column);

        if (!board.clearValue(column, row)) {
            System.out.printf("A posição (%s, %s) tem um valor fixo.\n", row, column);
        }

    }

    private static void showSudoku() {
        if (isNull(board)) {
            System.out.println("Nenhum jogo iniciado. Por favor, inicie um novo jogo.");
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var column: board.getSpaces()) {
                
                args[argPos++] = "" + ((isNull(column.get(i).getActual())) ? " " : column.get(i).getActual());

            }
        }

        System.out.println("Seu Sudoku:");
        System.out.printf((BoardTemplate.BOARD_TEMPLATE) + "\n", args);
    
    }

    private static void finishGame() {
       
    
    }

    private static void clearSudoku() {
        
        
    }

    private static void checkSudokuStatus() {
        
        
    }

    
    
    private static int runUntilGetValidNumber(final int min, final int max){
        var current = scanner.nextInt();
        while (current < min || current > max) {
            System.out.printf("Informe um número válido entre %d e %d: ", min, max);
            current = scanner.nextInt();
            
        }
        return current;
    }
    
}
