/*
 * This file is part of dopple, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2024 HypherionSA and Contributors
 *
 */
package dev.firstdark.dopple.plugin;

import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;

/**
 * @author HypherionSA
 * Main Plugin extension
 */
public class DoppleGradleExtension {

    // Private map of secrets downloaded from the Doppler API
    protected HashMap<String, String> envValues;

    // Service token for the project to pull secrets from
    @Getter private final Property<String> serviceToken;

    public DoppleGradleExtension(Project project) {
        this.serviceToken = project.getObjects().property(String.class).convention("INVALID");
    }

    /**
     * Internal method to expose the downloaded Doppler secrets to gradle
     * @param v The map of secrets and values, or empty map on failure
     */
    @ApiStatus.Internal
    void setEnvValues(HashMap<String, String> v) {
        this.envValues = v;
    }

    /**
     * Get the value of a Secret. Similar to getenv
     * @param key The KEY to get the value of
     * @return The value or INVALID if the value cannot be found
     */
    public String get(String key) {
        return envValues.getOrDefault(key, "INVALID");
    }
}
