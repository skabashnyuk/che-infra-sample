/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.workspace.infrastructure.dummy;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.name.Names;
import org.eclipse.che.api.workspace.server.spi.RuntimeInfrastructure;
import org.eclipse.che.api.workspace.server.spi.environment.InternalEnvironmentFactory;
import org.eclipse.che.workspace.infrastructure.dummy.bootstrapper.MyBootstrapperFactory;
import org.eclipse.che.workspace.infrastructure.dummy.env.MyInternalEnvironment;
import org.eclipse.che.workspace.infrastructure.dummy.env.MyInternalEnvironmentFactory;

/** @author Sergii Leshchenko */
public class MyInfraModule extends AbstractModule {
  @Override
  protected void configure() {
    MapBinder<String, InternalEnvironmentFactory> envFactories =
        MapBinder.newMapBinder(binder(), String.class, InternalEnvironmentFactory.class);

    envFactories
        .addBinding(MyInternalEnvironment.RECIPE_TYPE)
        .to(MyInternalEnvironmentFactory.class);

    install(new FactoryModuleBuilder().build(MyRuntimeContextFactory.class));
    install(new FactoryModuleBuilder().build(MyRuntimeFactory.class));
    install(new FactoryModuleBuilder().build(MyBootstrapperFactory.class));

    bind(RuntimeInfrastructure.class).to(MyRuntimeInfrastructure.class);

    bind(String.class)
        .annotatedWith(Names.named("che.infra.dummy.output_endpoint"))
        .toProvider(OutputEndpointProvider.class);
  }
}
