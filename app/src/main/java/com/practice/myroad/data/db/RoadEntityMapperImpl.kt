package com.practice.myroad.data.db

import com.practice.myroad.model.Road

class RoadEntityMapperImpl: EntityMapper<RoadEntity, Road> {
    override fun toDomainModel(entity: RoadEntity): Road {
        return Road(
            id = entity.id,
            displayName = entity.displayName,
            statusSeverity = entity.statusSeverity,
            statusSeverityDescription = entity.statusSeverityDescription
        )
    }

    override fun toEntity(domainModel: Road): RoadEntity {
        return RoadEntity(
            id = domainModel.id,
            displayName = domainModel.displayName,
            statusSeverity = domainModel.statusSeverity,
            statusSeverityDescription = domainModel.statusSeverityDescription
        )
    }

    override fun listToDomainModel(entityList: List<RoadEntity>): List<Road> {
        return entityList.map { roadEntity ->
            toDomainModel(roadEntity)
        }
    }
}