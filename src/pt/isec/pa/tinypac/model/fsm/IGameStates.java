package pt.isec.pa.tinypac.model.fsm;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public interface IGameStates {
      GameStates getState();

      // transições
      void update(Terminal terminal) throws IOException;
      boolean startGame();
      boolean eatPoint();
      boolean eatFruit();
      boolean eatPower();
      boolean eatGhost();
      IGameStates eatPoint(int x, int y);
      IGameStates wrapZone(int x, int y);
      IGameStates eatAll();
      IGameStates ghostCollision();
      IGameStates restart();
      IGameStates levelUp();
}
