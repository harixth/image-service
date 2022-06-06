package com.example.imageservice.image.services

import com.example.imageservice.configuration.PredefinedTypeConfig
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import com.example.imageservice.image.entities.PredefinedType
import com.example.imageservice.image.entities.PredefinedTypeName
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class PredefinedTypeService(private val predefinedTypeConfig: PredefinedTypeConfig) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun findPredefinedType(predefinedTypeName: String): PredefinedType {
        val ptn = PredefinedTypeName.valueOf(
        predefinedTypeName
            .replace("-", "").uppercase()
        )
        logger.info("Found Predefined Type Name for {}", ptn)
        return when (ptn) {
            PredefinedTypeName.THUMBNAIL -> predefinedTypeConfig.THUMBNAIL();
            PredefinedTypeName.DETAILLARGE -> predefinedTypeConfig.DETAILLARGE();
            else -> {
                logger.info("There is no predefined type $predefinedTypeName")
                throw NotFoundException();
            }
        }
    }
}