package com.mn.bowling;

public class Main {

    private MNBowlingGame game;

    public static void main(String[] args) {
        // method for total-score calculation and for rolls
        MNBowlingGame game = new MNBowlingGame();
        //One must have to move forward from first frame in order to start new game
        game.startGame();
        game.simulateGame();
    }

	public MNBowlingGame getGame() {
		return game;
	}

	public void setGame(MNBowlingGame game) {
		this.game = game;
	}
}
