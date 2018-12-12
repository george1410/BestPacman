package pacman.models.characters;

import javafx.animation.AnimationTimer;

/**
 * Interface for any elements of the game that are able to move around the maze.
 */
interface Moveable {
    AnimationTimer createAnimation(String s);
    void checkDoorway();
}
