package com.elijahbus.megaverse

import org.springframework.beans.factory.config.PropertiesFactoryBean
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory

class AppPropertiesSourceFactory : PropertySourceFactory {
    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> {
        return PropertiesPropertySource(
            resource.resource.filename!!,
            YamlPropertiesFactoryBean().also { it.setResources(resource.resource) }.getObject()!!
        )
    }
}
