package com.kt.giga.home.infra.dao.idms;

import org.springframework.stereotype.Component;

import com.kt.giga.home.infra.domain.idms.SubscriptionInfoRequest;
import com.kt.giga.home.infra.domain.idms.SubscriptionManagerInfoRequest;

@Component
public interface SubscriptionDao {

	void insertSubscription(SubscriptionInfoRequest subscriptionInfoRequest);
	void updateSubscription(SubscriptionInfoRequest subscriptionInfoRequest);
	SubscriptionInfoRequest selectSubscription(SubscriptionInfoRequest subscriptionInfoRequest);

    void insertManagerSubscription(SubscriptionManagerInfoRequest subscriptionMnagerInfoRequest);
    void updateManagerSubscription(SubscriptionManagerInfoRequest subscriptionMnagerInfoRequest);
    SubscriptionManagerInfoRequest selectSubscriptionManager(SubscriptionManagerInfoRequest subscriptionMnagerInfoRequest);
	
}
