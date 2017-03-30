package com.kt.giga.home.openapi.hcam.internal;

import com.kt.giga.home.openapi.vo.spotdev.SpotDev;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

public class RetvCallable<V> implements Callable<V> {

	protected int requestCount = 0;

	protected UUID uuid;

	protected List<SpotDevRetvReslt> sourceReslts = Collections.synchronizedList(new ArrayList<SpotDevRetvReslt>());

	protected List<SpotDev> spotDevs = Collections.synchronizedList(new ArrayList<SpotDev>());

	protected RetvCallback<V> retvCallback;

	protected long timeout = 1500L;

    public RetvCallable() {
    }

	public int getRequestCount() {
		return this.requestCount;
	}

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

	public UUID getUUID() {
		return uuid;
	}

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public List<SpotDev> getSpotDevs() {
        return this.spotDevs;
    }

	public void setSpotDevs(List<SpotDev> spotDevs) {
		this.spotDevs = spotDevs;
	}

	public RetvCallback<V> getRetvCallback() {
		return this.retvCallback;
	}

    public void setRetvCallback(RetvCallback<V> retvCallback) {
        this.retvCallback = retvCallback;
    }

	public List<SpotDevRetvReslt> getSpotDevRetvReslts() {
		return this.sourceReslts;
	}

    public void setSpotDevRetvReslts(List<SpotDevRetvReslt> spotDevRetvReslts) {
        this.sourceReslts = spotDevRetvReslts;
    }

	public void addSourceReslt(SpotDevRetvReslt spotDevRetvReslt) {

		synchronized (sourceReslts) {
			sourceReslts.add(spotDevRetvReslt);
		}
	}

	public long getTimeout() {
		return this.timeout;
	}

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isComplete() {
        return this.sourceReslts.size() == requestCount;
    }

	@Override
	public V call() throws Exception {
		synchronized(this) {
			if (this.requestCount > 0)
				this.wait(timeout);
		}

		V retv = null;
		synchronized (sourceReslts) {
			retv = retvCallback.retv(spotDevs, sourceReslts);
		}

		return retv;

	}

	public void wake() {
		synchronized(this) {
			this.notify();
		}
	}

}
