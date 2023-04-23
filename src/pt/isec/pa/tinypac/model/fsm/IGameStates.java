package pt.isec.pa.tinypac.model.fsm;

public interface IGameStates {
      GameStates getState();

      // transições
      //void update(Terminal terminal) throws IOException;
      boolean endVulnerableTime();
      boolean setGhostsFree(); // no MOVEMENT
      boolean checkIfGhostsOut(); // no GHOSTS_OUT
      boolean startGame(int direction); // no INITIAL
      boolean eatPoint(); // no PLAYING e no VULNERABLE
      boolean eatFruit(); // no PLAYING e no VULNERABLE
      boolean eatPower(); // no PLAYING
      boolean eatGhost(); // no VULNERABLE
      boolean wrapZone(int x, int y); // NO PLAYING e no VULNERABLE
      boolean ifEatAll(); // no PLAYING
      boolean ghostCollision(); // no PLAYING
      boolean restart(); // no GAME_OVER
      boolean levelUp(); // no WIN
      boolean endGame(); // no GAME_OVER e no WIN
      boolean pauseGame();
      boolean resumeGame();
      boolean setPacManNewDirection(int direction);
      void checks();
}
