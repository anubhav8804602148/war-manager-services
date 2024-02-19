package com.war.manager.map.layer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.map.layer.entities.MapLayerTypeEntity;

public interface MapLayerTypeRepostiory extends JpaRepository<MapLayerTypeEntity, Long>{

	List<MapLayerTypeEntity> findAllByMapLayerActive(boolean mapLayerActive);

}
