package com.kt.giga.home.openapi.hcam.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.giga.home.openapi.hcam.internal.CtrlCallable;
import com.kt.giga.home.openapi.hcam.internal.RetvCallable;
import com.kt.giga.home.openapi.vo.spotdev.SpotDevRetvReslt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;



@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReportChannelService {

	private static Gson gson = null;
	static
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create();
	}

    private final Logger log = LoggerFactory.getLogger(getClass());
    private ConcurrentHashMap<UUID, RetvCallable> retvMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Long> elapsedTimeMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<UUID, CtrlCallable> ctrlMap = new ConcurrentHashMap<>();


    public void addRetvCallable(UUID uuid, RetvCallable callable) {
		this.retvMap.put(uuid, callable);
	}

	public void addSpotDevRetvReslt(UUID uuid, SpotDevRetvReslt reslt) {

		log.debug("### Received : {}", gson.toJson(reslt));
		RetvCallable retvAbstract = this.retvMap.get(uuid);

        if (null == retvAbstract)
            return;

		retvAbstract.getSpotDevRetvReslts().add(reslt);

		if (retvAbstract.isComplete())
			retvAbstract.wake();

	}

	public void addCtrlCallable(UUID uuid, CtrlCallable callable) {
		this.ctrlMap.put(uuid, callable);
	}

    public void addStartTime(String uuidString) {
        this.elapsedTimeMap.put(uuidString, System.nanoTime());
    }

    public Long getElapsed(String uuidString) {
        Long startTime = elapsedTimeMap.get(uuidString);

        if (startTime != null) {
            elapsedTimeMap.remove(uuidString);
            return (System.nanoTime() - startTime) / 1000000;
        } else {
            return null;
        }
    }

    @Scheduled(fixedDelay = 60 * 1000, initialDelay = 20 * 1000)
    public void deleteElapsed() {
        Iterator<String> iter = elapsedTimeMap.keySet().iterator();
        while (iter.hasNext()) {
            String uuidString = iter.next();
            long startTime = elapsedTimeMap.get(uuidString);
            if (((System.nanoTime() - startTime) / 1000000) > 10000) {
                log.debug("## Deleting maps...");
                iter.remove();
            }
        }
    }

}
