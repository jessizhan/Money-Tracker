package ui;

import exceptions.ImpossibleAmountException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.math.BigDecimal;

// this project allows you keep track of expenses made
// each money entry has the date, category (for expenses) and amount spent or gained
// you can also view your previous entries and check your current balance

public class MoneyTrackerMenu extends Application {
    private static final String COLOR_BG = "lightsteelblue";
    private static final String COLOR_GRAY = "#181818";
    private static final String COLOR_WHITE = "white";

    private LogBook logbook = new LogBook();
    private Scene sceneMain;

    // EFFECTS: returns logbook
    public LogBook getLogbook() {
        return logbook;
    }

    // EFFECTS: replace logbook with otherLogBook
    public void setLogbook(LogBook otherLogBook) {
        logbook = otherLogBook;
    }

    // MODIFIES: this
    // EFFECTS: creates the GUI
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("🤑Money Tracker🤑");

        GridPane layout = options(primaryStage);
        setBgColor(layout);

        sceneMain = new Scene(layout, 400, 200);

        primaryStage.setScene(sceneMain);
        primaryStage.show();
    }

    private void setBgColor(GridPane layout) {
        layout.setStyle("-fx-background-color: " + COLOR_BG + ";");
    }

    private void setButtonStyle(Button button, String bg, String fg) {
        button.setStyle("-fx-background-color: " + bg + "; -fx-text-fill: " + fg + ";");
    }

    private GridPane options(Stage primaryStage) {
        GridPane layout = getGridPane();
        layout.add(createIncomeButton(primaryStage), 0, 0);
        layout.add(createExpenseButton(primaryStage), 0, 1);
        layout.add(createEntriesButton(primaryStage), 0, 2);
        layout.add(createBalanceButton(primaryStage), 0, 3);

        return layout;
    }

    private Button createIncomeButton(Stage primaryStage) {
        Button button = new Button("Add New Income Entry");
        setButtonStyle(button, "white", "black");

        button.setOnAction(event -> primaryStage.setScene(new Scene(incomeScene(primaryStage), 400,200)));

        return button;
    }

    private Button createExpenseButton(Stage primaryStage) {
        Button button = new Button("Add New Expense Entry");
        setButtonStyle(button, "#F5F5F5", "black");

        button.setOnAction(event -> primaryStage.setScene(new Scene(expenseScene(primaryStage), 400, 200)));

        return button;
    }

    private Button createEntriesButton(Stage primaryStage) {
        Button button = new Button("View Entries");
        setButtonStyle(button, "#E8E8E8", "black");

        button.setOnAction(event -> primaryStage.setScene(new Scene(entriesScene(primaryStage),400,200)));

        return button;
    }

    private Button createBalanceButton(Stage primaryStage) {
        Button button = new Button("Check Balance");
        setButtonStyle(button, "#DCDCDC", "black");

        button.setOnAction(event -> primaryStage.setScene(new Scene(balanceScene(primaryStage), 400,200)));

        return button;
    }

    private GridPane balanceScene(Stage primaryStage) {
        GridPane layout = getGridPane();
        Button buttonBack = new Button("Back");
        setButtonStyle(buttonBack, COLOR_GRAY, COLOR_WHITE);
        layout.add(buttonBack,0,1);

        Text text = new Text("Current balance: $" + logbook.getBalance().toString());
        text.setStyle("-fx-font: normal bold 30px 'sans-serif' ");
        layout.add(text, 0, 0);

        buttonBack.setOnAction(event -> backToMainScene(primaryStage));

        return layout;
    }

    private VBox entriesScene(Stage primaryStage) {
        Button buttonBack = new Button("Back");
        setButtonStyle(buttonBack, COLOR_GRAY, COLOR_WHITE);
        buttonBack.setAlignment(Pos.TOP_LEFT);
        VBox layout = new VBox(buttonBack);
        layout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        Text text;
        ObservableList list = layout.getChildren();

        for (MoneyEntry e: logbook.getEntries()) {
            String string = "- " + e.toString();
            text = new Text(string);
            list.add(text);
        }

        buttonBack.setOnAction(event -> backToMainScene(primaryStage));

        return layout;
    }


    private GridPane incomeScene(Stage primaryStage) {
        Button buttonSave = new Button("Save");
        Button buttonBack = new Button("Back");
        setButtonStyle(buttonBack, COLOR_GRAY, COLOR_WHITE);
        setButtonStyle(buttonSave, COLOR_GRAY, COLOR_WHITE);

        GridPane layout = new GridPane();
        Text amountLabel = new Text("Amount:");
        TextField amountText = new TextField();

        Text dateLabel = new Text("Date:");
        DatePicker datePicker = new DatePicker();

        createGridPane(layout);

        incomeLayoutButtons(buttonSave, buttonBack, layout, amountLabel, amountText, dateLabel, datePicker);

        buttonBack.setOnAction(event -> backToMainScene(primaryStage));

        buttonSave.setOnAction(event -> incomeSaveClicked(primaryStage, amountText, datePicker));


        return layout;
    }

    private void incomeLayoutButtons(Button buttonSave, Button buttonBack, GridPane layout, Text amountLabel,
                                     TextField amountText, Text dateLabel, DatePicker datePicker) {
        layout.add(amountLabel, 0, 0);
        layout.add(amountText, 1, 0);
        layout.add(dateLabel, 0, 1);
        layout.add(datePicker, 1, 1);
        layout.add(buttonSave, 0,3);
        layout.add(buttonBack, 1,3);
    }

    private void incomeSaveClicked(Stage primaryStage, TextField amountText, DatePicker datePicker) {
        Income income = new Income();
        BigDecimal amount = new BigDecimal(amountText.getText());
        try {
            income.setAmount(amount);
        } catch (ImpossibleAmountException e) {
            e.printStackTrace();
        }
        income.setDate(datePicker.getValue());
        logbook.addEntry(income);
        logbook.plus(amount);
        backToMainScene(primaryStage);
    }

    private GridPane expenseScene(Stage primaryStage) {
        Text categoryLabel = new Text("Category:");
        ChoiceBox<String> categoryChoiceBox = getStringChoiceBox();

        Button buttonSave = new Button("Save");
        Button buttonBack = new Button("Back");
        setButtonStyle(buttonBack, COLOR_GRAY, COLOR_WHITE);
        setButtonStyle(buttonSave, COLOR_GRAY, COLOR_WHITE);

        Text amountLabel = new Text("Amount:");
        TextField amountText = new TextField();

        Text dateLabel = new Text("Date:");
        DatePicker datePicker = new DatePicker();

        GridPane layout = getGridPane();

        expenseLayoutButtons(categoryLabel, categoryChoiceBox, buttonSave, buttonBack, layout, amountLabel, amountText,
                dateLabel, datePicker);

        buttonBack.setOnAction(event -> backToMainScene(primaryStage));

        buttonSave.setOnAction(event -> expenseSaveClicked(primaryStage, categoryChoiceBox, amountText, datePicker));

        return layout;
    }

    private GridPane getGridPane() {
        GridPane layout = new GridPane();
        createGridPane(layout);
        return layout;
    }

    private ChoiceBox<String> getStringChoiceBox() {
        ChoiceBox<String> categoryChoiceBox = new ChoiceBox<>();
        categoryChoiceBox.getItems().addAll("Drink", "Food", "Misc.", "Shopping", "Transportation");
        return categoryChoiceBox;
    }

    private void expenseSaveClicked(Stage primaryStage, ChoiceBox<String> categoryChoiceBox,
                                    TextField amountText, DatePicker datePicker) {
        Expense expense = new Expense();
        BigDecimal amount = new BigDecimal(amountText.getText());
        try {
            expense.setAmount(amount);
        } catch (ImpossibleAmountException e) {
            e.printStackTrace();
        }
        expense.setDate(datePicker.getValue());
        expense.setCategory(new Category(categoryChoiceBox.getValue()));
        logbook.addEntry(expense);
        logbook.minus(amount);
        backToMainScene(primaryStage);
    }

    private void expenseLayoutButtons(Text categoryLabel, ChoiceBox<String> categoryChoiceBox, Button buttonSave,
                                      Button buttonBack, GridPane layout, Text amountLabel, TextField amountText,
                                      Text dateLabel, DatePicker datePicker) {
        layout.add(amountLabel, 0, 0);
        layout.add(amountText, 1, 0);
        layout.add(dateLabel, 0, 1);
        layout.add(datePicker, 1, 1);
        layout.add(categoryLabel, 0, 2);
        layout.add(categoryChoiceBox, 1, 2);
        layout.add(buttonSave, 0,4);
        layout.add(buttonBack, 1,4);
    }

    private void backToMainScene(Stage primaryStage) {
        primaryStage.setScene(sceneMain);
    }

    private void createGridPane(GridPane layout) {
        layout.setMinSize(400, 200);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(5);
        layout.setHgap(5);
        layout.setAlignment(Pos.CENTER);
        setBgColor(layout);
    }

    // EFFECTS: launches the GUI
    public static void main(String[] args) {
        launch(args);
    }
}
