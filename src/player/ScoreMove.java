package player;

class ScoreMove {
	int score;
	Move move;

	ScoreMove(int score, Move move) {
		this.score = score;
		this.move = move;
	}

	public String toString() {
		return "Move: " + move + "\n Score: " + score;
	}
}
