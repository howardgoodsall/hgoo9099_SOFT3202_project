package au.edu.sydney.soft3202.task3.view;

import au.edu.sydney.soft3202.task3.model.GameBoard;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.Optional;
import java.util.ArrayList;

/**
 * This is the overall window scene for the application. It creates and contains the different elements in the
 * top, bottom, center, and right side of the window, along with linking them to the model.
 *
 * Identify the mutable couplings between the View and the Model: This class and the BoardPane are the only 2 View
 * classes that mutate the Model, and all mutations go first to the GameBoard. There is coupling in other ways
 * from the View to the Model, but they are accessor methods only.
 *
 * Also note that while this represents the game window, it *contains* the Scene that JavaFX needs, and does not
 * inherit from Scene. This is true for all JavaFX components in this application, they are contained, not extended.
 */
public class GameWindow {
    private final BoardPane boardPane;
    private final Scene scene;
    private MenuBar menuBar;
    private VBox sideButtonBar;

    private final GameBoard model;

    public GameWindow(GameBoard model) {
        this.model = model;

        BorderPane pane = new BorderPane();
        this.scene = new Scene(pane);

        this.boardPane = new BoardPane(model);

        enterUsernameBox();//Call enterUsername here

        StatusBarPane statusBar = new StatusBarPane(model);
        buildMenu();
        buildSideButtons();
        buildKeyListeners();

        pane.setCenter(boardPane.getPane());
        pane.setTop(menuBar);
        pane.setRight(sideButtonBar);
        pane.setBottom(statusBar.getStatusBar());

    }

    private void enterUsernameBox() {//Added method - username box on startup
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Enter Username");
        textInput.setHeaderText("Enter Username");
        Optional<String> result = textInput.showAndWait();
        if(result.isPresent()) {
            String inp = textInput.getEditor().getText();
            if(!inp.equalsIgnoreCase("null") && !inp.equalsIgnoreCase("")) {//Pre-emptive null check
                model.enterUsername(inp);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Username Error");
                alert.setHeaderText("That username is not allowed");
                alert.showAndWait();
                return;
            }
        } else {
            System.exit(0);
        }
    }

    private void buildKeyListeners() {
        // This allows keyboard input. Note that the scene is used, so any time
        // the window is in focus the keyboard input will be registered.
        // More often, keyboard input is more closely linked to a specific
        // node that must have focus, i.e. the Enter key in a text input to submit
        // a form.

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.N) {
                newGameAction();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                serialiseAction();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.L) {
                deserialiseAction();
            }
        });
    }

    private void buildSideButtons() {
        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction((event) -> newGameAction());

        Button serialiseBtn = new Button("Save Game");
        serialiseBtn.setOnAction((event) -> serialiseAction());

        Button deserialiseBtn = new Button("Load Game");
        deserialiseBtn.setOnAction((event) -> deserialiseAction());

        this.sideButtonBar = new VBox(newGameBtn, serialiseBtn, deserialiseBtn);
        sideButtonBar.setSpacing(10);
    }

    private void buildMenu() {
        Menu actionMenu = new Menu("Actions");

        MenuItem newGameItm = new MenuItem("New Game");
        newGameItm.setOnAction((event)-> newGameAction());

        MenuItem serialiseItm = new MenuItem("Save Game");
        serialiseItm.setOnAction((event)-> serialiseAction());

        MenuItem deserialiseItm = new MenuItem("Load Game");
        deserialiseItm.setOnAction((event)-> deserialiseAction());

        actionMenu.getItems().addAll(newGameItm, serialiseItm, deserialiseItm);

        this.menuBar = new MenuBar();
        menuBar.getMenus().add(actionMenu);
    }

    private void newGameAction() {
        // Note the separation here between newGameAction and doNewGame. This allows
        // for the validation aspects to be separated from the operation itself.

        if (null == model.getCurrentTurn()) { // no current game
            doNewGame();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New Game Warning");
        alert.setHeaderText("Starting a new game now will lose all current progress.");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
         doNewGame();
        }
    }

    private void serialiseAction() {//modified this method to save game
        // Serialisation is a way of turning some data into a communicable form.
        // In Java it has a library to support it, but here we are just manually converting the field
        // we know we need into a string (in the model). We can then use that string in reverse to get that state back

        if (null == model.getCurrentTurn()) { // no current game
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Game Save Error");
            alert.setHeaderText("There is no game to save!");
            alert.showAndWait();
            return;
        }

        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Save Game");
        textInput.setHeaderText("Game Name");
        textInput.showAndWait();
        String inp = textInput.getEditor().getText();
        if(!inp.equalsIgnoreCase("null")) {
            ArrayList<String> existingGames = model.getExistingGames();
            for(int i = 0; i < existingGames.size(); i++) {
                if(existingGames.get(i).equals(inp)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Overwrite Save?");
                    alert.setHeaderText("That game name already exists, do you want to overwrite?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        model.saveExistingGame(inp);
                        return;
                    }
                }
            }
            model.saveNewGame(inp);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Game Save Error");
            alert.setHeaderText("That game name is not allowed");
            alert.showAndWait();
            return;
        }
    }

    private void deserialiseAction() {
        // Here we take an existing serialisation string and feed it back into the model to retrieve that state.
        // We don't do any validation here, as that would leak model knowledge into the view.

        ArrayList<String> existingGames = model.getExistingGames();

        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList(existingGames);
        list.setItems(items);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Select save");
        alert.setHeaderText("Select a game save to load");

        GridPane content = new GridPane();
        content.add(list, 0, 0);
        alert.getDialogPane().setContent(content);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String game_name = list.getSelectionModel().getSelectedItem();

            try {
                model.deserialise(game_name);
            } catch (IllegalArgumentException e) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Serialisation Error");
                alertError.setHeaderText(e.getMessage());

                alertError.showAndWait();
                return;
            }

            boardPane.updateBoard();
        }
    }

    private void doNewGame() {
        // Here we have an action that we know would likely mutate the state of the model, and so the view should
        // update. Unlike the StatusBarPane that uses the observer pattern to do this, here we can just trigger it
        // because we know the model will mutate as a result of our call to it.
        // Generally speaking the observer pattern is superior - I would recommend using it instead of
        // doing it this way.

        model.newGame();
        boardPane.updateBoard();
    }

    public Scene getScene() {
        return this.scene;
    }
}
