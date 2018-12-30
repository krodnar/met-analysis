package main.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import main.contours.Contour;
import main.contours.ContoursHierarchy;
import main.preprocess.ImagePreprocessor;
import main.utils.Utils;
import main.views.ImageViewPane;
import org.opencv.core.Mat;

import java.net.URL;
import java.util.ResourceBundle;

public class ContoursController implements Initializable {

	private ImagePreprocessor preprocessor;

	private final ImageView contoursView = new ImageView();
	private final ImageViewPane contoursViewPane = new ImageViewPane(contoursView);

	private ContoursHierarchy contoursHierarchy;

	@FXML
	private BorderPane root;
	@FXML
	private TableView<Contour> contoursTable;
	@FXML
	private TableColumn<Contour, Integer> contourIndex;
	@FXML
	private TableColumn<Contour, Double> contourArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contoursView.setPreserveRatio(true);

		contourIndex.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getIndex()));
		contourArea.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getArea()));

		contoursView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			double ix = contoursView.getImage().getWidth();
			double iy = contoursView.getImage().getHeight();

			double ex = event.getX();
			double ey = event.getY();

			double bx = contoursView.getBoundsInLocal().getMaxX();
			double by = contoursView.getBoundsInLocal().getMaxY();

			double cx = bx / ix;
			double cy = by / iy;

			Contour contour = contoursHierarchy.getContourContainingPoint(ex / cx, ey / cy);
			if (contour != null) {
				contoursTable.getSelectionModel().select(contour);
				contoursTable.scrollTo(contour);
			}
		});
	}

	public void showContours() {
		if (!preprocessor.isReady()) {
			return;
		}
		preprocessor.finishProcessing();

		Mat source = preprocessor.getSource();
		contoursHierarchy = ContoursHierarchy.from(preprocessor.getProcessedMat(), source);
		Mat image = contoursHierarchy.getContoursImage();

		contoursView.setImage(Utils.mat2Image(image));
		root.setCenter(contoursViewPane);

		populateList();
	}

	private void populateList() {
		ObservableList<Contour> list = FXCollections.observableArrayList();
		list.setAll(contoursHierarchy.getContours());
		contoursTable.setItems(list);

		contoursTable.getSelectionModel().selectedItemProperty().addListener((observable, oldContour, newContour) -> {
			if (newContour == null) {
				return;
			}

			int index = newContour.getIndex();

			Mat image = contoursHierarchy.getHighlightedContour(index);
			contoursView.setImage(Utils.mat2Image(image));
		});
	}

	public ImagePreprocessor getPreprocessor() {
		return preprocessor;
	}

	public void setPreprocessor(ImagePreprocessor preprocessor) {
		this.preprocessor = preprocessor;
	}
}
