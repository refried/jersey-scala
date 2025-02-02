package com.codahale.jersey.inject

import javax.ws.rs.ext.Provider
import com.sun.jersey.api.model.Parameter
import com.sun.jersey.core.spi.component.{ComponentScope, ComponentContext}
import com.sun.jersey.spi.inject.{Injectable, InjectableProvider}
import com.sun.jersey.server.impl.model.parameter.multivalued.MultivaluedParameterExtractor
import javax.ws.rs.{FormParam, QueryParam}

@Provider
class ScalaCollectionsQueryParamInjectableProvider
  extends AbstractScalaCollectionsParamInjectableProvider
  with InjectableProvider[QueryParam, Parameter]
{
  def getInjectable(ic: ComponentContext, a: QueryParam, c: Parameter): Injectable[_] = {
    val parameterName = c.getSourceName()
    if (parameterName != null && !parameterName.isEmpty) {
      buildInjectable(parameterName, c.getDefaultValue, !c.isEncoded, c.getParameterClass)
    } else null
  }

  private def buildInjectable(name: String, default: String, decoded: Boolean, klass: Class[_]): Injectable[_ <: Object] = {
    val extractor = buildExtractor(name, default, klass)
    if (extractor != null) {
      new ScalaCollectionQueryParamInjectable(extractor, decoded)
    } else null
  }
}



