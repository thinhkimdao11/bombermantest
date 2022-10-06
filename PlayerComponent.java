package mangobomb.bombermango;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayerComponent extends Component {
    public CellMoveComponent cell;
    public AStarMoveComponent astar;

    private int maxBombs = 1;
    private int bombsPlaced = 0;

    public void increaseMaxBombs() {
        maxBombs++;
    }

    public void placeBomb() {
        if (bombsPlaced == maxBombs) {
            return;
        }

        bombsPlaced++;

        Entity bomb = spawn("Bomb", new SpawnData(cell.getCellX() * 48, cell.getCellY() * 48).put("radius", HelloApplication.SCALED_SIZE / 2));

        getGameTimer().runOnceAfter(() -> {
            bomb.getComponent(Bomb.class).explode();
            bombsPlaced--;
        }, Duration.seconds(2));
    }

    private AnimatedTexture texture;
    private AnimationChannel PlayerLeft, PlayerRight, PlayerIdle;

    public PlayerComponent() {

    }

    @Override
    public void onAdded() {
        PlayerRight = new AnimationChannel(List.of(
                FXGL.image("sprites/player_right.png", 70, 48),
                FXGL.image("sprites/player_right_1.png", 70, 48),
                FXGL.image("sprites/player_right_2.png", 70, 48)
        ), Duration.seconds(0.25));
        PlayerIdle = new AnimationChannel(List.of(
                FXGL.image("sprites/player_down.png", 70, 48),
                FXGL.image("sprites/player_down_1.png", 70, 48),
                FXGL.image("sprites/player_down_2.png", 70, 48)
        ), Duration.seconds(0.25));
        PlayerLeft = new AnimationChannel(List.of(
                FXGL.image("sprites/player_left.png", 70, 48),
                FXGL.image("sprites/player_left_1.png", 70, 48),
                FXGL.image("sprites/player_left_2.png", 70, 48)
        ), Duration.seconds(0.25));

        texture = new AnimatedTexture(PlayerLeft);
        texture.loop();
        //entity.getTransformComponent().setScaleOrigin(new Point2D(24, 24));
        entity.getViewComponent().addChild(texture);
        entity.setScaleOrigin(new Point2D(24,24));
        entity.setScaleX(1);
        entity.setScaleY(1);
    }

    @Override
    public void onUpdate(double tpf) {
        if (cell.isMoving() || cell.isMovingRight()) {
            if (texture.getAnimationChannel() != PlayerLeft) {
                texture.loopAnimationChannel(PlayerLeft);
            }
        } else {
            if (texture.getAnimationChannel() != PlayerIdle) {
                texture.loopAnimationChannel(PlayerIdle);
            }
        }

    }

    public void moveLeft() {
        getEntity().setScaleX(1);
        astar.moveToLeftCell();
    }
    public void moveRight() {
        getEntity().setScaleX(-1);
        astar.moveToRightCell();
    }
    public void moveUp() {
        astar.moveToUpCell();
    }
    public void moveDown() {
        astar.moveToDownCell();
    }


        String gameSong = new String("gameaudio.wav");
        Music gameMusic = FXGL.getAssetLoader().loadMusic(gameSong);
//    Media media = new Media("gameaudio.wav");
//    MediaPlayer mediaPlayer = new MediaPlayer(media);
//    MediaView mediaView = new MediaView(mediaPlayer);
        boolean playing = false;


    public void music() {
        if (!playing) {
            FXGL.getAudioPlayer().playMusic(gameMusic);
//            mediaPlayer.play();
            playing = true;
        } //else {
////            getAudioPlayer().stopMusic(gameMusic);
////            mediaPlayer.pause();
//            playing = false;
//        }

//        FXGL.getAudioPlayer().playMusic(gameMusic);
//        FXGL.getAudioPlayer().stopMusic(gameMusic);

//        Music gamemusic = getAssetLoader().loadMusic("gameaudio.wav");
//        getAudioPlayer().playMusic(gamemusic);
//        Boolean playing = false;
//
//
//        if(!playing)
//        {
//            getAudioPlayer().playMusic(gamemusic);
//            playing = true;
//        }
//        else{
//            getAudioPlayer().stopMusic(gamemusic);
//            playing = false;
//        }

    }
}
