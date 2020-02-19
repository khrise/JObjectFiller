package com.tynamix.objectfiller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Filler<T> {

    public T create(Class<T> classType) {

        return createAndFill(classType);

    }

    private T createAndFill(Class<T> classType) {

        T createdObject = (T) createInstanceOfType(classType, null);
        fillObject(classType, createdObject);

        return createdObject;
    }

    private Object createInstanceOfType(Class<?> classType, FillerSetupItem currentSetupItem) {
        try {
            return classType.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void fillObject(Class<?> classType, Object createdObject) {

        FillerSetupItem fillerSetupItem = new FillerSetupItem();

        if (fillerSetupItem.getTypeToValueSupplier().containsKey(classType)) {
            createdObject = (T) fillerSetupItem.getTypeToValueSupplier().get(classType).get();
            return;
        }

        List<Field> privateFields = getSettableFields(classType);

        fillProperties(createdObject, fillerSetupItem, privateFields);
    }

    private void fillProperties(Object createdObject, FillerSetupItem currentSetupItem, List<Field> privateFields) {
        if (privateFields.size() == 0) {
            return;
        }

        for (Field privateField : privateFields) {
            privateField.setAccessible(true);

            Object filledObject = createAndFillObject(privateField.getType(), currentSetupItem);
            try {
                privateField.set(createdObject, filledObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            privateField.setAccessible(false);
        }
    }

    private Object createAndFillObject(Class<?> type, FillerSetupItem currentSetupItem) {
        if (hasTypeARandomFunc(type, currentSetupItem)) {
            return this.getRandomValue(type, currentSetupItem);
        }

        if (typeIsPoco(type)) {
            return this.getFilledPoco(type, currentSetupItem);
        }

        Object newValue = this.getRandomValue(type, currentSetupItem);
        return newValue;
    }

    private Object getFilledPoco(Class<?> type, FillerSetupItem currentSetupItem) {
        Object result = this.createInstanceOfType(type, currentSetupItem);

        this.fillObject(type, result);

        return result;
    }

    private boolean typeIsPoco(Class<?> type) {
        return !type.isPrimitive() && !type.isArray() && getSettableFields(type).size() > 0;
    }

    private Object getRandomValue(Class<?> type, FillerSetupItem currentSetupItem) {
        if (hasTypeARandomFunc(type, currentSetupItem)) {
            return currentSetupItem.getTypeToValueSupplier().get(type).get();
        }

        return null;
    }

    private boolean hasTypeARandomFunc(Class<?> type, FillerSetupItem currentSetupItem) {
        return currentSetupItem.getTypeToValueSupplier().containsKey(type);
    }

    private List<Field> getSettableFields(Class<?> classType) {
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
