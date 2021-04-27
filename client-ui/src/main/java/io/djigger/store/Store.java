/*******************************************************************************
 * (C) Copyright 2016 Jérôme Comte and Dorian Cransac
 *
 *  This file is part of djigger
 *
 *  djigger is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  djigger is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with djigger.  If not, see <http://www.gnu.org/licenses/>.
 *
 *******************************************************************************/
package io.djigger.store;

import io.djigger.model.Capture;
import io.djigger.monitoring.java.instrumentation.InstrumentationEvent;
import io.djigger.monitoring.java.model.Metric;
import io.djigger.monitoring.java.model.ThreadInfo;

import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Store implements Serializable {

    private static final long serialVersionUID = 8746530878726453216L;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private final StoreCollection<Capture> captures = new StoreCollection<>(lock);

    private final StoreCollection<ThreadInfo> threadInfos = new StoreCollection<>(lock);

    private final StoreCollection<InstrumentationEvent> instrumentationEvents = new StoreCollection<>(lock);

    private final StoreCollection<Metric<?>> metrics = new StoreCollection<>(lock);

    public Store() {
        super();
    }

    public void clear() {
        captures.clear();
        threadInfos.clear();
        instrumentationEvents.clear();
        metrics.clear();
    }

    public void drainTo(Store target) {
        captures.drainTo(target.captures);
        threadInfos.drainTo(target.threadInfos);
        instrumentationEvents.drainTo(target.instrumentationEvents);
        metrics.drainTo(target.metrics);
    }

    public StoreCollection<Capture> getCaptures() {
        return captures;
    }

    public StoreCollection<ThreadInfo> getThreadInfos() {
        return threadInfos;
    }

    public StoreCollection<InstrumentationEvent> getInstrumentationEvents() {
        return instrumentationEvents;
    }

    public StoreCollection<Metric<?>> getMetrics() {
        return metrics;
    }

    public ReadWriteLock getLock(){
        return lock;
    }
}
