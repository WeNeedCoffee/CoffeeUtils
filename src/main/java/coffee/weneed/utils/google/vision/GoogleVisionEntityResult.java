package coffee.weneed.utils.google.vision;

import org.json.JSONObject;
import com.google.cloud.vision.v1p3beta1.WebDetection.WebEntity;
import coffee.weneed.utils.dataholders.IJSONObjectDataHolder;

// TODO: Auto-generated Javadoc
/**
 * The Class GoogleVisionEntityResult.
 */
public class GoogleVisionEntityResult implements IJSONObjectDataHolder {

	/** The description. */
	private String description;

	/** The id. */
	private String id;

	/** The score. */
	private float score;

	/**
	 * Instantiates a new google vision entity result.
	 *
	 * @param json the json
	 */
	public GoogleVisionEntityResult(final JSONObject json) {
		fromJSON(json);
	}

	/**
	 * Instantiates a new google vision entity result.
	 *
	 * @param description the description
	 * @param id          the id
	 * @param score       the score
	 */
	public GoogleVisionEntityResult(String description, String id, float score) {
		this.description = description;
		this.id = id;
		this.score = score;
	}

	/**
	 * Instantiates a new google vision entity result.
	 *
	 * @param entity the entity
	 */
	public GoogleVisionEntityResult(WebEntity entity) {
		fromWebEntity(entity);
	}

	/**
	 * From JSON.
	 *
	 * @param json the json
	 */
	@Override
	public void fromJSON(JSONObject json) {
		setDescription(json.optString("description", json.optString("entity", "")));
		setScore((float) json.getDouble("score"));
		setID(json.getString("id"));
	}

	/**
	 * From web entity.
	 *
	 * @param entity the entity
	 */
	public void fromWebEntity(WebEntity entity) {
		setDescription(entity.getDescription());
		setID(entity.getEntityId());
		setScore(entity.getScore());
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getID() {
		return id;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public float getScore() {
		return score;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * Sets the score.
	 *
	 * @param score the new score
	 */
	public void setScore(float score) {
		this.score = score;
	}

	/**
	 * To JSON.
	 *
	 * @return the JSON object
	 */
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("description", getDescription());
		json.put("id", getID());
		json.put("score", Float.toString(getScore()));
		return json;
	}
}
