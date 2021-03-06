package common;

/**
 * 
 * The Interface of Cache system on the server side, in order to have KEY,
 * VALUES stores in memory for fast access
 */
public interface CacheInterface {

	/**
	 * Check if the key exists in the cache then gets it back
	 * 
	 * @param key
	 * @return the String related to key or null if not found
	 */
	public String get(String key);

	/**
	 * called when cache is full. Based on the caching strategy will remove one
	 * element
	 */
	public void pop();

	/**
	 * add a new <key,value> to the cache
	 * 
	 * @param key
	 * @param value
	 */
	public void push(String key, String value);

	/**
	 * sets the Strategy used for Caching
	 * 
	 * @param s
	 */
	public void setStrategy(CacheStrategy s);

	public void remove(String key);

}