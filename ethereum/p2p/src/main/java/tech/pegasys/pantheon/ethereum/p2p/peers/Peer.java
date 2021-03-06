/*
 * Copyright 2018 ConsenSys AG.
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
package tech.pegasys.pantheon.ethereum.p2p.peers;

import tech.pegasys.pantheon.crypto.SecureRandomProvider;
import tech.pegasys.pantheon.util.bytes.BytesValue;
import tech.pegasys.pantheon.util.enode.EnodeURL;

public interface Peer extends PeerId {

  /** @return The enode representing the location of this peer. */
  EnodeURL getEnodeURL();

  /**
   * Generates a random peer ID in a secure manner.
   *
   * @return The generated peer ID.
   */
  static BytesValue randomId() {
    final byte[] id = new byte[64];
    SecureRandomProvider.publicSecureRandom().nextBytes(id);
    return BytesValue.wrap(id);
  }

  /**
   * Returns this peer's enode URL.
   *
   * @return The enode URL as a String.
   */
  default String getEnodeURLString() {
    return this.getEnodeURL().toString();
  }
}
