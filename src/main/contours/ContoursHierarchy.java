package main.contours;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContoursHierarchy {

	private List<Contour> contours;

	private List<MatOfPoint> contourPoints;
	private Mat hierarchy;

	public static ContoursHierarchy from(Mat image) {
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(image, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		return new ContoursHierarchy(contours, hierarchy);
	}

	public ContoursHierarchy(List<MatOfPoint> contoursPoints, Mat hierarchy) {
		this.contourPoints = contoursPoints;
		this.hierarchy = hierarchy;
		this.contours = new ArrayList<>();

		processContours(contoursPoints, hierarchy);
	}

	private void processContours(List<MatOfPoint> contoursPoints, Mat hierarchy) {
		for (int i = 0; i < contoursPoints.size(); i++) {
			Contour contour = new Contour(i, contoursPoints.get(i));
			contours.add(contour);
		}

		for (Contour contour : contours) {
			int index = contour.getIndex();

			int parentIndex = (int) hierarchy.get(0, index)[3];
			if (parentIndex != -1) {
				Contour parent = contours.get(parentIndex);

				contour.setParent(parent);
				parent.addChild(contour);
			}
		}

		List<Contour> visited = new ArrayList<>();

		while (visited.size() != contours.size()) {
			int index = 0;
			Contour current = contours.get(index);
			while (visited.contains(current)) {
				current = contours.get(++index);
			}

			while (current.hasParent()) {
				current = current.getParent();
			}
			current.setHierarchyLevel(0);
			visited.add(current);

			int level = 1;
			List<Contour> currentLevel;
			List<Contour> nextLevel = new ArrayList<>();

			currentLevel = new ArrayList<>(current.getChilds());

			do {
				for (Contour child : currentLevel) {
					child.setHierarchyLevel(level);
					visited.add(child);
					nextLevel.addAll(child.getChilds());
				}

				currentLevel = new ArrayList<>(nextLevel);
				nextLevel.clear();
				level++;
			} while (!currentLevel.isEmpty());
		}
	}

	public Contour getContourContainingPoint(double x, double y) {
		List<Contour> c = new ArrayList<>();

		for (Contour contour : contours) {
			MatOfPoint2f points2f = new MatOfPoint2f(contour.getPoints().toArray());
			double dist = Imgproc.pointPolygonTest(points2f, new Point(x, y), false);

			if (dist >= 1) {
				c.add(contour);
			}
		}

		return c.stream().max(Comparator.comparingInt(Contour::getHierarchyLevel)).orElse(null);
	}

	public List<Contour> getContours() {
		return contours;
	}

	public void setContours(List<Contour> contours) {
		this.contours = contours;
	}

	public List<MatOfPoint> getContourPoints() {
		return contourPoints;
	}

	public void setContourPoints(List<MatOfPoint> contourPoints) {
		this.contourPoints = contourPoints;
	}

	public Mat getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Mat hierarchy) {
		this.hierarchy = hierarchy;
	}
}
