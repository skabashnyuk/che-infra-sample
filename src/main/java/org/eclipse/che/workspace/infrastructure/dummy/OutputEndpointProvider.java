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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

/** @author Sergii Leshchenko */
public class OutputEndpointProvider implements Provider<String> {

  private final String cheWebsocketEndpoint;

  @Inject
  public OutputEndpointProvider(@Named("che.websocket.endpoint") String cheWebsocketEndpoint) {
    this.cheWebsocketEndpoint = cheWebsocketEndpoint;
  }

  @Override
  public String get() {
    // default che WebSocket endpoint can be returned here
    // or custom established endpoint to avoid master overload
    return cheWebsocketEndpoint;
  }
}
