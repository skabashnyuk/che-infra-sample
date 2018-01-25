/*
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.workspace.infrastructure.dummy.env;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.che.api.core.ValidationException;
import org.eclipse.che.api.core.model.workspace.Warning;
import org.eclipse.che.api.installer.server.InstallerRegistry;
import org.eclipse.che.api.workspace.server.spi.InfrastructureException;
import org.eclipse.che.api.workspace.server.spi.environment.InternalEnvironmentFactory;
import org.eclipse.che.api.workspace.server.spi.environment.InternalMachineConfig;
import org.eclipse.che.api.workspace.server.spi.environment.InternalRecipe;
import org.eclipse.che.api.workspace.server.spi.environment.MachineConfigsValidator;
import org.eclipse.che.api.workspace.server.spi.environment.RecipeRetriever;

/** @author Sergii Leshchenko */
public class MyInternalEnvironmentFactory
    extends InternalEnvironmentFactory<MyInternalEnvironment> {

  private final String defaultMachineMemorySizeAttribute;

  @Inject
  public MyInternalEnvironmentFactory(
      InstallerRegistry installerRegistry,
      RecipeRetriever recipeRetriever,
      MachineConfigsValidator machinesValidator,
      @Named("che.workspace.default_memory_mb") long defaultMachineMemorySizeMB) {
    super(installerRegistry, recipeRetriever, machinesValidator);
    this.defaultMachineMemorySizeAttribute =
        String.valueOf(defaultMachineMemorySizeMB * 1024 * 1024);
  }

  @Override
  protected MyInternalEnvironment doCreate(
      InternalRecipe recipe, Map<String, InternalMachineConfig> machines, List<Warning> warnings)
      throws InfrastructureException, ValidationException {

    String recipeContext = recipe.getContent();
    // recipe should be parsed here

    // Check that all the machines from recipe are in the list of InternalMachineConfig.
    // If not, add missing machines to normalize state of internal environment configuration.

    // If RAM limit is respected by environment
    //     If RAM limit is not set in InternalMachineConfig and is set in recipe
    //       put limit from recipe to InternalMachineConfig attribute
    //       MachineConfig#MEMORY_LIMIT_ATTRIBUTE
    //     Otherwise put attribute with limit from defaultMachineMemorySizeAttribute

    return new MyInternalEnvironment(/* env specific fields here,*/ recipe, machines, warnings);
  }
}
