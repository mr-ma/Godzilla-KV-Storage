package common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import logger.LogSetup;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import app_kvClient.KVClient;

public class Cache implements CacheInterface{
	private HashMap<String, String> cache;
	ArrayList<String> keys;
	private CacheStrategy strategy;
	private int size;
	private Logger logger = Logger.getRootLogger();
	Stack <String> qeueOfKeys;
	
	public Cache(int size, CacheStrategy strategy){
		this.size = size;
		this.strategy = strategy;
		cache = new HashMap<String,String>(size);
		if(strategy == CacheStrategy.LRU)
			keys = new ArrayList<String>(size);
		else
			qeueOfKeys = new Stack<String>();
	}
	
	/**
	 * Check if the key exists in the cache then gets it back
	 * @param key
	 * @return
	 */
	public String get(String key){
		String response = cache.get(key);
		
		/* key does exist in our cache */
		if(cache.containsKey(key)){
			
			/* when we have LRU strategy */
			if(this.strategy == CacheStrategy.LRU){
				int i = keys.indexOf(key);
				
				/* this condition should not happen ! was added just for safety */
				if(i == -1){
					reOrder(key);
					return response;
				}
				
				/* removes key from the queue of keys and puts in the first place */
				else{
					keys.remove(i);
					reOrder(key);
					return response;
				}
			}
		}
		return response;
						
	}
	
	/**
	 * called when cache is full. Based on the caching strategy will remove one element
	 */
	public void pop(){
		switch(this.strategy){
			case FIFO:{
				int i = 0;
				cache.remove(qeueOfKeys.get(0));
				qeueOfKeys.remove(0);
				break;
			}
			case LFU:{
				cache.remove(qeueOfKeys.pop());
				break;
			}
			case LRU:{
				cache.remove(keys.get(size-1));
				keys.remove(keys.get(size-1));
				break;
			}
		}
	}
	
	/**
	 * add a new <key,value> to the cache
	 * @param key
	 * @param value
	 */
	public void push(String key, String value){
		if(cache.containsKey(key)){
			logger.info(key+ " already exists in the cache");
			cache.put(key, value);
			if(strategy == CacheStrategy.LRU){
				reOrder(key);
				return;
			}
			qeueOfKeys.push(key);
			return ;
		}
		else{

			// if cache is full
			if(cache.size()==size){
				pop();
				cache.put(key, value);
				if(strategy == CacheStrategy.LRU){
					keys.add(key);
					reOrder(key);
					return ;
				}
				qeueOfKeys.push(key);
				return ;
			}
			else{
				cache.put(key, value);
				if(strategy == CacheStrategy.LRU){
					keys.add(key);
					reOrder(key);
					return;
				}
				qeueOfKeys.push(key);
				return ;
			}

		}

	}
	
	public void setStrategy(CacheStrategy s){
		this.strategy = s;
		Set<String> v = cache.keySet();
		logger.info("reinitializing cache strategy");
		switch(this.strategy){
		case LRU:{
			keys = new ArrayList<String>();
			for(String key:v){
				keys.add(key);
			}
			break;
		}
		default:{
			for(String key:v){
				qeueOfKeys.push(key);
			}
			break;
		}		
		}
	}
	
	/**
	 * Just for LRU strategy, reorders the keys based on LRU
	 * @param index of the recent hit
	 */
	private void reOrder(String key){
			if(keys.contains(key)){
				keys.remove(key);
			}
			keys.add(0, key);
		
	}
	
	public static void main(String args[])
	{
		try {
			new LogSetup("logs/server/cache.log", Level.ALL);
			
		} catch (IOException e) {
			System.out.println("Error! Unable to initialize logger!");
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void remove(String key) {
		cache.remove(key);
		
	}
	
}