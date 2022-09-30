package com.example.KafkaSpringBoot.pojo;

import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.Disposables;

import java.util.Map;

@Component
public class DisposableSubscribersFactory {

    private final Map<String, Disposable.Composite> subscribersRegistry;

    public DisposableSubscribersFactory() {
        this(null);
    }

    public DisposableSubscribersFactory(Map<String, Disposable.Composite> subscribersRegistry) {
        this.subscribersRegistry = subscribersRegistry;
    }

    public void registerSubscribersToDispose(String registryKey, Disposable disposable) {
        Disposable.Composite disposableComposite = subscribersRegistry.get(registryKey);
        disposableComposite.add(disposable);
        subscribersRegistry.put(registryKey,disposableComposite);
    }

    public void disposeSubscribers(String registryKey) {
        if (subscribersRegistry.get(registryKey) != null)
            subscribersRegistry.get(registryKey).dispose();
        subscribersRegistry.put(registryKey, Disposables.composite());
    }
    
}
