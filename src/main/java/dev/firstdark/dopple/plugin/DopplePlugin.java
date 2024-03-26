/*
 * This file is part of dopple, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2024 HypherionSA and Contributors
 *
 */
package dev.firstdark.dopple.plugin;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * @author HypherionSA
 * The main plugin logic
 */
public class DopplePlugin implements Plugin<Project> {

    @Override
    public void apply(@NotNull Project target) {
        // Create our extension
        DoppleGradleExtension extension = target.getExtensions().create("dopple", DoppleGradleExtension.class);

        // Check if the Service token has been set, and download the secrets from the doppler API
        target.afterEvaluate(c -> {
            if (extension.getServiceToken().isPresent() && extension.getServiceToken().get().equalsIgnoreCase("INVALID"))
                return;

            extension.setEnvValues(getDopplerEnvTokens(extension.getServiceToken().get()));
        });
    }

    /**
     * Helper method to download the secrets from the Doppler API
     * @param token The Doppler service token
     * @return Hashmap of KEY/SECRET values, or empty map on failure
     */
    @SuppressWarnings("unchecked")
    private HashMap<String, String> getDopplerEnvTokens(String token) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.doppler.com/v3/configs/config/secrets/download?format=json")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return new Gson().fromJson(response.body().string(), HashMap.class);
            }
        } catch (Exception e) {
            throw new GradleException(e.getMessage());
        }

        return new HashMap<>();
    }
}
