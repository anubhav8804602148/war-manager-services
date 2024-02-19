package com.war.manager.map.layer.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.war.manager.map.layer.entities.GeoLayerEntity;
import com.war.manager.map.layer.repositories.GeoLayerRepository;
import com.war.manager.map.layer.services.GeoLayerService;

@Service
public class GeoLayerServiceImpl implements GeoLayerService{

	@Autowired
	GeoLayerRepository geoLayerRepository;
	
	@Override
	public GeoLayerEntity saveNewGeoLayerEntity(GeoLayerEntity geoLayerEntity) {
		if(geoLayerEntity==null) return null;
		return geoLayerRepository.save(geoLayerEntity);
	}

}
