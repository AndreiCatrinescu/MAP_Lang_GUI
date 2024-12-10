package Lang.View;

import Lang.Exceptions.InterpreterError;
import Lang.Model.Statements.Statement;
import Lang.Model.Structures.ProgramState;
import Lang.Model.Values.Value;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class InterpreterGUI extends Application {
    private Stage appStage;
    private RunExample currentCommand;
    private int currentId;

    private ObservableList<String> updateOut() {
        try {
            var out = currentCommand.getProgramStates().getFirst().getOut().getAll();
            var updateContent = FXCollections.<String>observableArrayList();
            for (var o : out) {
                updateContent.add(o.toString());
            }
            return updateContent;
        } catch (Exception e) {
            return FXCollections.observableArrayList();
        }
    }

    private ObservableList<Integer> updateStatesList() {
        var states = currentCommand.getProgramStates();
        var updateContent = FXCollections.<Integer>observableArrayList();
        for (var s : states) {
            updateContent.add(s.getId());
        }
        return updateContent;
    }

    private ObservableList<String> updateFileTable() {
        try {
            var files = currentCommand.getProgramStates().getFirst().getFileTable().getContent();
            var updateContent = FXCollections.<String>observableArrayList();
            for (var f : files.keySet()) {
                updateContent.add(f.toString());
            }
            return updateContent;
        } catch (Exception e) {
            return FXCollections.observableArrayList();
        }
    }

    private ObservableList<Map.Entry<Integer, Value>> updateHeap() {
        try {
            var heap = currentCommand.getProgramStates().getFirst().getHeap().getContent();
            var updateContent = FXCollections.<Map.Entry<Integer, Value>>observableArrayList();
            updateContent.addAll(heap.entrySet());
            return updateContent;
        } catch (Exception e) {
            return FXCollections.observableArrayList();
        }
    }

    private ObservableList<Map.Entry<String, Value>> updateSymTable() {
        ProgramState state;
        try {
            state = currentCommand.getProgramStates()
                    .stream()
                    .filter(s -> s.getId() == currentId).toList().getFirst();
        } catch (NoSuchElementException e) {
            try {
                currentId = currentCommand.getProgramStates().getFirst().getId();
                state = currentCommand.getProgramStates().get(currentId);
            } catch (Exception e2) {
                return FXCollections.observableArrayList();
            }
        }

        var sym = state.getSymTable().getContent();

        var updateContent = FXCollections.<Map.Entry<String, Value>>observableArrayList();
        updateContent.addAll(sym.entrySet());

        return updateContent;
    }

    private ObservableList<String> updateExeStack() {
        ProgramState state;
        try {
            state = currentCommand.getProgramStates()
                    .stream()
                    .filter(s -> s.getId() == currentId).toList().getFirst();
        } catch (NoSuchElementException e) {
            try {
                currentId = currentCommand.getProgramStates().getFirst().getId();
                state = currentCommand.getProgramStates().get(currentId);
            } catch (Exception e2) {
                return FXCollections.observableArrayList();
            }
        }

        var exe = state.getExeStack().getContent();

        var updateContent = FXCollections.<String>observableArrayList();
        for (var e : exe.reversed()) {
            updateContent.add(e.toString());
        }

        return updateContent;
    }

    private Scene executionScreen() {
        currentId = 0;
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        Label countLabel = new Label("Program State Count");
        Label heapLabel = new Label("Heap");
        Label outLabel = new Label("Output");
        Label fileLabel = new Label("Files Opened");
        Label stateIdsLabel = new Label("Program States Running");
        Label symTableLabel = new Label("Variables");
        Label exeStackLabel = new Label("Execution Stack");

        Button executeButton = new Button("Step");
        executeButton.setMinSize(100, 45);

        Button backButton = new Button("New Program");
        backButton.setMinSize(100, 45);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                appStage.setScene(programChoiceScene());
            }
        });

        root.setMaxSize(1200, 750);

        TextField programStateCount = new TextField();
        programStateCount.setEditable(false);
        programStateCount.setFocusTraversable(false);

        TableView<Map.Entry<Integer, Value>> heapTable = new TableView<>();

        TableColumn<Map.Entry<Integer, Value>, String> addressColumn = new TableColumn<>("address");
        addressColumn.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(param.getValue().getKey())));

        TableColumn<Map.Entry<Integer, Value>, String> valueColumn = new TableColumn<>("value");
        valueColumn.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(param.getValue().getValue())));

        heapTable.getColumns().add(addressColumn);
        heapTable.getColumns().add(valueColumn);
        heapTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ListView<String> outList = new ListView<>();

        ListView<String> fileTable = new ListView<>();

        ListView<Integer> programStateIds = new ListView<>();
        programStateIds.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("ID " + item);
                }
            }
        });


        ListView<String> exeStackList = new ListView<>();

        TableView<Map.Entry<String, Value>> symTable = new TableView<>();

        TableColumn<Map.Entry<String, Value>, String> varNameColumn = new TableColumn<>("name");
        varNameColumn.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getKey()));

        TableColumn<Map.Entry<String, Value>, String> varValueColumn = new TableColumn<>("value");
        varValueColumn.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getValue().toString()));

        symTable.getColumns().add(varNameColumn);
        symTable.getColumns().add(varValueColumn);
        symTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        outList.setItems(updateOut());
        fileTable.setItems(updateFileTable());
        programStateIds.setItems(updateStatesList());
        programStateCount.setText("" + updateStatesList().size());
        heapTable.setItems(updateHeap());
        symTable.setItems(updateSymTable());
        exeStackList.setItems(updateExeStack());

        executeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentCommand.executeOne();
                outList.setItems(updateOut());
                fileTable.setItems(updateFileTable());
                heapTable.setItems(updateHeap());
                currentCommand.removeCompletedThreads();
                exeStackList.setItems(updateExeStack());
                programStateIds.setItems(updateStatesList());
                programStateCount.setText("" + updateStatesList().size());
                symTable.setItems(updateSymTable());
                fileTable.refresh();
                programStateIds.refresh();
                heapTable.refresh();
                outList.refresh();
                symTable.refresh();
                exeStackList.refresh();
            }
        });

        programStateIds.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (newValue != null)
                    currentId = newValue;
                else {
                    currentId = oldValue;
                }
//                System.out.println(currentId);
                symTable.setItems(updateSymTable());
                symTable.refresh();
                exeStackList.setItems(updateExeStack());
                exeStackList.refresh();
            }
        });

        heapTable.setFocusTraversable(false);
        outList.setFocusTraversable(false);
        fileTable.setFocusTraversable(false);
        programStateIds.setFocusTraversable(false);
        symTable.setFocusTraversable(false);
        executeButton.setFocusTraversable(false);
        backButton.setFocusTraversable(false);
        exeStackList.setFocusTraversable(false);


        root.add(programStateCount, 1, 0);
        root.add(countLabel, 0, 0);
        root.add(heapTable, 3, 1);
        root.add(heapLabel, 2, 1);
        root.add(outList, 1, 2);
        root.add(outLabel, 0, 2);
        root.add(fileTable, 3, 2);
        root.add(fileLabel, 2, 2);
        root.add(programStateIds, 3, 0);
        root.add(stateIdsLabel, 2, 0);
        root.add(symTable, 1, 1);
        root.add(symTableLabel, 0, 1);
        root.add(executeButton, 4, 1);
        root.add(backButton, 4, 2);
        root.add(exeStackLabel, 4, 0);
        root.add(exeStackList, 5, 0);
        return new Scene(root);
    }

    private Scene programChoiceScene() {
        HBox root = new HBox(5);
        root.setPadding(new Insets(50));
        ListView<String> programStateList = new ListView<>(new ObservableListBase<>() {
            @Override
            public String get(int index) {
                return "Program " + index + ":\n\n" + Examples.getAllExamples().get(index).toString();
            }

            @Override
            public int size() {
                return Examples.getAllExamples().size();
            }
        });

        programStateList.setFocusTraversable(false);

        programStateList.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        Statement program = Examples.getAllExamples().get(newValue.intValue());
                        RunExample command = new RunExample(newValue.toString(), "", program);
                        try {
                            command.typecheck();
                        } catch (InterpreterError e) {
                            System.out.println(e.getMessage());
                            return;
                        }
                        command.setLogFile("log_program" + newValue + ".txt");
                        currentCommand = command;
                        appStage.setScene(executionScreen());
                    }
                }
        );
        root.getChildren().addAll(programStateList);
        return new Scene(root);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Interpreter");
        stage.setScene(programChoiceScene());
        stage.show();
        appStage = stage;
    }

    public static void main(String[] args) {
        launch(args); //an object Application is created
    }

}