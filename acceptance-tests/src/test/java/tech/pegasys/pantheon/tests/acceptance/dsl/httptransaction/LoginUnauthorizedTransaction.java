/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.pantheon.tests.acceptance.dsl.httptransaction;

import static org.junit.Assert.fail;

import java.io.IOException;

public class LoginUnauthorizedTransaction implements HttpTransaction<Void> {
  private final String username;
  private final String password;

  public LoginUnauthorizedTransaction(final String username, final String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public Void execute(final HttpRequestFactory httpFactory) {
    try {
      httpFactory.loginUnauthorized(username, password);
      return null;
    } catch (IOException e) {
      fail("Login request failed with exception: " + e.toString());
      return null;
    }
  }
}
