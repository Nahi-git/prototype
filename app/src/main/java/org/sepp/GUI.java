package org.sepp;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main (String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuBar menuBar = new MenuBar();

        //Created menus at the top of GUI
        Menu fileMenu = new Menu("File");
        Menu configMenu = new Menu("Config");
        Menu tasksMenu = new Menu("Tasks");
        Menu helpMenu = new Menu("Help");

        //Menu items for file Menu
        MenuItem run = new MenuItem("Run...");
        MenuItem directory = new MenuItem("Set directory");

        //Retrieves all file menu items into the fileMenu
        fileMenu.getItems().addAll(
                run,
                directory
        );

        //Menu items for configMenu
        MenuItem newConfig = new MenuItem("Create new config");
        Menu loadConfig = new Menu("Load config");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As...");
        MenuItem close = new MenuItem("Close");
        MenuItem pref = new MenuItem("Preferences...");
        MenuItem quit = new MenuItem("Quit");

        //Retrieves all config menu items into the configMenu
        configMenu.getItems().addAll(
                newConfig,
                loadConfig,
                new SeparatorMenuItem(),
                save,
                saveAs,
                close,
                new SeparatorMenuItem(),
                pref,
                quit

        );

        //Menu items for taskMenu
        MenuItem newTask = new MenuItem("New");
        MenuItem delete = new MenuItem("Delete");

        //Retrieves all task items into taskMenu
        tasksMenu.getItems().addAll(
                newTask,
                delete
        );

        // Gathers all the menus to the menuBar
        menuBar.getMenus().addAll(fileMenu,configMenu,tasksMenu,helpMenu);

        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);

        Scene scene = new Scene (layout, 1024, 768);
        primaryStage.setTitle("Prototype");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
