package com.practice.myroad.data.network.response

import com.practice.myroad.data.db.MyRoadEntity
import com.practice.myroad.utils.NetworkMapper

data class RoadResponse(var id:String,
                var displayName:String,
                var statusSeverity:String,
                var statusSeverityDescription: String) {
    var isUserError:Boolean = false
}


class RoadResponseMapperImpl: NetworkMapper<RoadResponse, MyRoadEntity> {
    override fun toEntity(roadResponse: RoadResponse): MyRoadEntity {
        return MyRoadEntity(id = roadResponse.id,
        displayName = roadResponse.displayName,
        statusSeverity = roadResponse.statusSeverity,
        statusSeverityDescription = roadResponse.statusSeverityDescription)
    }

    override fun toNetworkResponse(myRoadEntity: MyRoadEntity): RoadResponse {
        return RoadResponse(id = myRoadEntity.id,
        displayName = myRoadEntity.displayName,
        statusSeverity = myRoadEntity.statusSeverity,
        statusSeverityDescription = myRoadEntity.statusSeverityDescription)
    }

    override fun toEntityList(roadResponseList: List<RoadResponse>): List<MyRoadEntity> {
        return roadResponseList.map {roadEntity ->
            toEntity(roadEntity)
        }
    }
}