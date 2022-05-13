package ca.uwaterloo.cs349;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.ImagePattern;

import static java.lang.Math.abs;

public class Snake extends Application {

    ArrayList<Float> ball_x =  new ArrayList<Float>();
    ArrayList<Float> ball_y =  new ArrayList<Float>();
    ArrayList<Float> prev_x = new ArrayList<Float>();
    ArrayList<Float> prev_y = new ArrayList<Float>();
    ArrayList<Circle> balls = new ArrayList<>();
    ArrayList<Circle> level_one_fruit = new ArrayList<>();
    ArrayList<Float> level_one_fruit_x = new ArrayList<Float>();
    ArrayList<Float> level_one_fruit_y = new ArrayList<Float>();
    Random rand = new Random();
    //1 is right, 2 is down, 3 is left, 4 is up
    int ball_dir = 1;
    int count_down = 1830;
    float ball_radius = 20;
    float dx = 1.0f, dy = 0.0f, speed = 1.0f;
    boolean change_dir_right = false;
    boolean change_dir_left = false;
    boolean green = false;
    boolean pause = false;
    boolean timer_start = false;
    int level = 0;
    int num_fruit = 0;
    int score = 0;
    Group root_0 = new Group();
    Group root = new Group();
    Group root2 = new Group();
    Group root3 = new Group();
    Group root4 = new Group();
    Scene scene, scene1, scene2,scene3,scene4;
    enum SCENES {SCENE, SCENE1, SCENE2,SCENE3,SCENE4};
    // Create a canvas as a drawing surface
    final Canvas canvas_0 = new Canvas(1280,800);
    final Canvas canvas = new Canvas(1280,800);
    final Canvas canvas2 = new Canvas(1280,800);
    final Canvas canvas3 = new Canvas(1280,800);
    final Canvas canvas4 = new Canvas(1280,800);
    Image image_apple = new Image("apple.jpg", 876, 800, true, true);
    Image up = new Image("up.png", 876, 800, true, true);
    Image down = new Image("down.png", 876, 800, true, true);
    Image right = new Image("right.png", 876, 800, true, true);
    Image left = new Image("left.png", 876, 800, true, true);

    @Override
    public void start(Stage stage) {
        // scene one
        Image image = new Image("hero.png", 876, 800, true, true);
        ImageView imageView = new ImageView(image);

        scene = new Scene(root_0, 1200, 800,Color.BLACK);
        scene1 = new Scene(root, 1280, 800, Color.BLACK);
        scene2 = new Scene(root2, 1280, 800, Color.BLACK);
        scene3 = new Scene(root3, 1280, 800, Color.BLACK);
        scene4 = new Scene(root4, 1280, 800, Color.BLACK);

        root_0.getChildren().add(canvas_0);
        GraphicsContext gc = canvas_0.getGraphicsContext2D();
        gc.setLineWidth(5);
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.YELLOWGREEN);
        gc.setFont(new Font("TimesRoman",30));
        gc.fillText("Name: David Kong\nStudent ID: 20759072",10,40);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("   Control:  ",550,270);
        gc.fillText("Press s to start the game",550,310);
        gc.fillText("Press <- or -> to turn the snake in the direction indicated",550,350);
        gc.fillText("Press p to pause and un-pause the game",550,390);
        gc.fillText("Press r to reset to the splash screen",550,430);
        gc.fillText("Press 1,2 or 3 to enter the corresponding level",550,470);
        gc.fillText("Press q to quit and display the high score screen",550,510);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.S) {
                level_reset(1);
                level = 1;
                timer_start = true;
                setScene(stage, SCENES.SCENE1);
            } else if (event.getCode() == KeyCode.DIGIT1) {
                level_reset(1);
                level = 1;
                setScene(stage, SCENES.SCENE1);
            } else if (event.getCode() == KeyCode.DIGIT2) {
                level_reset(2);
                level = 2;
                setScene(stage, SCENES.SCENE2);
            } else if (event.getCode() == KeyCode.DIGIT3){
                level_reset(3);
                level = 3;
                setScene(stage, SCENES.SCENE3);
            } else if (event.getCode() == KeyCode.Q){
                pause = true;
                level = 4;
                setScene(stage, SCENES.SCENE4);
            }
        });

        scene1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                change_dir_right = true;
            } else if (event.getCode() == KeyCode.LEFT) {
                change_dir_left = true;
            } else if (event.getCode() == KeyCode.DIGIT2) {
                level_reset(2);
                level = 2;
                setScene(stage, SCENES.SCENE2);
            } else if (event.getCode() == KeyCode.DIGIT3){
                level_reset(3);
                level = 3;
                setScene(stage, SCENES.SCENE3);
            } else if (event.getCode() == KeyCode.P && pause == false){
                    pause = true;
            } else if (event.getCode() == KeyCode.P && pause == true){
                pause = false;
            } else if (event.getCode() == KeyCode.R){
                setScene(stage, SCENES.SCENE);
                pause = true;
            } else if (event.getCode() == KeyCode.Q){
                pause = true;
                level = 4;
                setScene(stage, SCENES.SCENE4);
            }
        });

        scene2.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DIGIT1) {
                level_reset(1);
                level = 1;
                setScene(stage, SCENES.SCENE1);
            } else if (event.getCode() == KeyCode.DIGIT3){
                level_reset(3);
                level = 3;
                setScene(stage, SCENES.SCENE3);

            } else if (event.getCode() == KeyCode.RIGHT) {
                change_dir_right = true;
            } else if (event.getCode() == KeyCode.LEFT) {
                change_dir_left = true;
            } else if (event.getCode() == KeyCode.P && pause == false){
                pause = true;
            } else if (event.getCode() == KeyCode.P && pause == true){
                pause = false;
            } else if (event.getCode() == KeyCode.R){
                setScene(stage, SCENES.SCENE);
                pause = true;
            } else if (event.getCode() == KeyCode.Q){
                pause = true;
                level = 4;
                setScene(stage, SCENES.SCENE4);
            }
        });

        scene3.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DIGIT1) {
                level_reset(1);
                level = 1;
                setScene(stage, SCENES.SCENE1);
            } else if (event.getCode() == KeyCode.DIGIT2){
                level_reset(2);
                level = 2;
                setScene(stage, SCENES.SCENE2);
            } else if (event.getCode() == KeyCode.RIGHT) {
                change_dir_right = true;
            } else if (event.getCode() == KeyCode.LEFT) {
                change_dir_left = true;
            } else if (event.getCode() == KeyCode.P && pause == false){
                pause = true;
            } else if (event.getCode() == KeyCode.P && pause == true){
                pause = false;
            } else if (event.getCode() == KeyCode.R){
                score = 0;
                num_fruit = 0;
                pause = true;
                setScene(stage, SCENES.SCENE);
            } else if (event.getCode() == KeyCode.Q){
                pause = true;
                level = 4;
                setScene(stage, SCENES.SCENE4);
            }
        });

        scene4.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.R) {
                score = 0;
                num_fruit = 0;
                setScene(stage, SCENES.SCENE);
            }
        });


        root4.getChildren().add(canvas4);



        // timer ticks every time we want to advance a frame
        // An AnimationTimer runs at 60 FPS
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handle_animation(stage);
            }
        };
        timer.start(); // this timer will be stopped automatically by JavaFX when the program terminates
        stage.setResizable(false);
        setScene(stage, SCENES.SCENE);
        stage.show();
    }

    void level_reset(int lev) {
        pause = false;
        timer_start = true;
        if (lev == 1 || lev == 2) {
            count_down = 1830;
        } else {
            count_down = -1;
        }
        if (lev == 1) {
            //reset everything
            dx = 1.0f;
            dy = 0.0f;
            speed = 1.0f;
            ball_dir = 1;
            change_dir_right = false;
            change_dir_left = false;
            ball_x.clear();
            ball_y.clear();
            prev_x.clear();
            prev_y.clear();
            balls.clear();
            level_one_fruit.clear();
            level_one_fruit_x.clear();
            level_one_fruit_y.clear();
            root.getChildren().clear();

            ball_x.add(100.0f);
            ball_y.add(60.0f);
            prev_x.add(0.0f);
            prev_y.add(0.0f);

            ball_x.add(60.0f);
            ball_y.add(60.0f);
            prev_x.add(100.0f);
            prev_y.add(60.0f);

            ball_x.add(20.0f);
            ball_y.add(60.0f);
            prev_x.add(60.0f);
            prev_y.add(60.0f);

            Circle ball = new Circle();
            Circle ball_2 = new Circle();
            Circle ball_3 = new Circle();
            balls.add(ball);
            balls.add(ball_2);
            balls.add(ball_3);

            // setup ball
            balls.get(0).setCenterX(ball_x.get(0));
            balls.get(0).setCenterY(ball_y.get(0));
            balls.get(0).setRadius(ball_radius);
            balls.get(0).setFill(Color.BLACK);
            balls.get(1).setCenterX(ball_x.get(1));
            balls.get(1).setCenterY(ball_y.get(1));
            balls.get(1).setRadius(ball_radius);
            balls.get(1).setFill(Color.BLUE);
            balls.get(2).setCenterX(ball_x.get(2));
            balls.get(2).setCenterY(ball_y.get(2));
            balls.get(2).setRadius(ball_radius);
            balls.get(2).setFill(Color.GREY);
            root.getChildren().add(canvas);
            root.getChildren().add(balls.get(0));
            root.getChildren().add(balls.get(1));
            root.getChildren().add(balls.get(2));

            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(220.0f);
            level_one_fruit_x.add(420.0f);
            level_one_fruit_y.add(420.0f);
            level_one_fruit_x.add(540.0f);
            level_one_fruit_y.add(220.0f);
            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(460.0f);
            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(340.0f);
            for (int i = 0; i < 5; ++i) {
                Circle fruit = new Circle();
                fruit.setRadius(20.0f);
                fruit.setFill(Color.RED);
                level_one_fruit.add(fruit);
            }

            for (int i = 0; i < 5; ++i) {
                root.getChildren().add(level_one_fruit.get(i));
            }

        } else if (lev == 2) {
            //reset everything
            dx = 2.0f;
            dy = 0.0f;
            speed = 2.0f;
            ball_dir = 1;
            change_dir_right = false;
            change_dir_left = false;
            ball_x.clear();
            ball_y.clear();
            prev_x.clear();
            prev_y.clear();
            balls.clear();
            level_one_fruit_x.clear();
            level_one_fruit_y.clear();
            level_one_fruit.clear();
            root2.getChildren().clear();

            ball_x.add(100.0f);
            ball_y.add(60.0f);
            prev_x.add(0.0f);
            prev_y.add(0.0f);

            ball_x.add(60.0f);
            ball_y.add(60.0f);
            prev_x.add(100.0f);
            prev_y.add(60.0f);

            ball_x.add(20.0f);
            ball_y.add(60.0f);
            prev_x.add(60.0f);
            prev_y.add(60.0f);

            Circle ball = new Circle();
            Circle ball_2 = new Circle();
            Circle ball_3 = new Circle();
            balls.add(ball);
            balls.add(ball_2);
            balls.add(ball_3);

            // setup ball
            balls.get(0).setCenterX(ball_x.get(0));
            balls.get(0).setCenterY(ball_y.get(0));
            balls.get(0).setRadius(ball_radius);
            balls.get(0).setFill(Color.BLACK);
            balls.get(1).setCenterX(ball_x.get(1));
            balls.get(1).setCenterY(ball_y.get(1));
            balls.get(1).setRadius(ball_radius);
            balls.get(1).setFill(Color.BLUE);
            balls.get(2).setCenterX(ball_x.get(2));
            balls.get(2).setCenterY(ball_y.get(2));
            balls.get(2).setRadius(ball_radius);
            balls.get(2).setFill(Color.GREY);
            root2.getChildren().add(canvas2);
            root2.getChildren().add(balls.get(0));
            root2.getChildren().add(balls.get(1));
            root2.getChildren().add(balls.get(2));

            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(220.0f);
            level_one_fruit_x.add(420.0f);
            level_one_fruit_y.add(420.0f);
            level_one_fruit_x.add(540.0f);
            level_one_fruit_y.add(220.0f);
            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(460.0f);
            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(340.0f);
            level_one_fruit_x.add(340.0f);
            level_one_fruit_y.add(260.0f);
            level_one_fruit_x.add(380.0f);
            level_one_fruit_y.add(460.0f);
            level_one_fruit_x.add(580.0f);
            level_one_fruit_y.add(260.0f);
            level_one_fruit_x.add(260.0f);
            level_one_fruit_y.add(420.0f);
            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(140.0f);
            for (int i = 0; i < 10; ++i) {
                Circle fruit = new Circle();
                fruit.setRadius(20.0f);
                fruit.setFill(Color.RED);
                level_one_fruit.add(fruit);
            }

            for (int i = 0; i < 10; ++i) {
                root2.getChildren().add(level_one_fruit.get(i));
            }

        } else if (lev == 3) {
            //reset everything
            dx = 2.5f;
            dy = 0.0f;
            speed = 2.50f;
            ball_dir = 1;
            change_dir_right = false;
            change_dir_left = false;
            ball_x.clear();
            ball_y.clear();
            prev_x.clear();
            prev_y.clear();
            balls.clear();
            level_one_fruit_x.clear();
            level_one_fruit_y.clear();
            level_one_fruit.clear();
            root3.getChildren().clear();

            ball_x.add(100.0f);
            ball_y.add(60.0f);
            prev_x.add(0.0f);
            prev_y.add(0.0f);

            ball_x.add(60.0f);
            ball_y.add(60.0f);
            prev_x.add(100.0f);
            prev_y.add(60.0f);

            ball_x.add(20.0f);
            ball_y.add(60.0f);
            prev_x.add(60.0f);
            prev_y.add(60.0f);

            Circle ball = new Circle();
            Circle ball_2 = new Circle();
            Circle ball_3 = new Circle();
            balls.add(ball);
            balls.add(ball_2);
            balls.add(ball_3);

            // setup ball
            balls.get(0).setCenterX(ball_x.get(0));
            balls.get(0).setCenterY(ball_y.get(0));
            balls.get(0).setRadius(ball_radius);
            balls.get(0).setFill(Color.BLACK);
            balls.get(1).setCenterX(ball_x.get(1));
            balls.get(1).setCenterY(ball_y.get(1));
            balls.get(1).setRadius(ball_radius);
            balls.get(1).setFill(Color.BLUE);
            balls.get(2).setCenterX(ball_x.get(2));
            balls.get(2).setCenterY(ball_y.get(2));
            balls.get(2).setRadius(ball_radius);
            balls.get(2).setFill(Color.GREY);
            root3.getChildren().add(canvas3);
            root3.getChildren().add(balls.get(0));
            root3.getChildren().add(balls.get(1));
            root3.getChildren().add(balls.get(2));

            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(220.0f);
            level_one_fruit_x.add(420.0f);
            level_one_fruit_y.add(420.0f);
            level_one_fruit_x.add(540.0f);
            level_one_fruit_y.add(220.0f);
            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(460.0f);
            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(340.0f);
            level_one_fruit_x.add(340.0f);
            level_one_fruit_y.add(260.0f);
            level_one_fruit_x.add(380.0f);
            level_one_fruit_y.add(460.0f);
            level_one_fruit_x.add(580.0f);
            level_one_fruit_y.add(260.0f);
            level_one_fruit_x.add(260.0f);
            level_one_fruit_y.add(420.0f);
            level_one_fruit_x.add(220.0f);
            level_one_fruit_y.add(140.0f);
            level_one_fruit_x.add(380.0f);
            level_one_fruit_y.add(260.0f);
            level_one_fruit_x.add(380.0f);
            level_one_fruit_y.add(60.0f);
            level_one_fruit_x.add(580.0f);
            level_one_fruit_y.add(580.0f);
            level_one_fruit_x.add(500.0f);
            level_one_fruit_y.add(500.0f);
            level_one_fruit_x.add(700.0f);
            level_one_fruit_y.add(700.0f);

            for (int i = 0; i < 15; ++i) {
                Circle fruit = new Circle();
                fruit.setRadius(20.0f);
                fruit.setFill(Color.RED);
                level_one_fruit.add(fruit);
            }

            for (int i = 0; i < 15; ++i) {
                root3.getChildren().add(level_one_fruit.get(i));
            }
        }
    }

    void setScene(Stage stage, SCENES s) {

        switch(s) {
            case SCENE:
                stage.setTitle("Splash Page");
                stage.setScene(scene);
                break;
            case SCENE1:
                stage.setTitle("Level 1");
                stage.setScene(scene1);
                break;
            case SCENE2:
                stage.setTitle("Level 2");
                stage.setScene(scene2);
                break;
            case SCENE3:
                stage.setTitle("Level 3");
                stage.setScene(scene3);
                break;
            case SCENE4:
                stage.setTitle("Game Over");
                stage.setScene(scene4);
                break;
        }
    }

    void handle_animation(Stage stage) {

        if (count_down == 0) {
            pause = true;
            if (level == 1) {
                level_reset(2);
                level = 2;
                setScene(stage, SCENES.SCENE2);
            } else if (level == 2) {
                level_reset(3);
                level = 3;
                setScene(stage, SCENES.SCENE3);
            }
        }
        for (int i = 1; i < ball_x.size(); ++i) {
            float x_dif = abs(ball_x.get(0) - ball_x.get(i));
            float y_dif = abs(ball_y.get(0) - ball_y.get(i));
            if ((x_dif + y_dif) < 35) {
                pause = true;
                level = 4;
                setScene(stage, SCENES.SCENE4);
            }
        }



        if (timer_start == true && pause == false) {
            --count_down;
        }

        //intialize the fruit
        if (level == 1) {

            if (ball_x.get(0) < 15 || ball_x.get(0) > 1265 || ball_y.get(0) < 55 || ball_y.get(0) > 785) {
                pause = true;
                level = 4;
                setScene(stage, SCENES.SCENE4);
            }

            // Use the graphics context to draw on a canvas
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setLineWidth(5);
            gc.setStroke(Color.WHITE);
            //Initialize the scene which has 32 x 19 grid
            for (int i = 0; i < 32; ++i) {
                if (i%2 == 0) {
                    green = false;
                } else {
                    green = true;
                }
                for (int j = 0; j < 19; ++j) {
                    if (green == true) {
                        gc.setFill(Color.YELLOWGREEN);
                        int x_cor = 40 * i;
                        int y_cor = 40 * j + 40;
                        gc.fillRect(x_cor,y_cor,40,40);
                        green = false;
                    } else {
                        gc.setFill(Color.ORANGE);
                        int x_cor = 40 * i;
                        int y_cor = 40 * j + 40;
                        gc.fillRect(x_cor,y_cor,40,40);
                        green = true;
                    }
                }
            }
            for (int i = 0; i < 32; ++i) {
                gc.clearRect(40 * i, 0, 40, 40);
            }
            gc.setFont(new Font("TimesRoman",30));
            String score_text = "Score:" + score;
            gc.fillText(score_text,20,30);
            String Fruit_text = "Fruit:" + num_fruit;
            gc.fillText(Fruit_text,180,30);
            String Time = "Time:" + count_down/60;
            gc.fillText(Time,500,30);
            String Level_text = "Level:" + level;
            gc.fillText(Level_text,700,30);



            //display fruit
            for (int i = 0; i < level_one_fruit_x.size(); ++i) {
                level_one_fruit.get(i).setFill(new ImagePattern(image_apple));
                level_one_fruit.get(i).setCenterX(level_one_fruit_x.get(i));
                level_one_fruit.get(i).setCenterY(level_one_fruit_y.get(i));
            }

            //  if we hit the edge of the window, change direction
            //  if (ball_x < (0.0f + ball_radius) || ball_x > (screen_width - ball_radius)) { dx *= -1.0f; }
            //   if (ball_y < (40.0f + ball_radius) || ball_y > (screen_height - ball_radius)) { dy *= -1.0f; }

            //determine whether we should change the direction
            if (change_dir_right == true && (ball_x.get(0) - ball_radius) % 40 == 0
                    && (ball_y.get(0) - ball_radius) % 40 == 0) {
                if (ball_dir == 1) {
                    dx = 0.0f;
                    dy = speed;
                    ball_dir = 2;
                } else if (ball_dir == 2) {
                    dx = -speed;
                    dy = 0.0f;
                    ball_dir = 3;
                } else if (ball_dir == 3) {
                    dx = 0.0f;
                    dy = -speed;
                    ball_dir = 4;
                } else {//direction = 4
                    dx = speed;
                    dy = 0.0f;
                    ball_dir = 1;
                }
                change_dir_right = false;
            } else if (change_dir_left == true && (ball_x.get(0) - ball_radius) % 40
                    == 0 && (ball_y.get(0) - ball_radius) % 40 == 0) {
                if (ball_dir == 1) {
                    dx = 0.0f;
                    dy = -speed;
                    ball_dir = 4;
                } else if (ball_dir == 2) {
                    dx = speed;
                    dy = 0.0f;
                    ball_dir = 1;
                } else if (ball_dir == 3) {
                    dx = 0.0f;
                    dy = speed;
                    ball_dir = 2;
                } else {//direction = 4
                    dx = -speed;
                    dy = 0.0f;
                    ball_dir = 3;
                }
                change_dir_left = false;
            }

            if (pause == false) {
                // move the ball
                ball_x.set(0,ball_x.get(0) + dx);
                ball_y.set(0,ball_y.get(0) + dy);

                //update the last frame
                if (((ball_x.get(0) - ball_radius) % 40 == 0) && ((ball_y.get(0) - ball_radius) % 40 == 0)) {
                    for (int i = balls.size() - 1; i > 0; --i) {
                        ball_x.set(i,prev_x.get(i));
                        ball_y.set(i,prev_y.get(i));
                    }
                    for (int i = balls.size() - 1; i > 0;--i) {
                        prev_x.set(i,ball_x.get(i - 1));
                        prev_y.set(i,ball_y.get(i - 1));
                    }
                    //fruit has been eaten
                    for (int j = 0; j < level_one_fruit_x.size(); ++j) {
                        if (level_one_fruit_x.get(j).equals(ball_x.get(0))
                                && level_one_fruit_y.get(j).equals(ball_y.get(0))) {
                            String sound = getClass().getClassLoader().getResource("click.mp3").toString();
                            AudioClip clip = new AudioClip(sound);
                            clip.play();
                            ++score;
                            ++num_fruit;
                            Circle ball_new = new Circle();
                            balls.add(ball_new);
                            float last_x = ball_x.get(balls.size()-2);
                            float last_y = ball_y.get(balls.size()-2);
                            prev_x.add(last_x);
                            prev_y.add(last_y);
                            root.getChildren().add(balls.get(balls.size()-1));
                            balls.get(balls.size()-1).setRadius(ball_radius);
                            balls.get(balls.size()-1).setFill(Color.DARKGREY);
                            boolean found;
                            found = false;
                            int rand1 = rand.nextInt(32);
                            int rand2 = rand.nextInt(19);
                            for (int k = 0; k < level_one_fruit_x.size(); ++k) {
                                if (level_one_fruit_x.equals(rand1*40.0f + 20.0f) &&
                                        level_one_fruit_y.equals(rand2*40.0f + 60.0f)) {
                                    rand1 = rand.nextInt(32);
                                    rand2 = rand.nextInt(19);
                                }
                            }


                            level_one_fruit_x.set(j,rand1*40.0f + 20.0f);
                            level_one_fruit_y.set(j,rand2*40.0f + 60.0f);

                            if ((last_x+60) <= 1280 ) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x + 40 == ball_x.get(i) && last_y == ball_y.get(i)) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x+40);
                                    ball_y.add(last_y);
                                    found = true;
                                }
                            }
                            if ((last_x-60) >= 0 && !found) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x - 40 == ball_x.get(i) && last_y == ball_y.get(i)) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x-40);
                                    ball_y.add(last_y);
                                    found = true;
                                }
                            }
                            if ((last_y+60) <= 760 && !found) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x == ball_x.get(i) && last_y == ball_y.get(i) + 40) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x);
                                    ball_y.add(last_y + 40);
                                    found = true;
                                }
                            }
                            if ((last_y-60) >= 0 && !found) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x == ball_x.get(i) && last_y == ball_y.get(i) - 40) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x);
                                    ball_y.add(last_y-40);
                                    found = true;
                                }
                            }
                            if (!found) {
                                return;
                            }
                            break;
                        }

                    }

                }

                //adjust the positions for balls that are not the head
                for (int i = balls.size() - 1; i > 0; --i) {
                    if (prev_x.get(i).equals(ball_x.get(i))) {//horizontal same
                        if (prev_y.get(i) > ball_y.get(i)) {
                            ball_y.set(i,ball_y.get(i) + speed);
                        } else {
                            ball_y.set(i,ball_y.get(i) - speed);
                        }
                    } else {//vertical same
                        if (prev_x.get(i) > ball_x.get(i)) {
                            ball_x.set(i,ball_x.get(i) + speed);
                        } else {
                            ball_x.set(i,ball_x.get(i) - speed);
                        }
                    }
                }
            }
            if (ball_dir == 1) {
                balls.get(0).setFill(new ImagePattern(right));
            } else if (ball_dir == 2) {
                balls.get(0).setFill(new ImagePattern(down));
            } else if (ball_dir == 3) {
                balls.get(0).setFill(new ImagePattern(left));
            } else if (ball_dir == 4) {
                balls.get(0).setFill(new ImagePattern(up));
            }
            //display the body
            for (int i = balls.size() - 1; i >= 0; --i) {
                balls.get(i).setCenterX(ball_x.get(i));
                balls.get(i).setCenterY(ball_y.get(i));
            }
            //display fruit
            for (int i = 0; i < level_one_fruit_x.size(); ++i) {
                level_one_fruit.get(i).setFill(new ImagePattern(image_apple));
                level_one_fruit.get(i).setCenterX(level_one_fruit_x.get(i));
                level_one_fruit.get(i).setCenterY(level_one_fruit_y.get(i));
            }

        } else if (level == 2) {
            if (ball_x.get(0) < 15 || ball_x.get(0) > 1265 || ball_y.get(0) < 55 || ball_y.get(0) > 785) {
                pause = true;
                level = 4;
                setScene(stage, SCENES.SCENE4);
            }
            GraphicsContext gc = canvas2.getGraphicsContext2D();
            gc.setLineWidth(5);
            gc.setStroke(Color.WHITE);
            //Initialize the scene which has 32 x 19 grid
            for (int i = 0; i < 32; ++i) {
                if (i%2 == 0) {
                    green = false;
                } else {
                    green = true;
                }
                for (int j = 0; j < 19; ++j) {
                    if (green == true) {
                        gc.setFill(Color.YELLOWGREEN);
                        int x_cor = 40 * i;
                        int y_cor = 40 * j + 40;
                        gc.fillRect(x_cor,y_cor,40,40);
                        green = false;
                    } else {
                        gc.setFill(Color.ORANGE);
                        int x_cor = 40 * i;
                        int y_cor = 40 * j + 40;
                        gc.fillRect(x_cor,y_cor,40,40);
                        green = true;
                    }
                }
            }
            for (int i = 0; i < 32; ++i) {
                gc.clearRect(40 * i, 0, 40, 40);
            }
            gc.setFont(new Font("TimesRoman",30));
            String score_text = "Score:" + score;
            gc.fillText(score_text,20,30);
            String Fruit_text = "Fruit:" + num_fruit;
            gc.fillText(Fruit_text,180,30);
            String Time = "Time:" + count_down/60;
            gc.fillText(Time,500,30);
            String Level_text = "Level:" + level;
            gc.fillText(Level_text,700,30);

            //display fruit
            for (int i = 0; i < level_one_fruit_x.size(); ++i) {
                level_one_fruit.get(i).setFill(new ImagePattern(image_apple));
                level_one_fruit.get(i).setCenterX(level_one_fruit_x.get(i));
                level_one_fruit.get(i).setCenterY(level_one_fruit_y.get(i));
            }


            //  if we hit the edge of the window, change direction
            //  if (ball_x < (0.0f + ball_radius) || ball_x > (screen_width - ball_radius)) { dx *= -1.0f; }
            //   if (ball_y < (40.0f + ball_radius) || ball_y > (screen_height - ball_radius)) { dy *= -1.0f; }

            //determine whether we should change the direction
            if (change_dir_right == true && (ball_x.get(0) - ball_radius) % 40 == 0
                    && (ball_y.get(0) - ball_radius) % 40 == 0) {
                if (ball_dir == 1) {
                    dx = 0.0f;
                    dy = speed;
                    ball_dir = 2;
                } else if (ball_dir == 2) {
                    dx = -speed;
                    dy = 0.0f;
                    ball_dir = 3;
                } else if (ball_dir == 3) {
                    dx = 0.0f;
                    dy = -speed;
                    ball_dir = 4;
                } else {//direction = 4
                    dx = speed;
                    dy = 0.0f;
                    ball_dir = 1;
                }
                change_dir_right = false;
            } else if (change_dir_left == true && (ball_x.get(0) - ball_radius) % 40
                    == 0 && (ball_y.get(0) - ball_radius) % 40 == 0) {
                if (ball_dir == 1) {
                    dx = 0.0f;
                    dy = -speed;
                    ball_dir = 4;
                } else if (ball_dir == 2) {
                    dx = speed;
                    dy = 0.0f;
                    ball_dir = 1;
                } else if (ball_dir == 3) {
                    dx = 0.0f;
                    dy = speed;
                    ball_dir = 2;
                } else {//direction = 4
                    dx = -speed;
                    dy = 0.0f;
                    ball_dir = 3;
                }
                change_dir_left = false;
            }

            if (pause == false) {
                // move the ball
                ball_x.set(0,ball_x.get(0) + dx);
                ball_y.set(0,ball_y.get(0) + dy);

                //update the last frame
                if (((ball_x.get(0) - ball_radius) % 40 == 0) && ((ball_y.get(0) - ball_radius) % 40 == 0)) {
                    for (int i = balls.size() - 1; i > 0; --i) {
                        ball_x.set(i,prev_x.get(i));
                        ball_y.set(i,prev_y.get(i));
                    }
                    for (int i = balls.size() - 1; i > 0;--i) {
                        prev_x.set(i,ball_x.get(i - 1));
                        prev_y.set(i,ball_y.get(i - 1));
                    }
                    for (int j = 0; j < level_one_fruit_x.size(); ++j) {
                        if (level_one_fruit_x.get(j).equals(ball_x.get(0))
                                && level_one_fruit_y.get(j).equals(ball_y.get(0))) {
                            String sound = getClass().getClassLoader().getResource("click.mp3").toString();
                            AudioClip clip = new AudioClip(sound);
                            clip.play();
                            score += 2; //level 2 each fruit has 2 score
                            ++num_fruit;
                            Circle ball_new = new Circle();
                            balls.add(ball_new);
                            float last_x = ball_x.get(balls.size()-2);
                            float last_y = ball_y.get(balls.size()-2);
                            prev_x.add(last_x);
                            prev_y.add(last_y);
                            root2.getChildren().add(balls.get(balls.size()-1));
                            balls.get(balls.size()-1).setRadius(ball_radius);
                            balls.get(balls.size()-1).setFill(Color.DARKGREY);
                            boolean found;
                            found = false;
                            int rand1 = rand.nextInt(32);
                            int rand2 = rand.nextInt(19);
                            for (int k = 0; k < level_one_fruit_x.size(); ++k) {
                                if (level_one_fruit_x.equals(rand1*40.0f + 20.0f) &&
                                        level_one_fruit_y.equals(rand2*40.0f + 60.0f)) {
                                    rand1 = rand.nextInt(32);
                                    rand2 = rand.nextInt(19);
                                }
                            }
                            for (int k = 0; k < prev_x.size(); ++k) {
                                if (prev_x.equals(rand1*40.0f + 20.0f) &&
                                        prev_y.equals(rand2*40.0f + 60.0f)) {
                                    rand1 = rand.nextInt(32);
                                    rand2 = rand.nextInt(19);
                                }
                            }

                            level_one_fruit_x.set(j,rand1*40.0f + 20.0f);
                            level_one_fruit_y.set(j,rand2*40.0f + 60.0f);

                            if ((last_x+60) <= 1280 ) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x + 40 == ball_x.get(i) && last_y == ball_y.get(i)) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x+40);
                                    ball_y.add(last_y);
                                    found = true;
                                }
                            }
                            if ((last_x-60) >= 0 && !found) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x - 40 == ball_x.get(i) && last_y == ball_y.get(i)) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x-40);
                                    ball_y.add(last_y);
                                    found = true;
                                }
                            }
                            if ((last_y+60) <= 760 && !found) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x == ball_x.get(i) && last_y == ball_y.get(i) + 40) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x);
                                    ball_y.add(last_y + 40);
                                    found = true;
                                }
                            }
                            if ((last_y-60) >= 0 && !found) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x == ball_x.get(i) && last_y == ball_y.get(i) - 40) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x);
                                    ball_y.add(last_y-40);
                                    found = true;
                                }
                            }
                            if (!found) {
                                return;
                            }
                            break;
                        }

                    }
                }

                //adjust the positions for balls that are not the head
                for (int i = balls.size() - 1; i > 0; --i) {
                    if (prev_x.get(i).equals(ball_x.get(i))) {//horizontal same
                        if (prev_y.get(i) > ball_y.get(i)) {
                            ball_y.set(i,ball_y.get(i) + speed);
                        } else {
                            ball_y.set(i,ball_y.get(i) - speed);
                        }
                    } else {//vertical same
                        if (prev_x.get(i) > ball_x.get(i)) {
                            ball_x.set(i,ball_x.get(i) + speed);
                        } else {
                            ball_x.set(i,ball_x.get(i) - speed);
                        }
                    }
                }
            }
            if (ball_dir == 1) {
                balls.get(0).setFill(new ImagePattern(right));
            } else if (ball_dir == 2) {
                balls.get(0).setFill(new ImagePattern(down));
            } else if (ball_dir == 3) {
                balls.get(0).setFill(new ImagePattern(left));
            } else if (ball_dir == 4) {
                balls.get(0).setFill(new ImagePattern(up));
            }

            //display the body
            for (int i = balls.size() - 1; i >= 0; --i) {
                balls.get(i).setCenterX(ball_x.get(i));
                balls.get(i).setCenterY(ball_y.get(i));
            }

            //display fruit
            for (int i = 0; i < level_one_fruit_x.size(); ++i) {
                level_one_fruit.get(i).setFill(new ImagePattern(image_apple));
                level_one_fruit.get(i).setCenterX(level_one_fruit_x.get(i));
                level_one_fruit.get(i).setCenterY(level_one_fruit_y.get(i));
            }

        } else if (level == 3) {
            if (ball_x.get(0) < 15 || ball_x.get(0) > 1265 || ball_y.get(0) < 55 || ball_y.get(0) > 785) {
                pause = true;
                level = 4;
                setScene(stage, SCENES.SCENE4);
            }
            GraphicsContext gc = canvas3.getGraphicsContext2D();
            gc.setLineWidth(5);
            gc.setStroke(Color.WHITE);
            //Initialize the scene which has 32 x 19 grid
            for (int i = 0; i < 32; ++i) {
                if (i%2 == 0) {
                    green = false;
                } else {
                    green = true;
                }
                for (int j = 0; j < 19; ++j) {
                    if (green == true) {
                        gc.setFill(Color.YELLOWGREEN);
                        int x_cor = 40 * i;
                        int y_cor = 40 * j + 40;
                        gc.fillRect(x_cor,y_cor,40,40);
                        green = false;
                    } else {
                        gc.setFill(Color.ORANGE);
                        int x_cor = 40 * i;
                        int y_cor = 40 * j + 40;
                        gc.fillRect(x_cor,y_cor,40,40);
                        green = true;
                    }
                }
            }

            for (int i = 0; i < 32; ++i) {
                gc.clearRect(40 * i, 0, 40, 40);
            }
            gc.setFont(new Font("TimesRoman",30));
            String score_text = "Score:" + score;
            gc.fillText(score_text,20,30);
            String Fruit_text = "Fruit:" + num_fruit;
            gc.fillText(Fruit_text,180,30);
            String Time = "Time:NA";
            gc.fillText(Time,500,30);
            String Level_text = "Level:" + level;
            gc.fillText(Level_text,700,30);

            //display fruit
            for (int i = 0; i < level_one_fruit_x.size(); ++i) {
                level_one_fruit.get(i).setFill(new ImagePattern(image_apple));
                level_one_fruit.get(i).setCenterX(level_one_fruit_x.get(i));
                level_one_fruit.get(i).setCenterY(level_one_fruit_y.get(i));
            }

            //  if we hit the edge of the window, change direction
            //  if (ball_x < (0.0f + ball_radius) || ball_x > (screen_width - ball_radius)) { dx *= -1.0f; }
            //   if (ball_y < (40.0f + ball_radius) || ball_y > (screen_height - ball_radius)) { dy *= -1.0f; }

            //determine whether we should change the direction
            if (change_dir_right == true && (ball_x.get(0) - ball_radius) % 40 == 0
                    && (ball_y.get(0) - ball_radius) % 40 == 0) {
                if (ball_dir == 1) {
                    dx = 0.0f;
                    dy = speed;
                    ball_dir = 2;
                } else if (ball_dir == 2) {
                    dx = -speed;
                    dy = 0.0f;
                    ball_dir = 3;
                } else if (ball_dir == 3) {
                    dx = 0.0f;
                    dy = -speed;
                    ball_dir = 4;
                } else {//direction = 4
                    dx = speed;
                    dy = 0.0f;
                    ball_dir = 1;
                }
                change_dir_right = false;
            } else if (change_dir_left == true && (ball_x.get(0) - ball_radius) % 40
                    == 0 && (ball_y.get(0) - ball_radius) % 40 == 0) {
                if (ball_dir == 1) {
                    dx = 0.0f;
                    dy = -speed;
                    ball_dir = 4;
                } else if (ball_dir == 2) {
                    dx = speed;
                    dy = 0.0f;
                    ball_dir = 1;
                } else if (ball_dir == 3) {
                    dx = 0.0f;
                    dy = speed;
                    ball_dir = 2;
                } else {//direction = 4
                    dx = -speed;
                    dy = 0.0f;
                    ball_dir = 3;
                }
                change_dir_left = false;
            }

            if (pause == false) {
                // move the ball
                ball_x.set(0,ball_x.get(0) + dx);
                ball_y.set(0,ball_y.get(0) + dy);

                //update the last frame
                if (((ball_x.get(0) - ball_radius) % 40 == 0) && ((ball_y.get(0) - ball_radius) % 40 == 0)) {
                    for (int i = balls.size() - 1; i > 0; --i) {
                        ball_x.set(i,prev_x.get(i));
                        ball_y.set(i,prev_y.get(i));
                    }
                    for (int i = balls.size() - 1; i > 0;--i) {
                        prev_x.set(i,ball_x.get(i - 1));
                        prev_y.set(i,ball_y.get(i - 1));
                    }
                    for (int j = 0; j < level_one_fruit_x.size(); ++j) {
                        if (level_one_fruit_x.get(j).equals(ball_x.get(0))
                                && level_one_fruit_y.get(j).equals(ball_y.get(0))) {
                            String sound = getClass().getClassLoader().getResource("click.mp3").toString();
                            AudioClip clip = new AudioClip(sound);
                            clip.play();
                            score += 3; //level 2 each fruit has 2 score
                            ++num_fruit;
                            Circle ball_new = new Circle();
                            balls.add(ball_new);
                            float last_x = ball_x.get(balls.size()-2);
                            float last_y = ball_y.get(balls.size()-2);
                            prev_x.add(last_x);
                            prev_y.add(last_y);
                            root3.getChildren().add(balls.get(balls.size()-1));
                            balls.get(balls.size()-1).setRadius(ball_radius);
                            balls.get(balls.size()-1).setFill(Color.DARKGREY);
                            boolean found;
                            found = false;
                            int rand1 = rand.nextInt(32);
                            int rand2 = rand.nextInt(19);
                            for (int k = 0; k < level_one_fruit_x.size(); ++k) {
                                if (level_one_fruit_x.equals(rand1*40.0f + 20.0f) &&
                                        level_one_fruit_y.equals(rand2*40.0f + 60.0f)) {
                                    rand1 = rand.nextInt(32);
                                    rand2 = rand.nextInt(19);
                                }
                            }

                            level_one_fruit_x.set(j,rand1*40.0f + 20.0f);
                            level_one_fruit_y.set(j,rand2*40.0f + 60.0f);

                            if ((last_x+60) <= 1280 ) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x + 40 == ball_x.get(i) && last_y == ball_y.get(i)) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x+40);
                                    ball_y.add(last_y);
                                    found = true;
                                }
                            }
                            if ((last_x-60) >= 0 && !found) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x - 40 == ball_x.get(i) && last_y == ball_y.get(i)) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x-40);
                                    ball_y.add(last_y);
                                    found = true;
                                }
                            }
                            if ((last_y+60) <= 760 && !found) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x == ball_x.get(i) && last_y == ball_y.get(i) + 40) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x);
                                    ball_y.add(last_y + 40);
                                    found = true;
                                }
                            }
                            if ((last_y-60) >= 0 && !found) {
                                boolean empty = true;
                                for (int i = 0; i < balls.size() - 1; ++i) {
                                    if (last_x == ball_x.get(i) && last_y == ball_y.get(i) - 40) {
                                        empty = false;
                                        break;
                                    }
                                }
                                if (empty) {
                                    ball_x.add(last_x);
                                    ball_y.add(last_y-40);
                                    found = true;
                                }
                            }
                            if (!found) {
                                return;
                            }
                            break;
                        }

                    }
                }

                //adjust the positions for balls that are not the head
                for (int i = balls.size() - 1; i > 0; --i) {
                    if (prev_x.get(i).equals(ball_x.get(i))) {//horizontal same
                        if (prev_y.get(i) > ball_y.get(i)) {
                            ball_y.set(i,ball_y.get(i) + speed);
                        } else {
                            ball_y.set(i,ball_y.get(i) - speed);
                        }
                    } else {//vertical same
                        if (prev_x.get(i) > ball_x.get(i)) {
                            ball_x.set(i,ball_x.get(i) + speed);
                        } else {
                            ball_x.set(i,ball_x.get(i) - speed);
                        }
                    }
                }
            }

            if (ball_dir == 1) {
                balls.get(0).setFill(new ImagePattern(right));
            } else if (ball_dir == 2) {
                balls.get(0).setFill(new ImagePattern(down));
            } else if (ball_dir == 3) {
                balls.get(0).setFill(new ImagePattern(left));
            } else if (ball_dir == 4) {
                balls.get(0).setFill(new ImagePattern(up));
            }

            //display the body
            for (int i = balls.size() - 1; i >= 0; --i) {
                balls.get(i).setCenterX(ball_x.get(i));
                balls.get(i).setCenterY(ball_y.get(i));
            }
            //display fruit
            for (int i = 0; i < level_one_fruit_x.size(); ++i) {
                level_one_fruit.get(i).setCenterX(level_one_fruit_x.get(i));
                level_one_fruit.get(i).setCenterY(level_one_fruit_y.get(i));
            }
        } else if (level == 4) {
            GraphicsContext gc = canvas4.getGraphicsContext2D();
            gc.setFill(Color.YELLOWGREEN);
            gc.clearRect(0, 0, 1280, 800);
            gc.setFont(new Font("TimesRoman",50));
            gc.fillText("Game Over",450,300);
            gc.setFont(new Font("TimesRoman",30));
            String score_text = "Score: " + score;
            gc.fillText(score_text,500,400);
            String tips = "Press r to restart";
            gc.fillText(tips,460,500);
        }
    }
}