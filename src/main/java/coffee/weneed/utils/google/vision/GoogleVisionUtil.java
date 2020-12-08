/*
package coffee.weneed.utils.google.vision;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.cloud.vision.v1p3beta1.AnnotateImageRequest;
import com.google.cloud.vision.v1p3beta1.AnnotateImageResponse;
import com.google.cloud.vision.v1p3beta1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1p3beta1.Feature;
import com.google.cloud.vision.v1p3beta1.Image;
import com.google.cloud.vision.v1p3beta1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import coffee.weneed.utils.NetUtil;

// TODO: Auto-generated Javadoc
*/
/**
 * The Class GoogleVisionUtil.
 *//*

public class GoogleVisionUtil {

	*/
/**
	 * Detect image.
	 *
	 * @author Daleth
	 * @param url the url
	 * @return the google vision result
	 * @throws IOException
	 * @throws GoogleVisionException
	 *//*

	public static GoogleVisionResult detectImage(URL url) throws IOException, GoogleVisionException {
		ArrayList<AnnotateImageRequest> requests = new ArrayList<>();
		Image img = Image.newBuilder().setContent(ByteString.copyFrom(NetUtil.downloadURL(url))).build();
		Feature feat = Feature.newBuilder().setType(Feature.Type.WEB_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);
		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();
			AnnotateImageResponse res = responses.get(0);
			if (!res.hasError()) {
				return new GoogleVisionResult(url.toString(), res.getWebDetection());
			}
			throw new GoogleVisionException(String.format("Error: %s\n", res.getError().getMessage()));
		}
	}
}
*/
