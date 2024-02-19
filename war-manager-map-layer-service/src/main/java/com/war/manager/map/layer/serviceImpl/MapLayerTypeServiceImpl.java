package com.war.manager.map.layer.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.war.manager.map.layer.entities.MapLayerTypeEntity;
import com.war.manager.map.layer.repositories.MapLayerTypeRepostiory;
import com.war.manager.map.layer.services.MapLayerTypeService;

@Service
public class MapLayerTypeServiceImpl implements MapLayerTypeService {

	@Autowired
	MapLayerTypeRepostiory mapLayerTypeRepostiory;
	
	@Override
	public List<MapLayerTypeEntity> getAllMapLayers() {
		return mapLayerTypeRepostiory.findAll();
	}

	@Override
	public List<MapLayerTypeEntity> getAllActiveMapLayers() {
		return mapLayerTypeRepostiory.findAllByMapLayerActive(true);
	}

}
