package com.war.manager.map.layer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.war.manager.map.layer.entities.GeoLayerEntity;
import com.war.manager.map.layer.entities.MapLayerTypeEntity;
import com.war.manager.map.layer.models.GeoLayer;
import com.war.manager.map.layer.services.GeoLayerService;
import com.war.manager.map.layer.services.MapLayerTypeService;

@RestController
@RequestMapping("/mapLayerType")
public class MapLayerTypeController {

	@Autowired
	MapLayerTypeService mapLayerTypeService;

	@Autowired
	GeoLayerService geoLayerService;

	@GetMapping("/getAllActiveLayers")
	public ResponseEntity<List<MapLayerTypeEntity>> getAllActiveLayers() {
		return ResponseEntity.ok(mapLayerTypeService.getAllActiveMapLayers());
	}

	@PostMapping("/saveMapLayer")
	public ResponseEntity<GeoLayerEntity> saveMapLayer(@RequestBody GeoLayer geoLayer) {
		return ResponseEntity.ofNullable(geoLayerService.saveNewGeoLayerEntity(new GeoLayerEntity(geoLayer)));
	}
}
