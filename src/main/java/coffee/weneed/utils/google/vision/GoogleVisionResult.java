/*
package coffee.weneed.utils.google.vision;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.cloud.vision.v1p3beta1.WebDetection;
import com.google.cloud.vision.v1p3beta1.WebDetection.WebEntity;
import com.google.cloud.vision.v1p3beta1.WebDetection.WebImage;
import com.google.cloud.vision.v1p3beta1.WebDetection.WebLabel;
import com.google.cloud.vision.v1p3beta1.WebDetection.WebPage;
import coffee.weneed.utils.dataholders.IJSONObjectDataHolder;
import coffee.weneed.utils.datatypes.CoffeeEntry;

// TODO: Auto-generated Javadoc
*/
/**
 * The Class GoogleVisionResult.
 *
 * @author Daleth
 *//*

public class GoogleVisionResult implements IJSONObjectDataHolder {

	*/
/** The descriptions. *//*

	private List<GoogleVisionEntityResult> descriptions = new ArrayList<>();

	*/
/** The labels. *//*

	private List<String> labels = new ArrayList<>();

	*/
/** The matching pages. *//*

	private List<CoffeeEntry<String, String>> matchingPages = new ArrayList<>();

	*/
/** The partial image matches. *//*

	private List<String> partialImageMatches = new ArrayList<>();

	*/
/** The full image matches. *//*

	private List<String> fullImageMatches = new ArrayList<>();

	*/
/** The similar images. *//*

	private List<String> similarImages = new ArrayList<>();

	*/
/** The image. *//*

	private String image;

	*/
/** The timestamp. *//*

	private long timestamp;

	*/
/**
	 * Instantiates a new google vision result.
	 *
	 * @param json the json
	 *//*

	public GoogleVisionResult(JSONObject json) {
		fromJSON(json);
	}

	*/
/**
	 * Instantiates a new google vision result.
	 *
	 * @param image the image
	 * @param web   the web
	 *//*

	public GoogleVisionResult(String image, WebDetection web) {
		this.image = image;
		fromWebDetection(web);
		timestamp = System.currentTimeMillis();
	}

	*/
/**
	 * From JSON.
	 *
	 * @param json the json
	 *//*

	@Override
	public void fromJSON(JSONObject json) {
		image = json.getString("image");
		timestamp = json.optLong("timestamp", json.optLong("ts", 0L));
		for (Object o : json.optJSONArray("descriptions") != null ? json.optJSONArray("descriptions") : json.optJSONArray("entities")) {
			if (!(o instanceof JSONObject)) {
				continue;
			}
			descriptions.add(new GoogleVisionEntityResult((JSONObject) o));
		}

		for (Object o : json.optJSONArray("labels") != null ? json.optJSONArray("labels") : json.optJSONArray("label")) {
			if (!(o instanceof String)) {
				continue;
			}
			labels.add((String) o);
		}

		for (Object o : json.optJSONArray("matching_pages") != null ? json.optJSONArray("matching_pages") : json.optJSONArray("matching_pages")) {
			if (!(o instanceof JSONObject)) {
				continue;
			}
			JSONObject obj = (JSONObject) o;
			matchingPages.add(new CoffeeEntry<>(obj.getString("url"), obj.getString("title")));
		}

		for (Object o : json.optJSONArray("partial_image_matches")) {
			if (o instanceof JSONObject) {
				partialImageMatches.add(((JSONObject) o).getString("url"));
			} else if (o instanceof String) {
				partialImageMatches.add((String) o);
			}
		}

		for (Object o : json.optJSONArray("full_image_matches")) {
			if (o instanceof JSONObject) {
				fullImageMatches.add(((JSONObject) o).getString("url"));
			} else if (o instanceof String) {
				fullImageMatches.add((String) o);
			}
		}

		for (Object o : json.optJSONArray("similar_images") != null ? json.optJSONArray("similar_images") : json.optJSONArray("similar_image")) {
			if (o instanceof JSONObject) {
				similarImages.add(((JSONObject) o).getString("url"));
			} else if (o instanceof String) {
				similarImages.add((String) o);
			}
		}
	}

	*/
/**
	 * From web detection.
	 *
	 * @param annotation the annotation
	 *//*

	private void fromWebDetection(WebDetection annotation) {
		for (WebEntity entity : annotation.getWebEntitiesList()) {
			descriptions.add(new GoogleVisionEntityResult(entity));
		}
		for (WebLabel label : annotation.getBestGuessLabelsList()) {
			labels.add(label.getLabel());
		}
		for (WebPage page : annotation.getPagesWithMatchingImagesList()) {
			matchingPages.add(new CoffeeEntry<>(page.getUrl(), page.getPageTitle()));
		}
		for (WebImage image : annotation.getPartialMatchingImagesList()) {
			partialImageMatches.add(image.getUrl());
		}
		for (WebImage image : annotation.getFullMatchingImagesList()) {
			fullImageMatches.add(image.getUrl());
		}
		for (WebImage image : annotation.getVisuallySimilarImagesList()) {
			similarImages.add(image.getUrl());
		}
	}

	*/
/**
	 * Gets the descriptions.
	 *
	 * @return the descriptions
	 *//*

	public List<GoogleVisionEntityResult> getDescriptions() {
		return descriptions;
	}

	*/
/**
	 * Gets the full image matches.
	 *
	 * @return the full image matches
	 *//*

	public List<String> getFullImageMatches() {
		return fullImageMatches;
	}

	*/
/**
	 * Gets the image.
	 *
	 * @return the image
	 *//*

	public String getImage() {
		return image;
	}

	*/
/**
	 * Gets the labels.
	 *
	 * @return the labels
	 *//*

	public List<String> getLabels() {
		return labels;
	}

	*/
/**
	 * Gets the matching pages.
	 *
	 * @return the matching pages
	 *//*

	public List<CoffeeEntry<String, String>> getMatchingPages() {
		return matchingPages;
	}

	*/
/**
	 * Gets the partial image matches.
	 *
	 * @return the partial image matches
	 *//*

	public List<String> getPartialImageMatches() {
		return partialImageMatches;
	}

	*/
/**
	 * Gets the similar images.
	 *
	 * @return the similar images
	 *//*

	public List<String> getSimilarImages() {
		return similarImages;
	}

	*/
/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 *//*

	public long getTimestamp() {
		return timestamp;
	}

	*/
/**
	 * Sets the descriptions.
	 *
	 * @param descriptions the new descriptions
	 *//*

	public void setDescriptions(List<GoogleVisionEntityResult> descriptions) {
		this.descriptions = descriptions;
	}

	*/
/**
	 * Sets the full image matches.
	 *
	 * @param full_image_matches the new full image matches
	 *//*

	public void setFullImageMatches(List<String> full_image_matches) {
		fullImageMatches = full_image_matches;
	}

	*/
/**
	 * Sets the image.
	 *
	 * @param image the new image
	 *//*

	public void setImage(String image) {
		this.image = image;
	}

	*/
/**
	 * Sets the labels.
	 *
	 * @param labels the new labels
	 *//*

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	*/
/**
	 * Sets the matching pages.
	 *
	 * @param matching_pages the matching pages
	 *//*

	public void setMatchingPages(List<CoffeeEntry<String, String>> matching_pages) {
		matchingPages = matching_pages;
	}

	*/
/**
	 * Sets the partial image matches.
	 *
	 * @param partial_image_matches the new partial image matches
	 *//*

	public void setPartialImageMatches(List<String> partial_image_matches) {
		partialImageMatches = partial_image_matches;
	}

	*/
/**
	 * Sets the similar images.
	 *
	 * @param similar_images the new similar images
	 *//*

	public void setSimilarImages(List<String> similar_images) {
		similarImages = similar_images;
	}

	*/
/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 *//*

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	*/
/**
	 * To JSON.
	 *
	 * @return the JSON object
	 *//*

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		json.put("image", image);
		json.put("timestamp", timestamp);
		for (GoogleVisionEntityResult res : getDescriptions()) {
			array.put(res.toJSON());
		}
		json.put("descriptions", array);
		array = new JSONArray();
		for (String s : labels) {
			array.put(s);
		}
		json.put("labels", array);
		array = new JSONArray();
		for (CoffeeEntry<String, String> s : matchingPages) {
			array.put(new JSONObject().put("url", s.getKey()).put("title", s.getValue()));
		}
		json.put("matching_pages", array);
		array = new JSONArray();
		for (String s : partialImageMatches) {
			array.put(s);
		}
		json.put("partial_image_matches", array);
		array = new JSONArray();
		for (String s : fullImageMatches) {
			array.put(s);
		}
		json.put("full_image_matches", array);
		array = new JSONArray();
		for (String s : similarImages) {
			array.put(s);
		}
		json.put("similar_images", array);

		return json;
	}

}
*/
