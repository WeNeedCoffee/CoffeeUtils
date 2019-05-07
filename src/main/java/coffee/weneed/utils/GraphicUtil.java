package coffee.weneed.utils;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class GraphicUtil {

	/***
	 * https://stackoverflow.com/questions/12705385/how-to-convert-a-byte-to-a-bufferedimage-in-java
	 *
	 * @param imageData
	 * @return
	 */
	public static BufferedImage createImageFromBytes(byte[] imageData) {
		ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
		try {
			return ImageIO.read(bais);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String encodeToString(BufferedImage image, String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();
			imageString = java.util.Base64.getEncoder().encodeToString(imageBytes);

			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}

	/**
	 * https://stackoverflow.com/questions/10245220/java-image-resize-maintain-aspect-ratio
	 *
	 * @param imgSize
	 * @param boundary
	 * @return
	 */
	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

		int original_width = imgSize.width;
		int original_height = imgSize.height;
		int bound_width = boundary.width;
		int bound_height = boundary.height;
		int new_width = original_width;
		int new_height = original_height;

		if (original_width > bound_width) {
			new_width = bound_width;
			new_height = new_width * original_height / original_width;
		}

		if (new_height > bound_height) {
			new_height = bound_height;
			new_width = new_height * original_width / original_height;
		}

		return new Dimension(new_width, new_height);
	}

	public static byte[] imageToBytes(BufferedImage image) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", output);
		return output.toByteArray();
	}

	/**
	 * https://www.journaldev.com/615/java-resize-image
	 *
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage resizeImage(final Image image, int width, int height) {
		final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		return bufferedImage;
	}

	public static BufferedImage scaleDown(BufferedImage image, int width, int height) throws IOException {
		Dimension scaled = getScaledDimension(new Dimension(image.getWidth(), image.getHeight()), new Dimension(width, height));
		return resizeImage(image, (int) Math.round(scaled.getWidth()), (int) Math.round(scaled.getHeight()));
	}

	public static BufferedImage scaleDown(byte[] image, int width, int height) throws IOException {
		return scaleDown(createImageFromBytes(image), width, height);
	}

	public static BufferedImage scaleDown(File image, int width, int height) throws IOException {
		return scaleDown(ImageIO.read(image), width, height);
	}

	public static BufferedImage scaleDown(String image, int width, int height) throws IOException {
		return scaleDown(NetUtil.downloadUrl(image), width, height);
	}

	public static BufferedImage scaleDown(URL image, int width, int height) throws IOException {
		return scaleDown(NetUtil.downloadUrl(image), width, height);
	}

	/**
	 * https://javabeat.net/java-image-format/
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String getImageType(File file) throws IOException {
		File imageFile = file;
		ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile);
		Iterator<ImageReader> imageReadersList = ImageIO.getImageReaders(imageInputStream);
		if (!imageReadersList.hasNext()) {
			throw new RuntimeException("Image Readers Not Found!!!");
		}
		String reader = imageReadersList.next().getFormatName();
		imageInputStream.close();
		return reader;
	}
}
