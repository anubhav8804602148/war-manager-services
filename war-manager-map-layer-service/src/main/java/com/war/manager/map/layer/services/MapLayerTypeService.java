package com.war.manager.map.layer.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.war.manager.map.layer.entities.MapLayerTypeEntity;

@Service
public interface MapLayerTypeService {

	public List<MapLayerTypeEntity> getAllMapLayers();

	public List<MapLayerTypeEntity> getAllActiveMapLayers();
	
}
