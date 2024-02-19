package com.war.manager.map.layer.models;

import java.sql.Timestamp;

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
public class GeoLayer {

	private long geoLayerId;
	private String geoLayerName;
	private String geoJsonString;
	private boolean mapLayerActive;
	private String createdBy;
	private Timestamp createdAt;

}
