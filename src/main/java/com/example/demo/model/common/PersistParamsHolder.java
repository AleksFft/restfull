package com.example.demo.model.common;

import com.example.demo.util.CommonUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
public class PersistParamsHolder implements ParamsHolder, Serializable {

    private static final XStream xStream = new XStream(new XppDriver());

    public PersistParamsHolder() {
        xStream.addPermission(AnyTypePermission.ANY);
    }

    public PersistParamsHolder(@NonNull Map<String, String> params) {
        this();
        this.params = params;
    }

    @NonNull
    private Map<String, String> params = new HashMap<>();

    @Override
    public <T> T readValueByKey(String key) {
        String value = getParams().get(key);
        return deserialize(value);
    }

    @Override
    public <T> void writeValueByKey(String key, T value) {
        getParams().put(key, serialize(value));
    }

    @Override
    public void deleteByKey(String key) {
        getParams().remove(key);
    }

    /**
     * Create new params holder by merged current with supplied.
     *
     * @param another another params holder
     * @return new merged params holder
     */
    public PersistParamsHolder merge(PersistParamsHolder another) {
        HashMap<String, String> newParams = new HashMap<>(this.getParams());
        newParams.putAll(another.getParams());
        return new PersistParamsHolder(newParams);
    }

    /**
     * Copy new params holder.
     *
     * @return coped holder
     */
    public PersistParamsHolder copy() {
        HashMap<String, String> params = new HashMap<>(this.getParams());
        return new PersistParamsHolder(params);
    }

    /**
     * Get all parameters as deserialized list.
     *
     * @return list of parameters
     */
    public List<ImmutablePair<String, Object>> toList() {
        ArrayList<ImmutablePair<String, Object>> result = new ArrayList<>();
        params.forEach((key, value) -> result.add(ImmutablePair.of(key, deserialize(value))));
        return result;
    }

    /**
     * Create params holder from specified list.
     *
     * @param params list of parameters
     * @return params holder
     */
    public static PersistParamsHolder fromList(@NonNull List<? extends Pair<String, Object>> params) {
        PersistParamsHolder holder = new PersistParamsHolder();
        for (Pair<String, Object> pair : params) {
            holder.writeValueByKey(pair.getLeft(), pair.getRight());
        }
        return holder;
    }

    private static <T> String serialize(T object) {
        return CommonUtil.ifNonNull(object, xStream::toXML);
    }

    @SuppressWarnings("unchecked")
    private static <T> T deserialize(String serializedObject) {
        return (T) CommonUtil.ifNonNull(serializedObject, xStream::fromXML);
    }
}