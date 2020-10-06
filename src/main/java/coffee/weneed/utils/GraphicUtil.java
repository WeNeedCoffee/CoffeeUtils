package coffee.weneed.utils;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class GraphicUtil.
 */
public class GraphicUtil {

	/**
	 * @author flavio https://www.programcreek.com/java-api-examples/?code=flschiavoni/shareMySheet/shareMySheet-master/src/br/edu/ufsj/sms/control/ImageTransformation.java
	 * @param image
	 * @param scale
	 * @return
	 * @throws IOException
	 */
	public static byte[] compress(BufferedImage image, float scale) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = writers.next();
		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(scale);
		ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
		writer.setOutput(ios);
		writer.write(null, new IIOImage(image, null, null), param);
		byte[] data = baos.toByteArray();
		writer.dispose();
		return data;
	}

	/**
	 * *
	 * https://stackoverflow.com/questions/12705385/how-to-convert-a-byte-to-a-bufferedimage-in-java
	 *
	 * @param imageData the image data
	 * @return the buffered image
	 */
	public static BufferedImage createImageFromBytes(byte[] imageData) {
		ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
		try {
			return ImageIO.read(bais);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Encode to string.
	 *
	 * @param image the image
	 * @param type  the type
	 * @return the string
	 */
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
	 * https://javabeat.net/java-image-format/
	 *
	 * @param file the file
	 * @return the image type
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String getImageType(File file) throws IOException {
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

	/**
	 * https://stackoverflow.com/questions/10245220/java-image-resize-maintain-aspect-ratio
	 *
	 * @param imgSize  the img size
	 * @param boundary the boundary
	 * @return the scaled dimension
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

	/**
	 * Image to bytes.
	 *
	 * @param image the image
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] imageToBytes(BufferedImage image) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", output);
		return output.toByteArray();
	}

	/**
	 * @author flavio https://www.programcreek.com/java-api-examples/?code=flschiavoni/shareMySheet/shareMySheet-master/src/br/edu/ufsj/sms/control/ImageTransformation.java
	 * @param img
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage resize(BufferedImage img, int maxWidth, int maxHeight) throws IOException {
		int scaledWidth = 0;
		int scaledHeight = 0;

		scaledWidth = maxWidth;
		scaledHeight = (int) (img.getHeight() * ((double) scaledWidth / img.getWidth()));

		if (scaledHeight > maxHeight) {
			scaledHeight = maxHeight;
			scaledWidth = (int) (img.getWidth() * ((double) scaledHeight / img.getHeight()));

			if (scaledWidth > maxWidth) {
				scaledWidth = maxWidth;
				scaledHeight = maxHeight;
			}
		}

		Image resized = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
		BufferedImage buffered = new BufferedImage(scaledWidth, scaledHeight, Image.SCALE_REPLICATE);
		buffered.getGraphics().drawImage(resized, 0, 0, null);

		return buffered;
	}

	/**
	 * https://www.journaldev.com/615/java-resize-image
	 *
	 * @param image  the image
	 * @param width  the width
	 * @param height the height
	 * @return the buffered image
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

	/**
	 * Scale down.
	 *
	 * @param image  the image
	 * @param width  the width
	 * @param height the height
	 * @return the buffered image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static BufferedImage scaleDown(BufferedImage image, int width, int height) throws IOException {
		Dimension scaled = getScaledDimension(new Dimension(image.getWidth(), image.getHeight()), new Dimension(width, height));
		return resizeImage(image, (int) Math.round(scaled.getWidth()), (int) Math.round(scaled.getHeight()));
	}

	/**
	 * Scale down.
	 *
	 * @param image  the image
	 * @param width  the width
	 * @param height the height
	 * @return the buffered image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static BufferedImage scaleDown(byte[] image, int width, int height) throws IOException {
		return scaleDown(createImageFromBytes(image), width, height);
	}

	/**
	 * Scale down.
	 *
	 * @param image  the image
	 * @param width  the width
	 * @param height the height
	 * @return the buffered image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static BufferedImage scaleDown(File image, int width, int height) throws IOException {
		return scaleDown(ImageIO.read(image), width, height);
	}

	/**
	 * Scale down.
	 *
	 * @param image  the image
	 * @param width  the width
	 * @param height the height
	 * @return the buffered image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static BufferedImage scaleDown(String image, int width, int height) throws IOException {
		return scaleDown(NetUtil.downloadURL(image), width, height);
	}

	/**
	 * Scale down.
	 *
	 * @param image  the image
	 * @param width  the width
	 * @param height the height
	 * @return the buffered image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static BufferedImage scaleDown(URL image, int width, int height) throws IOException {
		return scaleDown(NetUtil.downloadURL(image), width, height);
	}

	/**
	 * @author flavio https://www.programcreek.com/java-api-examples/?code=flschiavoni/shareMySheet/shareMySheet-master/src/br/edu/ufsj/sms/control/ImageTransformation.java
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		byte[] data = baos.toByteArray();
		return data;
	}

	/**
	 * @author StackOverflow:LanguagesNamedAfterCofee https://stackoverflow.com/questions/12879540/image-resizing-in-java-to-reduce-image-size
	 * @param img
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 */
	public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

		int type = img.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = img;
		BufferedImage scratchImage = null;
		Graphics2D g2 = null;

		int w = img.getWidth();
		int h = img.getHeight();

		int prevW = w;
		int prevH = h;

		do {
			if (w > targetWidth) {
				w /= 2;
				w = w < targetWidth ? targetWidth : w;
			}

			if (h > targetHeight) {
				h /= 2;
				h = h < targetHeight ? targetHeight : h;
			}

			if (scratchImage == null) {
				scratchImage = new BufferedImage(w, h, type);
				g2 = scratchImage.createGraphics();
			}

			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

			prevW = w;
			prevH = h;
			ret = scratchImage;
		} while (w != targetWidth || h != targetHeight);

		if (g2 != null) {
			g2.dispose();
		}

		if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
			scratchImage = new BufferedImage(targetWidth, targetHeight, type);
			g2 = scratchImage.createGraphics();
			g2.drawImage(ret, 0, 0, null);
			g2.dispose();
			ret = scratchImage;
		}

		return ret;

	}
}
