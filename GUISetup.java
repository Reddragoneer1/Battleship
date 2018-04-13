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
	
	public static void main(String [] args)
	{		
		launch(args);
	}	
	
	@Override
	public void start(Stage primaryStage)
	{
		primaryStage.setTitle("Setup");
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		HBox playerHBox = new HBox();
		HBox gameModeHBox = new HBox();
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
		
		Label gameModeString = new Label("Game mode:  ");
		gameModeString.setPadding(new Insets(5));
		gameModeString.setStyle("-fx-font-size: 16px");
		gameModeHBox.getChildren().add(0, gameModeString);
		
		
		ComboBox<String> gameMode = new ComboBox<>();
		
		gameMode.setItems(FXCollections.observableArrayList("Single player", "Two player"));
		gameMode.setValue("Single player");
		
		gameModeHBox.getChildren().add(1, gameMode);
		
		gameModeHBox.setPadding(new Insets(5));
		
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
		increaseBoardSize.setOnAction(event -> {
			i++;
			boardSizeNum.setText(toString(i));
		});
		
		
		Button decreaseBoardSize = new Button("-");
		decreaseBoardSize.setMinWidth(50);
		boardSizeHBox.getChildren().add(3, decreaseBoardSize);
		
		decreaseBoardSize.setOnAction(event -> {
			if(i > 7)	//TODO: Implement color fade when 7 or less
			{
				i--;
			}
			else
			{
				decreaseBoardSize.setStyle("-fx-color: grey");
			}
			boardSizeNum.setText(toString(i));
		});
		
		boardSizeHBox.setPadding(new Insets(5));

		
		Label loadGameString = new Label("Load game?  ");
		loadGameString.setPadding(new Insets(5));
		loadGameString.setStyle("-fx-font-size: 16px");
		gameModeHBox.getChildren().add(2, loadGameString);
		
		
		ComboBox<String> loadGame = new ComboBox<>();
		
		loadGame.setItems(FXCollections.observableArrayList("Yes", "No"));
		loadGame.setValue("No");
		
		gameModeHBox.getChildren().add(3, loadGame);
		
		gameModeHBox.setPadding(new Insets(5));
		
		vbox.getChildren().addAll(playerHBox, gameModeHBox, boardSizeHBox);
		
		Button start = new Button("Start");
		start.setAlignment(Pos.BOTTOM_RIGHT);
		start.setPadding(new Insets(5));
		vbox.getChildren().add(start);
		start.setOnAction(event -> {
			String game = gameMode.getValue();
			if(game.equals("Single player"))
			{
				primaryStage.close();		
				
				SinglePlayerGUI gui = new SinglePlayerGUI();
				try 
				{
					
					gui.setLoad(true);
					
					gui.boardUnits = i;
					gui.p1Name = playerNameInput.getText();

					gui.start(new Stage());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(game.equals("Two player"))
			{
				
				primaryStage.close();
				addPlayer(new Stage());
			}
			
		});
		
		Scene scene = new Scene(vbox, 500, 150);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public String toString(int i)
	{
		return i + " ";
	}
	
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
						gui.boardUnits = i;
						gui.p1Name = playerNameInput.getText();
						gui.p2Name = playerName2Input.getText();
						
						gui.start(new Stage());
						
					} catch (Exception e) {
						
						e.printStackTrace();
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


