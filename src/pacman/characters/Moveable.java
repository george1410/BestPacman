package pacman.characters;

import javafx.animation.AnimationTimer;

interface Moveable {
    AnimationTimer createAnimation(String s);
    void checkDoorway();
}
