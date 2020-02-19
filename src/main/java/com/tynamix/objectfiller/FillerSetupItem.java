package com.tynamix.objectfiller;

import com.tynamix.objectfiller.plugins.Integer.RandomIntPlugin;
import com.tynamix.objectfiller.plugins.String.RandomStringPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

class FillerSetupItem {
    private final Map<Class<?>, Supplier<?>> typeToRandomFunc;

    FillerSetupItem() {
        this.typeToRandomFunc = new HashMap<>();
        this.setDefaultRandomSuppliers();
    }

    public Map<Class<?>, Supplier<?>> getTypeToValueSupplier() {
        return typeToRandomFunc;
    }

    private void setDefaultRandomSuppliers() {
        typeToRandomFunc.put(String.class, new RandomStringPlugin()::createValue);
        typeToRandomFunc.put(Integer.class, new RandomIntPlugin()::createValue);
        typeToRandomFunc.put(int.class, new RandomIntPlugin()::createValue);
        typeToRandomFunc.put(UUID.class, UUID::randomUUID);
    }


}
