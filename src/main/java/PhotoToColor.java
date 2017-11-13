package main.java;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

public class PhotoToColor {

	public static Color getNearestColor(Color a, Map<String, Color> b) {
		Map<Integer, Color> distances = new HashMap<Integer, Color>();
		for (Entry<String, Color> entry : b.entrySet()) {
			int distance = PhotoToColor.getDistance(a, entry.getValue());
			distances.put(distance, entry.getValue());
		}

		return distances.get(minIndex(distances));

	}

	public static int minIndex(Map<Integer, Color> distances) {
		Entry<Integer, Color> min = null;
		for (Entry<Integer, Color> entry : distances.entrySet()) {
			if (min == null || min.getKey() > entry.getKey()) {
				min = entry;
			}
		}
		return min.getKey();

	}

	public static int getDistance(Color a, Color b) {
		return Math.abs(a.getRed() - b.getRed()) + Math.abs(a.getRed() - b.getRed())
				+ Math.abs(a.getRed() - b.getRed());
	}

	public static Map<String, Color> ConvertPhotoToColor(Map<Integer, String> map) {
		Map<String, Color> imageColors = new HashMap<String, Color>();
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			Integer key = entry.getKey();
			String imagePath = entry.getValue();
			System.out.println(key + " " + imagePath);
			BufferedImage img;
			try {
				img = ImageIO.read(new File(imagePath));
				Color color = averageColor(img, 0, 0, img.getWidth(), img.getHeight());
				imageColors.put(imagePath, color);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return imageColors;
	}

	public static Color averageColor(BufferedImage bi, int x0, int y0, int w, int h) {
		int x1 = x0 + w;
		int y1 = y0 + h;
		long sumr = 0, sumg = 0, sumb = 0;
		for (int x = x0; x < x1; x++) {
			for (int y = y0; y < y1; y++) {
				Color pixel = new Color(bi.getRGB(x, y));
				sumr += pixel.getRed();
				sumg += pixel.getGreen();
				sumb += pixel.getBlue();
			}
		}
		int num = w * h;
		return new Color((int) Math.ceil(sumr / num), (int) Math.ceil(sumg / num), (int) Math.ceil(sumb / num));
	}

	public static int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);

		int rgb[] = new int[] { (argb >> 16) & 0xff, // red
				(argb >> 8) & 0xff, // green
				(argb) & 0xff // blue
		};

		return rgb;
	}
}
