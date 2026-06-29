package common;

import java.util.List;

import test.Direction;
import test.Enemy; 

public interface GameMap {
    int getStageNumber();
    double getPacX();
    double getPacY();
    int[][] getMap();
    boolean isWaitingStart();
    Direction getPlayerDirection();
    List<Enemy> getEnemies(); 
}