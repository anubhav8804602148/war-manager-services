package com.war.manager.map.layer.entities;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.war.manager.map.layer.models.GeoLayer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table
@Entity
public class GeoLayerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long geoLayerId;
	private String geoLayerName;
	private String geoJsonString;
	private boolean mapLayerActive;
	private String createdBy;
	private Timestamp createdAt;

	@Transient
	private static Gson gson = new Gson();
	
	public GeoLayerEntity(GeoLayer geoLayer) {
		geoLayerId = geoLayer.getGeoLayerId();
		geoLayerName = geoLayer.getGeoLayerName();
		geoJsonString = geoLayer.getGeoJsonString();
		mapLayerActive = geoLayer.isMapLayerActive();
		createdBy =  geoLayer.getCreatedBy();
		createdAt = geoLayer.getCreatedAt();
	}

}
