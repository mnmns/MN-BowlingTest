package com.mn.bowling;
import java.util.*;

import static com.mn.bowling.BowlingConstants.*;

/**
 * MN-Bowling Scoring system
 */
public class MNBowlingGame implements BowlingGame {

    ArrayList<BowlingFrame> gameFrames = new ArrayList<BowlingFrame>();

    public void roll (int noOfPins) {

        System.out.println("frame" + getCurrentFrameNo() + ", rolled " + noOfPins);

        doSpecialScoresForPreviousFrame(noOfPins);

        getCurrentFrame().frameScore += noOfPins;

        if (isFirstRollOfFrame()) {
            getCurrentFrame().roll1 = new Roll();
            getCurrentFrame().roll1.knockedDownPins = noOfPins;
            // a strike can only occur on the 1st roll
            getCurrentFrame().roll1.isStrike = this.isStrike(noOfPins);
            displayScore();
            if (this.isStrike(noOfPins)) {
                advanceFrame();
            }
        }
        else {
            // this is the 2nd roll
            getCurrentFrame().roll2 = new Roll();
            getCurrentFrame().roll2.knockedDownPins = noOfPins;
            // a spare can only occur on the 2nd roll
            getCurrentFrame().roll2.isSpare = this.isSpare(noOfPins);
            displayScore();

            advanceFrame();
        }
    }

    private void doSpecialScoresForPreviousFrame(int noOfPins) {
        if (gameFrames.size() > 1) { // At least one previous frame
            // any additional score should be added to the previous frame
            // increment that frame score by score of both rolls on the current frame for a strike on the previous frame
            // increment that frame score by score of the 1st roll on the current frame for a spare on the previous frame
            if(
                    getPreviousFrame().roll1.isStrike ||
                    getPreviousFrame().roll2.isSpare && isFirstRollOfFrame()
            ) {
                getPreviousFrame().frameScore += noOfPins;
            }
        }
    }

    public BowlingFrame getCurrentFrame() {
        BowlingFrame currentFrame = gameFrames.get(gameFrames.size() - 1);
        return currentFrame;
    }

    private int getCurrentFrameNo() {
        return gameFrames.size();
    }

    public BowlingFrame getPreviousFrame() {
        return gameFrames.get(gameFrames.size() - 2);
    }

    private boolean isFirstRollOfFrame() {
        return getCurrentFrame().roll1 == null;
    }

    private boolean isSpare(int noOfPins) {
        return getCurrentFrame().roll1.knockedDownPins + noOfPins == perFramePins;
    }

    private boolean isStrike(int noOfPins) {
        return noOfPins == perFramePins;
    }

    /**
     * Activate the 1st frame of the game
     */
    public void startGame() {
        advanceFrame();
    };

    public void advanceFrame() {
        if (gameFrames.size() < perGameFrames) {
            BowlingFrame newFrame = new BowlingFrame();
            gameFrames.add(newFrame);
        }
        else completeGame();
    }

    private void completeGame() {
        // TO DO:
    }

    public int score() {
        int totalGameScore = 0;

        for (int i=0; i <= gameFrames.size() - 1; i++) {
            totalGameScore += gameFrames.get(i).frameScore;
        }

        return totalGameScore;
    }

    public void simulateGame() {
        //Frame 1- strike
        roll(10);
        //Frame2
        roll(3);
        roll(0);
        //Frame3 - wipeout
        roll(0);
        roll(0);
        //Frame 4 - spare
        roll(4);
        roll(6);
        //Frame 5
        roll(2);
        roll(6);
        //Frame 6 - spare
        roll(5);
        roll(5);
        //Frame 7 - strike
        roll(10);
        //Frame 8 - strike
        roll(10);
        //Frame 9 - spare
        roll(2);
        roll(8);
        //Frame 10 -spare
        roll(0);
        roll(10);
    }

    private void displayScore() {
        System.out.println("Total score - " + score());
    }
}
