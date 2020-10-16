package com.practice.myroad.data.db

import com.practice.myroad.model.MyRoad
import com.practice.myroad.utils.EntityMapper

class MyRoadEntityMapperImpl: EntityMapper<MyRoadEntity, MyRoad> {
    override fun toDomainModel(entityMy: MyRoadEntity): MyRoad {
        return MyRoad(
            id = entityMy.id,
            displayName = entityMy.displayName,
            statusSeverity = entityMy.statusSeverity,
            statusSeverityDescription = entityMy.statusSeverityDescription
        )
    }

    override fun toEntity(domainModel: MyRoad): MyRoadEntity {
        return MyRoadEntity(
            id = domainModel.id,
            displayName = domainModel.displayName,
            statusSeverity = domainModel.statusSeverity,
            statusSeverityDescription = domainModel.statusSeverityDescription
        )
    }

    override fun listToDomainModel(entityListMy: List<MyRoadEntity>): List<MyRoad> {
        return entityListMy.map { roadEntity ->
            toDomainModel(roadEntity)
        }
    }
}