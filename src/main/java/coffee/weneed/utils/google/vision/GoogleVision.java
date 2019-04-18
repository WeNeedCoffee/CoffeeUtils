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

public class GoogleVision {
	public static GoogleVisionResult detectImage(URL url) throws IOException {
		ArrayList<AnnotateImageRequest> requests = new ArrayList<>();
		Image img = Image.newBuilder().setContent(ByteString.copyFrom(NetUtil.downloadUrl(url))).build();
		Feature feat = Feature.newBuilder().setType(Feature.Type.WEB_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);
		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response;
			try {
				response = client.batchAnnotateImages(requests);
			} catch (Exception e) {
				return null;
			}
			List<AnnotateImageResponse> responses = response.getResponsesList();
			AnnotateImageResponse res = responses.get(0);
			if (!res.hasError()) {
				return new GoogleVisionResult(url.toString(), res.getWebDetection());
			}
			System.out.printf("Error: %s\n", res.getError().getMessage());
			return null;
		}
	}
}
