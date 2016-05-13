package com.basix86;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.controllers.mouse.picking.AWTMousePickingController;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.javafx.JavaFXChartFactory;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.picking.PickingSupport;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.pickable.PickablePoint;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import java.util.ArrayList;
import java.util.List;

public class SelectablePoints extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("SelectablePoints");

        // Jzy3d
        JavaFXChartFactory factory = new JavaFXChartFactory();

        AWTChart chart = (AWTChart) factory.newChart(Quality.Advanced, IChartComponentFactory.Toolkit.offscreen);

        List<PickablePoint> points = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PickablePoint point = new PickablePoint(new Coord3d(i, i, i), Color.BLACK, 10);
            points.add(point);
        }

        enablePicking(points, chart);

        Shape shape = new Shape();
        shape.add(points);

        chart.add(shape);

        ImageView imageView = factory.bindImageView(chart);

        // JavaFX
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        pane.getChildren().add(imageView);

        factory.addSceneSizeChangedListener(chart, scene);

        stage.setWidth(500);
        stage.setHeight(500);
    }


    //Not working
    private void enablePicking(List<PickablePoint> points, AWTChart chart) {
        AWTMousePickingController<?, ?> mousePicker = new AWTMousePickingController<>(chart, 10);
        PickingSupport picking = mousePicker.getPickingSupport();

        for (PickablePoint p : points) {
            picking.registerPickableObject(p, p);
        }

        picking.addObjectPickedListener((picked, ps) -> System.out.println(picked));
    }

}
