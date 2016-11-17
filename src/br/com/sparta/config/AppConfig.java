package br.com.sparta.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
@ApplicationPath("/rest")
public class AppConfig extends Application{

	
	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>>resources = new HashSet<>();
		resources.add(MultiPartFeature.class);
		return resources;
	}
	
	@Override
	public Map<String, Object> getProperties() {
		Map<String,Object>map= new HashMap<>();
		map.put("jersey.config.server.provider.packages", "br.com.improving.backend");
		return map;
	}
	
}
