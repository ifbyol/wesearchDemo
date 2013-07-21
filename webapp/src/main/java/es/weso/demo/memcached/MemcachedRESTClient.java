package es.weso.demo.memcached;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.log4j.Logger;

import es.weso.demo.exceptions.DemoException;

public class MemcachedRESTClient {

	protected static Logger log;
	private static MemcachedRESTClient MEMCACHED_REST_CLIENT;

	protected MemcachedClientBuilder builder;
	protected MemcachedClient memcachedClient;

	protected String memcachedUrls;
	protected int memcachedExpireTime;
	protected boolean memcachedEnabled;

	private MemcachedRESTClient(){
		MemcachedRESTClient.log = Logger.getLogger(MemcachedRESTClient.class);
		try {
			initialize();
		} catch (DemoException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() throws DemoException {
		this.memcachedUrls = "127.0.0.1:11211";
		this.memcachedExpireTime = 604800;
		this.memcachedEnabled = true;
		if(memcachedEnabled){
			try{
				this.builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(memcachedUrls));
				if(memcachedClient!=null && memcachedClient.isShutdown()){
					memcachedClient.shutdown();
				}
				this.memcachedClient = builder.build();
			}catch(IOException e){
				throw new DemoException("Fail Startging Memcached", e);
			}
		}
	}
	
	public static MemcachedRESTClient getInstance() {
		if(MEMCACHED_REST_CLIENT==null){
			MemcachedRESTClient.MEMCACHED_REST_CLIENT = new MemcachedRESTClient();
		}
		return MEMCACHED_REST_CLIENT;
	}
	
	public String execute(String url) {
		String result = null;
		if(memcachedEnabled){
			try {
				result = getResultFromCache(url);
			} catch (TimeoutException e) {
				result = null;
			} catch (InterruptedException e) {
				result = null;
			} catch (MemcachedException e) {
				result = null;
			}
		}
		return result;
	}
	
	protected String getResultFromCache(String url) throws TimeoutException,
		InterruptedException, MemcachedException {
		String key = "rest_" + url;
		String response = memcachedClient.get(key);
		return response;
	}
	
	public void storeValue(String key, String value) throws DemoException {
		log.info("Catching: rest_" + key);
		try {
			memcachedClient.set("rest_" + key, memcachedExpireTime, value);
		} catch (TimeoutException e) {
			throw new DemoException("Error storing", e);
		} catch (InterruptedException e) {
			throw new DemoException("Error storing", e);
		} catch (MemcachedException e) {
			throw new DemoException("Error storing", e);
		}
	}
	
}
