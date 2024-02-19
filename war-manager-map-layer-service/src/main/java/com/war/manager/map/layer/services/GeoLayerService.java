package com.war.manager.map.layer.services;

import org.springframework.stereotype.Service;

import com.war.manager.map.layer.entities.GeoLayerEntity;

@Service
public interface GeoLayerService {

	public GeoLayerEntity saveNewGeoLayerEntity(GeoLayerEntity geoLayerEntity);
}
