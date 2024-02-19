package com.war.manager.map.layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.map.layer.entities.GeoLayerEntity;

public interface GeoLayerRepository extends JpaRepository<GeoLayerEntity, Long>{

}
