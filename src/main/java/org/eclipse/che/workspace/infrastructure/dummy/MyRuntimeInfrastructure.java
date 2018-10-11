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

import static java.lang.String.format;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.api.core.ValidationException;
import org.eclipse.che.api.core.model.workspace.runtime.RuntimeIdentity;
import org.eclipse.che.api.core.notification.EventService;
import org.eclipse.che.api.workspace.server.spi.InfrastructureException;
import org.eclipse.che.api.workspace.server.spi.InternalInfrastructureException;
import org.eclipse.che.api.workspace.server.spi.RuntimeInfrastructure;
import org.eclipse.che.api.workspace.server.spi.environment.InternalEnvironment;
import org.eclipse.che.api.workspace.server.spi.provision.InternalEnvironmentProvisioner;
import org.eclipse.che.workspace.infrastructure.dummy.env.MyInternalEnvironment;

/** @author Sergii Leshchenko */
@Singleton
public class MyRuntimeInfrastructure extends RuntimeInfrastructure {

  public static final String NAME = "myInfra";
  public static final Set<String> SUPPORTED_RECIPES = ImmutableSet.of("myRecipe");

  private final MyRuntimeContextFactory contextFactory;

  @Inject
  public MyRuntimeInfrastructure(
      EventService eventService,
      Set<InternalEnvironmentProvisioner> envProvisioners,
      MyRuntimeContextFactory contextFactory) {
    super(NAME, SUPPORTED_RECIPES, eventService, envProvisioners);
    this.contextFactory = contextFactory;
  }

  @Override
  protected MyRuntimeContext internalPrepare(RuntimeIdentity id, InternalEnvironment source)
      throws ValidationException, InfrastructureException {
    MyInternalEnvironment environment;
    if (source instanceof MyInternalEnvironment) {
      environment = (MyInternalEnvironment) source;
    } else {
      throw new InternalInfrastructureException(
          format(
              "Environment type '%s' is not supported. Supported environment types: %s",
              source.getRecipe().getType(), MyInternalEnvironment.RECIPE_TYPE));
    }

    return contextFactory.create(environment, id, this);
  }
}
