package com.tynamix.objectfiller.plugins.Integer;

import com.tynamix.objectfiller.plugins.RandomizerPlugin;

import java.util.concurrent.ThreadLocalRandom;

public class RandomIntPlugin implements RandomizerPlugin<Integer> {

    private final int minValue;

    private final int maxValue;

    public RandomIntPlugin() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public RandomIntPlugin(int maxValue) {
        this(0, maxValue);
    }

    public RandomIntPlugin(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public Integer createValue() {
        return ThreadLocalRandom.current().nextInt(this.minValue, this.maxValue);
    }
}
