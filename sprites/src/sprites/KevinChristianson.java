package sprites;

/**
 * Created by kevinchristianson on 5/22/17.
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.transform.Rotate;


public class KevinChristianson extends Sprite {
    private AudioClip audioClip;
    private ImageView imageView;
    private Image open;
    private Image close;
    private boolean isOpen;
    private int animationCounter;
    private boolean lookRight;

    public KevinChristianson() {
        this.open = new Image(getClass().getResourceAsStream("/res/pacman_open.jpg"));
        this.close = new Image(getClass().getResourceAsStream("/res/pacman_closed.png"));
        this.imageView = new ImageView();
        this.imageView.setImage(close);
        this.getChildren().add(this.imageView);
        this.audioClip = new AudioClip(getClass().getResource("/res/waka_waka.mp3").toString());
        this.isOpen = false;
        this.animationCounter = 0;
        this.lookRight = true;
    }

    public void setSize(double width, double height) {
        super.setSize(width, height);
        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(height);
    }


    public void setDirection() {
        if(this.getVelocity().getX() > 0 && !this.lookRight) {
            this.getTransforms().add(new Rotate(180, this.getSize().getX() / 2, this.getSize().getY() / 2));
            this.lookRight = true;
        }else if(this.getVelocity().getX() < 0 && this.lookRight){
            this.getTransforms().add(new Rotate(180, this.getSize().getX() / 2, this.getSize().getY() / 2));
            this.lookRight = false;
        }
    }


    @Override
    public void step(){
        setDirection();
        this.animationCounter++;
        if(this.animationCounter > 10) {
            if (this.isOpen) {
                this.imageView.setImage(close);
                this.isOpen = false;
            } else {
                this.imageView.setImage(open);
                this.isOpen = true;
            }
            this.getChildren().remove(this.imageView);
            this.getChildren().add(this.imageView);
            this.animationCounter = 0;
        }
        super.step();

    }

    @Override
    public void makeSound() {
        this.audioClip.play();
    }
}
