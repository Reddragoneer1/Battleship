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
/**
 * This class is the initial screen for player names, game loading and board size. 
 * It will also ask the user if they want to load a game
 * @author  Nathanael
 */

public class GUIWin extends Application
{
	private String winnerName;
	public static void main(String [] args)
	{		
		launch(args);
	}	
	@Override
	public void start(Stage primaryStage)
	{
		winnerName = "LMAO";

		primaryStage.setTitle("Win Screen");

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);

		gridPane.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ESCAPE:
				primaryStage.close();
			// case ENTER:
			// 	primaryStage.close();
			}
		});

		Label winner = new Label("Congratulations " + winnerName + "!!!");
		gridPane.add(winner, 0, 0);
		winner.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(gridPane, 500, 150);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void run() {
		launch();
		
	}
}


