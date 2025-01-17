import java.util.Scanner;
import java.util.Arrays;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    Scanner scan = new Scanner(System.in);

    boolean[] matrix = new boolean[10];
    Arrays.fill(matrix, false);

    String cleanBoard = 
      " 1 | 2 | 3 \n" +
      "-----------\n" +
      " 4 | 5 | 6 \n" +
      "-----------\n" +
      " 7 | 8 | 9 \n";    
    String board = new String(cleanBoard);

    String[] winSequencies = {
      "147", "258", "369",  // COLS
      "123", "456", "789",  // ROWS
      "753", "357", "159"   // CROSS
    };

    String[] playerSequence = {"", ""};

    byte round = 1;
    boolean quit = false;
    byte roundCounter = 1;

    while (!quit) {

      print("\n==== JOGO DA VELHA ====\n");
      print(board + "\n");
      print("> VEZ DO JOGADOR: " + round + "\n");
      print("\n(digite 10 para quitar.)");
      print("*digite o numero onde quer marcar o board: ");
      if (!scan.hasNextInt()) {
        print("\n#Message\n@root: isso não é um numero né po");
        continue;
      }

      int input = scan.nextInt();
      if (input > 10 && input < 1) continue;
      if (input == 10) {quit = true; continue;}

      char choice = (char) ('0' + input);
      playerSequence[round - 1] = playerSequence[round - 1] + choice; 
      if (board.indexOf(choice) == -1) {
        print("\n![ALERTA] Esta posição já foi marcada. Ganhou uma nova chance manézão ...");
        Thread.sleep(4000);clear();continue;
      }

      print("voce escolheu " + choice);

      board = board.replace(choice, round == 1 ? 'X' : 'O');
      roundCounter++;
      boolean winner = false;

      for (String seq : winSequencies) {
        // DEBUG
        // print(seq + " -> " + playerSequence[round - 1] + " -- " + (playerSequence[round - 1] == seq));
        // Thread.sleep(200);

        char[] charSeq = playerSequence[round - 1].toCharArray(); Arrays.sort(charSeq);
        String sortSeq = new String(charSeq);
        char[] charWinSeq = seq.toCharArray(); Arrays.sort(seq.toCharArray());
        String sortWinSeq = new String(charWinSeq);

        if (!sortSeq.equals(sortWinSeq)) continue;

        clear();
        print("\n> VENCEDORRRRR!!!!!! PLAYER '" + round + "' Venceu a partida!'\n");
        print(board);
        winner = true; quit = true;

        Thread.sleep(4000);
        clear();
        break;
      }

      if (winner) continue;
      if (roundCounter == 10 && !winner) {
        print("\n> EMPATE! Deu velha...Recomeçando ...");
        roundCounter = 0;
        playerSequence[0] = ""; playerSequence[1] = "";
        Thread.sleep(3000); clear(); continue;
      } 

      round = (round == 1) ? (byte) 2 : (byte) 1;
      Thread.sleep(1000);
      clear();
    }

    scan.close();
  }

  public static void print(String text) {
    System.out.println(text);
  }

  public static void clear() {
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
