package main.contours;

import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Contour {

	private int index;
	private int hierarchyLevel;

	private Contour parent;
	private List<Contour> childs;

	private MatOfPoint points;

	private int aspectRatio;
	private double area;
	private double perimeter;
	private double extent;

	public Contour(MatOfPoint points) {
		this(-1, -1, points);
	}

	public Contour(int index, MatOfPoint points) {
		this(index, -1, points);
	}

	public Contour(int index, int hierarchyLevel, MatOfPoint points) {
		this.hierarchyLevel = hierarchyLevel;
		this.index = index;
		this.points = points;
		childs = new ArrayList<>();

		process();
	}

	private void process() {
		Rect boundingRect = Imgproc.boundingRect(points);
		MatOfPoint2f points2f = new MatOfPoint2f(points.toArray());

		area = Imgproc.contourArea(points);
		perimeter = Imgproc.arcLength(points2f, true);
		aspectRatio = boundingRect.width / boundingRect.height;
		extent = area / (boundingRect.area());
	}

	public void addChild(Contour child) {
		childs.add(child);
	}

	public void removeChild(Contour child) {
		childs.remove(child);
	}

	public boolean hasChilds() {
		return childs.size() != 0;
	}

	public List<Contour> getChilds() {
		return childs;
	}

	public void setChilds(List<Contour> childs) {
		this.childs = childs;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public Contour getParent() {
		return parent;
	}

	public void setParent(Contour parent) {
		this.parent = parent;
	}

	public int getHierarchyLevel() {
		return hierarchyLevel;
	}

	public void setHierarchyLevel(int hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
	}

	public MatOfPoint getPoints() {
		return points;
	}

	public void setPoints(MatOfPoint points) {
		this.points = points;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(int aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getPerimeter() {
		return perimeter;
	}

	public void setPerimeter(double perimeter) {
		this.perimeter = perimeter;
	}

	public double getExtent() {
		return extent;
	}

	public void setExtent(double extent) {
		this.extent = extent;
	}
}
