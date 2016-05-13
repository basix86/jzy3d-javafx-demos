package com.basix86;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.javafx.JavaFXChartFactory;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class MultipleSurface3dDemo extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Multiple surface plot");

        // Jzy3d
        JavaFXChartFactory factory = new JavaFXChartFactory();
        AWTChart chart = getDemoChart(factory, "offscreen");
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

    private AWTChart getDemoChart(JavaFXChartFactory factory, String toolkit) {
        // -------------------------------
        // Define a function to plot
        Mapper mapper_1 = new Mapper() {
            @Override
            public double f(double x, double y) {
                return x * Math.sin(x * y);
            }
        };

        Mapper mapper_2 = new Mapper() {
            @Override
            public double f(double x, double y) {
                return y * Math.cos(x) + 5;
            }
        };

        // Define range and precision for the function to plot
        Range range = new Range(-3, 3);
        int steps = 80;

        // Create the object to represent the function over the given range.
        final Shape surface_1 = Builder.buildOrthonormal(mapper_1, range, steps);
        surface_1.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface_1.getBounds().getZmin(), surface_1.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface_1.setFaceDisplayed(true);
        surface_1.setWireframeDisplayed(false);

        // Create the object to represent the function over the given range.
        final Shape surface_2 = Builder.buildOrthonormal(mapper_2, range, steps);
        surface_2.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface_1.getBounds().getZmin(), surface_1.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface_2.setFaceDisplayed(true);
        surface_2.setWireframeDisplayed(false);

        // -------------------------------
        // Create a chart
        Quality quality = Quality.Advanced;
        //quality.setSmoothPolygon(true);
        //quality.setAnimated(true);

        // let factory bind mouse and keyboard controllers to JavaFX node
        AWTChart chart = (AWTChart) factory.newChart(quality, toolkit);
        chart.getScene().getGraph().add(surface_1);
        chart.getScene().getGraph().add(surface_2);
        return chart;
    }
}
