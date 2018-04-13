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

public class GUISetup extends Application
{
	public int i = 8;
	public TextField playerNameInput;
	
	/**
	* Main meethod that initiates the calling of other methods.
	* @param args A standard string argument for a main method.
	*/
	public static void main(String [] args)
	{		
		launch(args);
	}	
	
	/**
	 * This is the default constructor to override Application
	 */ 
	@Override
	public void start(Stage primaryStage)
	{

	}
	/**
	 * The method is the overload constructor that creates buttons, labels, and a scene for the start screen, including the name of the player.
	 * @param primaryStage An Stage object to be populated with buttons and labels. gameMode is passed in from LoadGame
	 */ 
	public void start(Stage primaryStage, String gameMode)
	{
		primaryStage.setTitle("Setup");
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		HBox playerHBox = new HBox();
		// HBox gameModeHBox = new HBox();
		HBox boardSizeHBox = new HBox();
		
		vbox.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ESCAPE:
				primaryStage.close();
			}
		});
		
		Label playerName = new Label("Player Name: ");
		playerName.setPadding(new Insets(5));
		playerName.setStyle("-fx-font-size: 16px");
		playerHBox.getChildren().add(0, playerName);
		
		playerNameInput = new TextField();
		playerNameInput.setStyle("-fx-font-size: 16px");
		playerHBox.getChildren().add(1, playerNameInput);
		
		playerHBox.setPadding(new Insets(5));
				
		Label boardSize = new Label("Board Size: ");
		boardSize.setPadding(new Insets(5));
		boardSize.setStyle("-fx-font-size: 16px");
		boardSizeHBox.getChildren().add(0, boardSize);
		
		Label boardSizeNum = new Label(toString(i));
		boardSizeNum.setPadding(new Insets(5));
		boardSizeNum.setStyle("-fx-font-size: 16px");
		boardSizeHBox.getChildren().add(1, boardSizeNum);
		
		Button increaseBoardSize = new Button("+");
		increaseBoardSize.setMinWidth(50);
		boardSizeHBox.getChildren().add(2, increaseBoardSize);

		
		
		Button decreaseBoardSize = new Button("-");
		decreaseBoardSize.setMinWidth(50);
		boardSizeHBox.getChildren().add(3, decreaseBoardSize);
		
		increaseBoardSize.setOnAction(event -> {
			if(i < 16)
			{
				i++;
			}
			else
			{
			 	increaseBoardSize.setStyle("-fx-color: grey");
			}
			decreaseBoardSize.setStyle("-fx-color: #DCDCDC");
			boardSizeNum.setText(toString(i));
		});

		decreaseBoardSize.setOnAction(event -> {
			if(i > 7)
			{
				i--;
			}
			else
			{
				decreaseBoardSize.setStyle("-fx-color: grey");
			}
			increaseBoardSize.setStyle("-fx-color: #DCDCDC");
			boardSizeNum.setText(toString(i));
		});
		
		boardSizeHBox.setPadding(new Insets(5));

		vbox.getChildren().addAll(playerHBox, boardSizeHBox);
		
		Button start = new Button("Start");
		start.setAlignment(Pos.BOTTOM_RIGHT);
		start.setPadding(new Insets(5));
		vbox.getChildren().add(start);
		start.setOnAction(event -> {
			if(gameMode.equals("SinglePlayer"))
			{
				primaryStage.close();

				SinglePlayerGUI gui = new SinglePlayerGUI();
				try {
					gui.boardUnits = i;
					gui.p1Name = playerNameInput.getText();
					
					gui.start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(gameMode.equals("TwoPlayer"))
			{
				primaryStage.close();
				addPlayer(new Stage());
			}
			
		});
		
		Scene scene = new Scene(vbox, 500, 150);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	* Method converts i into a string instead of an integer.
	* @param i An integer for the interval location of the board.
	*/
	public String toString(int i)
	{
		return i + " ";
	}

	/**
	* Method creates buttons, labels, and a scene for a second player.
	* @param primaryStage An Stage object to be populated with buttons and labels.
	*/
	public void addPlayer(Stage primaryStage)
	{
		primaryStage.setTitle("Player 2");
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		HBox playerHBox = new HBox();
		
		vbox.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ESCAPE:
				primaryStage.close();
			}
		});
		
			Label playerName2 = new Label("Player 2 Name: ");
			playerName2.setPadding(new Insets(5));
			playerName2.setStyle("-fx-font-size: 16px");
			playerHBox.getChildren().add(0, playerName2);
			
			TextField playerName2Input = new TextField();
			playerName2Input.setStyle("-fx-font-size: 16px");
			playerHBox.getChildren().add(1, playerName2Input);

			vbox.getChildren().addAll(playerHBox);
			
			Button start = new Button("Start");
			start.setPadding(new Insets(5));
			start.setAlignment(Pos.BOTTOM_RIGHT);
			start.setPadding(new Insets(5));
			vbox.getChildren().add(start);
			start.setOnAction(event -> {
					primaryStage.close();
					TwoPlayerGUI gui = new TwoPlayerGUI();
					try {
						gui.start(new Stage());
						gui.twoPlayer.player1.setName(playerNameInput.getText());
						gui.twoPlayer.player2.setName(playerName2Input.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			Scene scene = new Scene(vbox, 500, 150);
			primaryStage.setScene(scene);
			primaryStage.show();
	}

	/**
	* Method implements the Runnable interface.
	*/
	public void run() 
	{
		launch();
	}
}


