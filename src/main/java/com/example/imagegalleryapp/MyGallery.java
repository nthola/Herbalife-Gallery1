package com.example.imagegalleryapp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyGallery extends Application {
    // Define multiple albums, each with their own set of thumbnail and full-size image paths
    private final List<String[]> albums = new ArrayList<>();
    private int currentAlbumIndex = 0;

    private ImageView currentImageView;
    private int currentIndex = -1; // Start with no image selected

    @Override
    public void start(Stage primaryStage) {

        initializeAlbums();

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        // Display thumbnails in a grid
        GridPane thumbnailGrid = new GridPane();
        thumbnailGrid.setAlignment(Pos.CENTER);
        thumbnailGrid.setHgap(10);
        thumbnailGrid.setVgap(10);

        String[] currentAlbum = albums.get(currentAlbumIndex);
        for (int i = 0; i < currentAlbum.length; i++) {
            ImageView thumbnail = createThumbnail(currentAlbum[i]);
            int index = i;
            thumbnail.setOnMouseClicked(e -> showFullImage(index));
            thumbnailGrid.add(thumbnail, i % 2, i / 2);
        }

        // Add album navigation buttons
        Button prevAlbumButton = new Button("Previous Album");
        prevAlbumButton.setOnAction(e -> showPreviousAlbum());

        Button nextAlbumButton = new Button("Next Album");
        nextAlbumButton.setOnAction(e -> showNextAlbum());

        HBox albumNavBox = new HBox(10);
        albumNavBox.setAlignment(Pos.CENTER);
        albumNavBox.getChildren().addAll(prevAlbumButton, nextAlbumButton);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Gallery");
        primaryStage.show();

        root.getChildren().addAll(albumNavBox, thumbnailGrid);

    }

    private ImageView createThumbnail(String imagePath) {
        // Load thumbnail from the resources
        Image thumbnail = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
        ImageView imageView = new ImageView(thumbnail);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        return imageView;
    }

    private void showFullImage(int index) {
        if (index == currentIndex) return; // Prevent reloading the same image

        // Clear previous image if any
        if (currentImageView != null) {
            currentImageView.setImage(null);
        }

        String[] currentAlbum = albums.get(currentAlbumIndex);
        // Display full-size image
        Image fullImage = new Image(Objects.requireNonNull(getClass().getResource(currentAlbum[index])).toExternalForm());
        currentImageView = new ImageView(fullImage);
        currentImageView.setFitWidth(400);
        currentImageView.setFitHeight(400);

        // Add navigation buttons
        Button previousButton = new Button("Previous");
        previousButton.setOnAction(e -> showFullImage((index - 1 + currentAlbum.length) % currentAlbum.length));

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> showFullImage((index + 1) % currentAlbum.length));

        Button backButton = new Button("Back to Thumbnails");
        backButton.setOnAction(e -> start(new Stage())); // Open a new stage for thumbnails

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(previousButton, nextButton, backButton);

        VBox imageBox = new VBox(10);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.getChildren().addAll(currentImageView, buttonBox);


        Scene scene = new Scene(imageBox, 600, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Full Image");
        stage.show();

        currentIndex = index;
    }

    private void showPreviousAlbum() {
        if (currentAlbumIndex > 0) {
            currentAlbumIndex--;
            start(new Stage()); // Refresh the UI to show thumbnails of the previous album
        }
    }

    private void showNextAlbum() {
        if (currentAlbumIndex < albums.size() - 1) {
            currentAlbumIndex++;
            start(new Stage()); // Refresh the UI to show thumbnails of the next album
        }
    }

    private void initializeAlbums() {
        // Define albums with at least 4 images each
        String[] album1 = {
                "/Images/aloe.jpg",
                "/Thumbnail/combo.jpg",
                "/Thumbnail/tea.jpg",
                "/Thumbnail/shake1.jpg"
        };
        albums.add(album1);

        String[] album2 = {
                "/Thumbnail/aloeB.jpg",
                "/Thumbnail/home.jpg",
                "/Thumbnail/images.jpg",
                "/Thumbnail/shake2.jpg"
        };
        albums.add(album2);

        String[] album3 = {
                "/skin/back.jpg",
                "/skin/colon.jpg",
                "/skin/images (5).jpg",
                "/skin/sky.jpg"
        };
        albums.add(album3);

        String[] album4 = {
                "/energy/aloeB.jpg",
                "/energy/home.jpg",
                "/energy/images.jpg",
                "/energy/shake1.jpg"
        };
        albums.add(album4);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
