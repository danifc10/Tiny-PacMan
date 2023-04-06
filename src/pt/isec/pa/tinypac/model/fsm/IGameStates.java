package pt.isec.pa.tinypac.model.fsm;

public interface IGameStates {
      GameStates getState();

      // transições
      void update();
      boolean startGame();
      IGameStates eatPoint();
      IGameStates eatFruit();
      IGameStates eatPower();
      IGameStates eatGhost();
      IGameStates eatPoint(int x, int y);
      IGameStates wrapZone();
      IGameStates eatAll();
      IGameStates ghostCollision();
      IGameStates restart();
      IGameStates levelUp();
}
