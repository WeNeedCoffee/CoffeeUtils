package coffee.weneed.utils.data.input;

/**
 * 
 * @author Dalethium
 *
 */
public abstract interface ISeekableLittleEndianAccessor extends ILittleEndianAccessor {

	public abstract long getPosition();

	public abstract void seek(long paramLong);
}