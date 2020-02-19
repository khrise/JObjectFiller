package com.tynamix.objectfiller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Filler<T> {

    public T Create(Class<T> classType) {

        try {
            T createdObject = classType.newInstance();
            FillerSetupItem fillerSetupItem = new FillerSetupItem();
            List<Field> privateFields = getSettableFields(classType);

            for (Field privateField : privateFields) {
                privateField.setAccessible(true);
                Supplier<?> valueSupplier = fillerSetupItem.getTypeToValueSupplier().get(privateField.getType());
                privateField.set(createdObject, valueSupplier.get());
                privateField.setAccessible(false);
            }

            return createdObject;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Field> getSettableFields(Class<T> classType) {
        List<Field> privateFields = new ArrayList<Field>();
        Field[] allFields = classType.getDeclaredFields();
        Method[] allMethods = classType.getDeclaredMethods();
        classType.getDeclaredMethods();
        for (Field field : allFields) {
            for (Method method : allMethods) {
                if (method.getName().equalsIgnoreCase("set" + field.getName())
                        && Modifier.isPrivate(field.getModifiers())) {
                    privateFields.add(field);
                }
            }
        }
        return privateFields;
    }


}
