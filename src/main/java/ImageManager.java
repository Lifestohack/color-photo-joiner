package main.java;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageManager {

	private BufferedImage img = null;
	private Map<Integer, String> map = null;
	private List<String> imagePaths = null;
	private int imageCount = 0;
	private List<String> ignoredExtensions = new ArrayList<String>();

	public Map<Integer, String> getAllImages(final File folder, Map<Integer, String> extensions) {
		if (map == null) {
			map = new HashMap<Integer, String>();
		}
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				getAllImages(fileEntry, extensions);
			} else {
				String extension = getFileExtension(fileEntry.getAbsolutePath());
				if (isImageFile(extension, extensions)) {
					map.put(imageCount++, fileEntry.getAbsolutePath());
				} else {
					if (!ignoredExtensions.contains(extension)) {
						ignoredExtensions.add(extension);

					}
				}

			}
		}
		return map;
	}

	private String getFileExtension(String path) {
		if (path.lastIndexOf(".") != -1 && path.lastIndexOf(".") != 0) {
			String d = path.substring(path.lastIndexOf(".") + 1);
			return d;
		} else {
			return "";
		}

	}

	private boolean isImageFile(String extension, Map<Integer, String> extensions) {
		boolean isImageFile = false;
		if (extensions.containsValue(extension) || extensions.containsValue(extension.toUpperCase())
				|| extensions.containsValue(extension.toLowerCase())) {
			isImageFile = true;
		}
		return isImageFile;
	}

	public int getNumberOfImagesFound() {
		return imageCount;
	}

	public List<String> getAllImagesList(final File folder) {
		if (imagePaths == null) {
			imagePaths = new ArrayList<String>();
		}
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				getAllImagesList(fileEntry);
			} else {
				imagePaths.add(fileEntry.getAbsolutePath());
			}
		}
		return imagePaths;
	}

	public BufferedImage readImageFromFile(String path) {
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public void saveImageToFile(BufferedImage img, String path) {
		File f = null;
		try {
			f = new File(path);
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getAllIgnoredExtensions() {
		return ignoredExtensions;

	}

	public void dispose() {
		img = null;
		map.clear();
		map = null;
		imagePaths.clear();
		imagePaths = null;
		imageCount = 0;
		ignoredExtensions.clear();
		ignoredExtensions = null;
	}

}
