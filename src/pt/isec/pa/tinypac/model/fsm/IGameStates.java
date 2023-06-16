package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.utils.Direction;

public interface IGameStates {
      GameStates getState();

      // transições
      boolean pauseGame();
      void changeDirection(Direction direction);

      void evolve();
}
