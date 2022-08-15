package com.example.demo.model.common;

public interface ParamsHolder {

    /**
     * Find param by key.
     *
     * @param key param key
     * @param <T> param type
     * @return param value or {@code null} it param with specified key doesn't exist
     */
    <T> T readValueByKey(String key);

    /**
     * Save param with specified key.
     *
     * @param key   parameter key
     * @param value parameter value
     * @param <T>   parameter value type
     */
    <T> void writeValueByKey(String key, T value);

    /**
     * Remove parameter by specified key.
     *
     * @param key parameter key
     */
    void deleteByKey(String key);
}

