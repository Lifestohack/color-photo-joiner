package main.java;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorPhotoJoiner {

	public static void main(String[] args) {

		String savePath = "D:\\Users\\Diwas Bhattarai\\Pictures\\Output.png";
		String openPath = "D:\\Users\\Diwas Bhattarai\\Pictures\\Input.jpg";
		String searchFolder = "D:\\Media\\Images\\France\\Paris with schatzi";

		ImageManager imgMgr = new ImageManager();
		File readFolder = new File(searchFolder);

		BufferedImage img = imgMgr.readImageFromFile(openPath);

		Map<String, int[]> imageToProcessRGB = new HashMap<String, int[]>();
		
		int i = 0;
		double total = img.getWidth() *img.getHeight();
		for (int y = 0; y < img.getHeight(); y++) {
		    for (int x = 0; x < img.getWidth(); x++) {	
		    	System.out.println((i/total)*100);
		    	i++;
		    	imageToProcessRGB.put(x+","+y, PhotoToColor.getPixelData(img, x, y));
		    }
		}
		
		
//		if (img == null) {
//			System.out.println("Image " + openPath + " could not be loaded.");
//		}

		System.out.println("Image Pixel: " + img.getWidth() + "x" + img.getHeight());

		Map<Integer, String> extensions = new HashMap<Integer, String>();

		extensions.put(1, "png");
		extensions.put(2, "jpg");
		extensions.put(6, "jpeg");
		// extensions.put(3, "mov");
		// extensions.put(4, "mp4");
		// extensions.put(5, "nef");
		// extensions.put(7, "db");
		// extensions.put(8, "wmv");
		extensions.put(9, "gif");
		// extensions.put(10, "pptx");

		Map<Integer, String> map = imgMgr.getAllImages(readFolder, extensions);

		Map<String, Color> averagecolorofallimages = PhotoToColor.ConvertPhotoToColor(map);

//		Color averageColor = PhotoToColor.averageColor(img, 0, 0, img.getWidth(), img.getHeight());

//		for (Map.Entry<Integer, String> entry : map.entrySet()) {
//			PhotoToColor.getDistance();		
//		}
		System.out.println(imgMgr.getNumberOfImagesFound());

		
		int width = img.getWidth();
		int height = img.getHeight();
	
		int[] d = imageToProcessRGB.get(0+","+0);
		BufferedImage img1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int a = 255; // alpha
				
				
				
				
				
				int r = imageToProcessRGB.get(x+","+y)[0]; // red
				int g = imageToProcessRGB.get(x+","+y)[1]; // green
				int b = imageToProcessRGB.get(x+","+y)[2]; // blue

				Color averageColor = PhotoToColor.getNearestColor(new Color(r,g,b), averagecolorofallimages);
				
				int red = averageColor.getRed(); // red
				int green = averageColor.getGreen(); // green
				int blue = averageColor.getBlue(); // blue
				
				
				int p = (a << 24) | (red << 16) | (green << 8) | blue; // pixel

				img1.setRGB(x, y, p);
			}
		}
		imgMgr.saveImageToFile(img1, savePath);
		System.out.print("Image saved successfully.");

	}
}
