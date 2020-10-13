package com.practice.myroad.data.db

interface EntityMapper<Entity, DomainModel> {

    fun toDomainModel(entity: Entity): DomainModel
    fun toEntity(domainModel: DomainModel): Entity
    fun listToDomainModel(entityList: List<Entity>): List<DomainModel>
}