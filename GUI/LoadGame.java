package GUI;
import BackEnd.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LoadGame extends Application
{

	
	public static void main(String [] args)
	{		
		launch(args);
	}	

	@Override
	public void start(Stage primaryStage)
	{
		primaryStage.setTitle("Load Game");
		VBox vbox = new VBox();
		HBox hboxLoad = new HBox();
		HBox hboxNext = new HBox();
		vbox.setAlignment(Pos.CENTER);

		vbox.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ESCAPE:
				primaryStage.close();
			}
		});

		Label loadGameString = new Label("Load game?  ");
		loadGameString.setPadding(new Insets(5));
		loadGameString.setStyle("-fx-font-size: 16px");
		hboxLoad.getChildren().add(0, loadGameString);

		ComboBox<String> loadGame = new ComboBox<>();
		
		loadGame.setItems(FXCollections.observableArrayList("Yes", "No"));
		loadGame.setValue("No");
		
		hboxLoad.getChildren().add(1, loadGame);


		ComboBox<String> gameMode = new ComboBox<>();
		
		gameMode.setItems(FXCollections.observableArrayList("SinglePlayer", "TwoPlayer"));
		gameMode.setValue("SinglePlayer");
		
		hboxLoad.getChildren().add(2, gameMode);
		

		Button next = new Button("Next");
			next.setPadding(new Insets(5));
			next.setAlignment(Pos.BOTTOM_RIGHT);
			next.setPadding(new Insets(5));
			hboxNext.getChildren().add(0, next);


		vbox.getChildren().addAll(hboxLoad,hboxNext);


		next.setOnAction(event -> {
					primaryStage.close();
					String loadGameAnswer = loadGame.getValue();
					if(loadGameAnswer.equals("Yes"))
					{
						if(gameMode.getValue().equals("SinglePlayer"))
						{
							SinglePlayerGUI gui = new SinglePlayerGUI();
							gui.singlePlayer.load();
							try {
								gui.start(new Stage());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else
						{
							TwoPlayerGUI gui = new TwoPlayerGUI();
							gui.twoPlayer.load();
							try {
								gui.start(new Stage());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					else
					{
						GUISetup setup = new GUISetup();
						try {
							setup.start(new Stage(), gameMode.getValue());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		Scene scene = new Scene(vbox, 500, 150);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void run() {
		launch();
	}
}