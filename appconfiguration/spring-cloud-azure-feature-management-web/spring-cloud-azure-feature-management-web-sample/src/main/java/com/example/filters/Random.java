// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.example.filters;

import com.azure.spring.cloud.feature.management.filters.FeatureFilter;
import com.azure.spring.cloud.feature.management.models.FeatureFilterEvaluationContext;
import org.springframework.stereotype.Component;

@Component("Random")
public class Random implements FeatureFilter {

    @Override
    public boolean evaluate(FeatureFilterEvaluationContext context) {
        double chance = Double.valueOf((String) context.getParameters().get("chance"));
        return Math.random() > chance / 100;
    }
}
