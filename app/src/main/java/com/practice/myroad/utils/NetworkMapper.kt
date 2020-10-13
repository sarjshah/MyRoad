package com.practice.myroad.utils

interface NetworkMapper<NetworkResponse, Entity> {

    fun toEntity(networkResponse: NetworkResponse): Entity
    fun toNetworkResponse(entity: Entity): NetworkResponse
    fun listToEntity(networkResponseList: List<NetworkResponse>): List<Entity>
}