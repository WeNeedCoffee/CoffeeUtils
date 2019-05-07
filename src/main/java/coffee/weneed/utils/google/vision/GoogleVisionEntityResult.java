package coffee.weneed.utils.google.vision;

import org.json.JSONObject;
import com.google.cloud.vision.v1p3beta1.WebDetection.WebEntity;
import coffee.weneed.utils.dataholders.IJSONObjectDataHolder;

public class GoogleVisionEntityResult implements IJSONObjectDataHolder {
	private String description;
	private String id;
	private float score;

	public GoogleVisionEntityResult(JSONObject json) {
		fromJSON(json);
	}

	public GoogleVisionEntityResult(String description, String id, float score) {
		setDescription(description);
		setID(id);
		setScore(score);
	}

	public GoogleVisionEntityResult(WebEntity entity) {
		fromWebEntity(entity);
	}

	@Override
	public void fromJSON(JSONObject json) {
		setDescription(json.optString("description", json.optString("entity", "")));
		setScore((float) json.getDouble("score"));
		setID(json.getString("id"));
	}

	public void fromWebEntity(WebEntity entity) {
		setDescription(entity.getDescription());
		setID(entity.getEntityId());
		setScore(entity.getScore());
	}

	public String getDescription() {
		return description;
	}

	public String getID() {
		return id;
	}

	public float getScore() {
		return score;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("description", getDescription());
		json.put("id", getID());
		json.put("score", getScore());
		return json;
	}
}
