package main.contours;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ContoursHierarchyVisualiser {

	private static final Scalar[] colors = new Scalar[]{
			new Scalar(0, 0, 0),
			new Scalar(0, 0, 255),
			new Scalar(0, 255, 0),
			new Scalar(0, 255, 255),
			new Scalar(255, 0, 0),
			new Scalar(255, 0, 255),
			new Scalar(255, 255, 0),
			new Scalar(255, 255, 255)
	};

	private Mat sourceImage = new Mat();
	private ContoursHierarchy hierarchy;

	private Mat contoursImage = new Mat();
	private boolean contoursDrawn = false;

	private Mat highlightImage = new Mat();
	private boolean highlightDrawn = false;

	public ContoursHierarchyVisualiser() {
	}

	public ContoursHierarchyVisualiser(ContoursHierarchy contoursHierarchy, Mat sourceImage) {
		this.hierarchy = contoursHierarchy;
		this.sourceImage = sourceImage;
		reset();
	}

	public Mat getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(Mat sourceImage) {
		this.sourceImage = sourceImage;
		reset();
	}

	public void setHierarchy(ContoursHierarchy hierarchy) {
		this.hierarchy = hierarchy;
		reset();
	}

	public Mat getContoursImage() {
		if (sourceImage == null) {
			throw new IllegalStateException("No source image provided.");
		}

		if (hierarchy == null) {
			throw new IllegalStateException("No contours hierarchy provided.");
		}

		if (!contoursDrawn) {
			for (int i = 0; i < hierarchy.getContours().size(); i++) {
				Scalar color = colors[hierarchy.getContours().get(i).getHierarchyLevel() % colors.length];
				Imgproc.drawContours(contoursImage, hierarchy.getContourPoints(), i, color, 3);
			}

			contoursDrawn = true;
		}

		return contoursImage;
	}

	public Mat getHighlightedContour(Contour contour) {
		return getHighlightedContour(contour.getIndex());
	}

	public Mat getHighlightedContour(int index) {
		if (sourceImage == null) {
			throw new IllegalStateException("No source image provided.");
		}

		if (hierarchy == null) {
			throw new IllegalStateException("No contours hierarchy provided.");
		}

		if (!highlightDrawn) {
			Scalar color = Scalar.all(0);
			Imgproc.drawContours(highlightImage, hierarchy.getContourPoints(), -1, color, 3);
			highlightDrawn = true;
		}

		Mat temp = highlightImage.clone();
		Scalar color = Scalar.all(255);
		Imgproc.drawContours(temp, hierarchy.getContourPoints(), index, color, 3);

		return temp;
	}


	private void reset() {
		contoursDrawn = false;
		contoursImage = sourceImage.clone();
		highlightDrawn = false;
		highlightImage = sourceImage.clone();
	}
}