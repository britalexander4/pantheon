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
package tech.pegasys.pantheon.ethereum.eth.sync.fullsync;

import tech.pegasys.pantheon.ethereum.ProtocolContext;
import tech.pegasys.pantheon.ethereum.eth.manager.EthContext;
import tech.pegasys.pantheon.ethereum.eth.sync.ChainDownloader;
import tech.pegasys.pantheon.ethereum.eth.sync.CheckpointHeaderManager;
import tech.pegasys.pantheon.ethereum.eth.sync.EthTaskChainDownloader;
import tech.pegasys.pantheon.ethereum.eth.sync.PipelineChainDownloader;
import tech.pegasys.pantheon.ethereum.eth.sync.SynchronizerConfiguration;
import tech.pegasys.pantheon.ethereum.eth.sync.state.SyncState;
import tech.pegasys.pantheon.ethereum.mainnet.ProtocolSchedule;
import tech.pegasys.pantheon.metrics.MetricsSystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FullSyncChainDownloader {
  private static final Logger LOG = LogManager.getLogger();

  private FullSyncChainDownloader() {}

  public static <C> ChainDownloader create(
      final SynchronizerConfiguration config,
      final ProtocolSchedule<C> protocolSchedule,
      final ProtocolContext<C> protocolContext,
      final EthContext ethContext,
      final SyncState syncState,
      final MetricsSystem metricsSystem) {

    final FullSyncTargetManager<C> syncTargetManager =
        new FullSyncTargetManager<>(
            config, protocolSchedule, protocolContext, ethContext, metricsSystem);

    if (config.isPiplineDownloaderForFullSyncEnabled()) {
      LOG.info("Using PipelineChainDownloader");
      return new PipelineChainDownloader<>(
          syncState,
          syncTargetManager,
          new FullSyncDownloadPipelineFactory<>(
              config, protocolSchedule, protocolContext, ethContext, metricsSystem),
          ethContext.getScheduler(),
          metricsSystem);
    }

    LOG.info("Using EthTaskChainDownloader");
    return new EthTaskChainDownloader<>(
        config,
        ethContext,
        syncState,
        syncTargetManager,
        new CheckpointHeaderManager<>(
            config, protocolContext, ethContext, syncState, protocolSchedule, metricsSystem),
        new FullSyncBlockImportTaskFactory<>(
            config, protocolSchedule, protocolContext, ethContext, metricsSystem),
        metricsSystem);
  }
}
