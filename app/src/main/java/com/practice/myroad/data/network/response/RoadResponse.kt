package com.practice.myroad.data.network.response

import com.practice.myroad.data.db.RoadEntity
import com.practice.myroad.utils.NetworkMapper

data class RoadResponse(var id:String,
                var displayName:String,
                var statusSeverity:String,
                var statusSeverityDescription: String)


class RoadResponseMapperImpl: NetworkMapper<RoadResponse, RoadEntity> {
    override fun toEntity(roadResponse: RoadResponse): RoadEntity {
        return RoadEntity(id = roadResponse.id,
        displayName = roadResponse.displayName,
        statusSeverity = roadResponse.statusSeverity,
        statusSeverityDescription = roadResponse.statusSeverityDescription)
    }

    override fun toNetworkResponse(roadEntity: RoadEntity): RoadResponse {
        return RoadResponse(id = roadEntity.id,
        displayName = roadEntity.displayName,
        statusSeverity = roadEntity.statusSeverity,
        statusSeverityDescription = roadEntity.statusSeverityDescription)
    }

    override fun listToEntity(roadResponseList: List<RoadResponse>): List<RoadEntity> {
        return roadResponseList.map {roadEntity ->
            toEntity(roadEntity)
        }
    }
}