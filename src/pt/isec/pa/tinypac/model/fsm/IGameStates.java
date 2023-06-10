package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.utils.Direction;

public interface IGameStates {
      GameStates getState();

      // transições
      boolean endVulnerableTime();
      boolean setGhostsFree(); // no MOVEMENT
      boolean startGame(); // no INITIAL
      boolean eatPoint(); // no PLAYING e no VULNERABLE
      boolean eatFruit(); // no PLAYING e no VULNERABLE
      boolean eatPower(); // no PLAYING
      boolean eatGhost(); // no VULNERABLE
      boolean ifEatAll(); // no PLAYING
      boolean ghostCollision(); // no PLAYING
      boolean restart(); // no GAME_OVER
      boolean levelUp(); // no WIN
      boolean endGame(); // no GAME_OVER e no WIN
      boolean pauseGame();
      boolean resumeGame();
      void changeDirection(Direction direction);

    void evolve();
}
