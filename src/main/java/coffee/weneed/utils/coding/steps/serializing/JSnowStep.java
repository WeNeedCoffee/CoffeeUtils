package coffee.weneed.utils.coding.steps.serializing;

import coffee.weneed.utils.coding.steps.ICodingStep;
import coffee.weneed.utils.jsnow.JSnowIO;

public class JSnowStep implements ICodingStep {

    /**
     * Decode.
     *
     * @param input the input
     * @return the byte[]
     */
    @Override
    public byte[] decode(byte[] input) {
        return JSnowIO.decode(new String(input));
    }

    /**
     * Encode.
     *
     * @param input the input
     * @return the byte[]
     */
    @Override
    public byte[] encode(byte[] input) {
        return JSnowIO.encode(input).getBytes();
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
    public int getID() {
        return 7;
    }

}

