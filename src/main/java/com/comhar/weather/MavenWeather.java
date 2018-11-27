package com.comhar.weather;

import  java.lang.System.*;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;

public  class MavenWeather extends Application {

    private TextField field;
    private String searchString;
    private WeatherReport weatherReport;
    private String displayInfo;
    private ChoiceBox<String> box;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUI(primaryStage);
    }

    private void initUI(Stage stage)
    {
        LocationService locationService = new LocationService();
        WeatherService weatherService = new WeatherService();

        GridPane root = new GridPane();
        root.setHgap(8);
        root.setVgap(8);
        root.setPadding(new Insets(5));

        ColumnConstraints cons1 = new ColumnConstraints();
        cons1.setHgrow(Priority.NEVER);
        root.getColumnConstraints().add(cons1);

        ColumnConstraints cons2 = new ColumnConstraints();
        cons2.setHgrow(Priority.ALWAYS);
        root.getColumnConstraints().addAll(cons1,cons2);

        RowConstraints rcons1 = new RowConstraints();
        rcons1.setVgrow(Priority.NEVER);

        RowConstraints rcons2 = new RowConstraints();
        rcons2.setVgrow(Priority.NEVER);

        root.getRowConstraints().addAll(rcons1, rcons2);

        CategoryAxis xAxis = new CategoryAxis();

        NumberAxis yAxis = new NumberAxis();

        Label lbl = new Label("Enter a city or place name: ");
        field = new TextField();
        Button srchBtn = new Button("Search");
         box = new ChoiceBox<String>(FXCollections.observableArrayList("Temp","Pressure","Wind","Clouds"));

        BarChart barChart = new BarChart(xAxis,yAxis);
        XYChart.Series data = new XYChart.Series();

        SingleSelectionModel model = box.getSelectionModel();
        model.selectedItemProperty().addListener((ObservableValue observable,Object oldValue,Object newValue)->{
                displayInfo= newValue.toString();

                data.getData().clear();
                barChart.getData().clear();

                switch(displayInfo)
                {
                    case "Temp":
                        yAxis.setLabel("Degrees Celsius");
                        barChart.setTitle("Temperature in "+weatherReport.getName());
                        data.getData().add(new XYChart.Data(weatherReport.getName(),weatherReport.getMain().getTemp()-273.15));
                        barChart.getData().add(data);
                        break;
                    case "Pressure":
                        yAxis.setLabel("hPa");
                        barChart.setTitle("Atmospheric Pressure in "+weatherReport.getName());
                        data.getData().add(new XYChart.Data(weatherReport.getName(),weatherReport.getMain().getPressure()));
                        barChart.getData().add(data);
                        break;
                    case "Wind":
                        yAxis.setLabel("Wind speed m/s");
                        barChart.setTitle("Wind Speed in "+weatherReport.getName());
                        data.getData().add(new XYChart.Data(weatherReport.getName(),weatherReport.getWind().getSpeed()));
                        barChart.getData().add(data);
                        break;
                    case "Clouds":
                        yAxis.setLabel("Cloud Coverage in %");
                        barChart.setTitle("Clouds in "+weatherReport.getName());
                        data.getData().add(new XYChart.Data(weatherReport.getName(),weatherReport.getClouds().getAll()));

                        barChart.getData().add(data);
                        break;
                }
        });
        barChart.setLegendVisible(false);


        GridPane.setHalignment(srchBtn, HPos.RIGHT);
        ButtonHandler handler = new ButtonHandler(locationService,weatherService);
        srchBtn.setOnAction(handler);


        root.add(lbl,0,0);
        root.add(field,1,0,3,1);
        root.add(srchBtn,3,2);
        root.add(box,0,3);
        root.add(barChart,0,4,4,4);
        Scene scene = new Scene(root,750,500);

        lbl.setFont(Font.font("Roboto", FontWeight.NORMAL,18));


        stage.setTitle("FX weather");
        stage.setScene(scene);
        stage.show();
    }


    public static  void main(String args[])
    {
        launch(args);
    }
    private class ButtonHandler implements EventHandler<ActionEvent>{

        private WeatherService weatherService;
        private LocationService locationService;

        public ButtonHandler(LocationService locationService, WeatherService weatherService)
        {
            this.locationService = locationService;
            this.weatherService = weatherService;
        }

        public void handle(ActionEvent event)
        {
            searchString = field.getText();

            Location location = locationService.GetLocation(searchString);
            weatherReport = weatherService.GetWeather(location.getLat(),location.getLon());


        }
    }
}



