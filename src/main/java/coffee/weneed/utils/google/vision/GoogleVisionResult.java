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
/**
 * The Class GoogleVisionResult.
 *
 * @author Daleth
 */
public class GoogleVisionResult implements IJSONObjectDataHolder {
	
	/** The descriptions. */
	private List<GoogleVisionEntityResult> descriptions = new ArrayList<>();
	
	/** The labels. */
	private List<String> labels = new ArrayList<>();
	
	/** The matching pages. */
	private List<CoffeeEntry<String, String>> matching_pages = new ArrayList<>();
	
	/** The partial image matches. */
	private List<String> partial_image_matches = new ArrayList<>();
	
	/** The full image matches. */
	private List<String> full_image_matches = new ArrayList<>();
	
	/** The similar images. */
	private List<String> similar_images = new ArrayList<>();
	
	/** The image. */
	private String image;
	
	/** The timestamp. */
	private long timestamp;

	/**
	 * Instantiates a new google vision result.
	 *
	 * @param json the json
	 */
	public GoogleVisionResult(JSONObject json) {
		fromJSON(json);
	}

	/**
	 * Instantiates a new google vision result.
	 *
	 * @param image the image
	 * @param web the web
	 */
	public GoogleVisionResult(String image, WebDetection web) {
		this.image = image;
		fromWebDetection(web);
		timestamp = System.currentTimeMillis();
	}

	/**
	 * From JSON.
	 *
	 * @param json the json
	 */
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
			matching_pages.add(new CoffeeEntry<>(obj.getString("url"), obj.getString("title")));
		}

		for (Object o : json.optJSONArray("partial_image_matches")) {
			if (o instanceof JSONObject) {
				partial_image_matches.add(((JSONObject) o).getString("url"));
			} else if (o instanceof String) {
				partial_image_matches.add((String) o);
			}
		}

		for (Object o : json.optJSONArray("full_image_matches")) {
			if (o instanceof JSONObject) {
				full_image_matches.add(((JSONObject) o).getString("url"));
			} else if (o instanceof String) {
				full_image_matches.add((String) o);
			}
		}

		for (Object o : json.optJSONArray("similar_images") != null ? json.optJSONArray("similar_images") : json.optJSONArray("similar_image")) {
			if (o instanceof JSONObject) {
				similar_images.add(((JSONObject) o).getString("url"));
			} else if (o instanceof String) {
				similar_images.add((String) o);
			}
		}
	}

	/**
	 * From web detection.
	 *
	 * @param annotation the annotation
	 */
	private void fromWebDetection(WebDetection annotation) {
		for (WebEntity entity : annotation.getWebEntitiesList()) {
			descriptions.add(new GoogleVisionEntityResult(entity));
		}
		for (WebLabel label : annotation.getBestGuessLabelsList()) {
			labels.add(label.getLabel());
		}
		for (WebPage page : annotation.getPagesWithMatchingImagesList()) {
			matching_pages.add(new CoffeeEntry<>(page.getUrl(), page.getPageTitle()));
		}
		for (WebImage image : annotation.getPartialMatchingImagesList()) {
			partial_image_matches.add(image.getUrl());
		}
		for (WebImage image : annotation.getFullMatchingImagesList()) {
			full_image_matches.add(image.getUrl());
		}
		for (WebImage image : annotation.getVisuallySimilarImagesList()) {
			similar_images.add(image.getUrl());
		}
	}

	/**
	 * Gets the descriptions.
	 *
	 * @return the descriptions
	 */
	public List<GoogleVisionEntityResult> getDescriptions() {
		return descriptions;
	}

	/**
	 * Gets the full image matches.
	 *
	 * @return the full image matches
	 */
	public List<String> getFull_image_matches() {
		return full_image_matches;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public List<String> getLabels() {
		return labels;
	}

	/**
	 * Gets the matching pages.
	 *
	 * @return the matching pages
	 */
	public List<CoffeeEntry<String, String>> getMatching_pages() {
		return matching_pages;
	}

	/**
	 * Gets the partial image matches.
	 *
	 * @return the partial image matches
	 */
	public List<String> getPartial_image_matches() {
		return partial_image_matches;
	}

	/**
	 * Gets the similar images.
	 *
	 * @return the similar images
	 */
	public List<String> getSimilar_images() {
		return similar_images;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the descriptions.
	 *
	 * @param descriptions the new descriptions
	 */
	public void setDescriptions(List<GoogleVisionEntityResult> descriptions) {
		this.descriptions = descriptions;
	}

	/**
	 * Sets the full image matches.
	 *
	 * @param full_image_matches the new full image matches
	 */
	public void setFull_image_matches(List<String> full_image_matches) {
		this.full_image_matches = full_image_matches;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Sets the labels.
	 *
	 * @param labels the new labels
	 */
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	/**
	 * Sets the matching pages.
	 *
	 * @param matching_pages the matching pages
	 */
	public void setMatching_pages(List<CoffeeEntry<String, String>> matching_pages) {
		this.matching_pages = matching_pages;
	}

	/**
	 * Sets the partial image matches.
	 *
	 * @param partial_image_matches the new partial image matches
	 */
	public void setPartial_image_matches(List<String> partial_image_matches) {
		this.partial_image_matches = partial_image_matches;
	}

	/**
	 * Sets the similar images.
	 *
	 * @param similar_images the new similar images
	 */
	public void setSimilar_images(List<String> similar_images) {
		this.similar_images = similar_images;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * To JSON.
	 *
	 * @return the JSON object
	 */
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
		for (CoffeeEntry<String, String> s : matching_pages) {
			array.put(new JSONObject().put("url", s.getKey()).put("title", s.getValue()));
		}
		json.put("matching_pages", array);
		array = new JSONArray();
		for (String s : partial_image_matches) {
			array.put(s);
		}
		json.put("partial_image_matches", array);
		array = new JSONArray();
		for (String s : full_image_matches) {
			array.put(s);
		}
		json.put("full_image_matches", array);
		array = new JSONArray();
		for (String s : similar_images) {
			array.put(s);
		}
		json.put("similar_images", array);

		return json;
	}

}
