import rand_AI.MachinePlayer;
import player.*;
import board.*;
public class NetworkRunner {
	public static String runGame(Player one, Player two, Board board) {
		int i = 0;
		Player curr;
		Player other;
		int color;
		while (true) {
			if (i%2 == 0) {
				curr = one;
				other = two;
				color = Board.WHITE;
			} else {
				curr = two;
				other = one;
				color = Board.BLACK;
			}
			Move m = curr.chooseMove();
			board.makeMove(m, color);
			other.opponentMove(m);
			i++;
			if (board.hasNetwork(Board.WHITE)) {
				return "One";
			} else if (board.hasNetwork(Board.BLACK)) {
				return "Two";
			}
		}
	}

	public static double winPercentage() {
		int wins = 0;
		int games = 0;
		for (int i = 0; i <= 25; i++) {
			String s = runGame(new player.MachinePlayer(Board.WHITE), new rand_AI.MachinePlayer(Board.BLACK), new Board());
			if (s == "One") {
				wins += 1;
			}
			games += 1;
		}

		for (int i = 0; i <= 25; i++) {
			String s = runGame(new rand_AI.MachinePlayer(Board.WHITE), new player.MachinePlayer(Board.BLACK), new Board());
			if (s == "Two") {
				wins += 1;
			}
			games += 1;
		}
		return ((double) wins)/games;
	}

	public static void main(String[] args) {
		// System.out.println("The percentage of wins for our MachinePlayer is " + winPercentage());
		System.out.println("One game: \n" + runGame(new player.MachinePlayer(Board.WHITE), new rand_AI.MachinePlayer(Board.BLACK), new Board()));
	}
}