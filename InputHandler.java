package mangobomb.bombermango;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;

import static mangobomb.bombermango.HelloApplication.*;

public class InputHandler {

    static UserAction moveUp = new UserAction("moveUp") {
        @Override
        protected void onActionBegin() {
            HelloApplication.playerComponent.moveUp();
        }
    };

    static UserAction moveDown = new UserAction("moveDown") {
        @Override
        protected void onActionBegin() {
            HelloApplication.playerComponent.moveDown();
        }
    };

    static UserAction moveRight = new UserAction("moveRight") {
        @Override
        protected void onActionBegin() {
            HelloApplication.playerComponent.moveRight();
        }
    };

    static UserAction moveLeft = new UserAction("moveLeft") {
        @Override
        protected void onActionBegin() {
            HelloApplication.playerComponent.moveLeft();
        }
    };

    static UserAction implantBomb = new UserAction("implantBomb") {
        @Override
        protected void onActionBegin() {
            HelloApplication.playerComponent.placeBomb();
        }
    };
}