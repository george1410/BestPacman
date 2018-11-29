package pacman;

import javafx.animation.AnimationTimer;

interface Moveable {
    AnimationTimer createAnimation(String s);
    void checkDoorway();
}
