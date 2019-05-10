package coffee.weneed.utils.coding.steps;

import coffee.weneed.utils.coding.IDecodable;
import coffee.weneed.utils.coding.IEncodable;

/**
 * The Interface CryptStep.
 */
public interface ICodingStep extends IEncodable, IDecodable {

	int getID();
}
