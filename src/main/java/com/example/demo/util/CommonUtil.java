package com.example.demo.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import ru.vtb.dbo.dictionary.dto.IdentifiableDto;
import ru.vtb.dbo.dictionary.dto.LookupDto;

@Slf4j
@UtilityClass
public class CommonUtil {

    /**
     * Simple method for composing look up dto.
     *
     * @param id  entity id
     * @param <T> type
     * @return composed look up
     */
    public static <T extends IdentifiableDto> LookupDto<T> lookup(Long id) {
        return new LookupDto<T>().setId(id);
    }

    /**
     * Return stream of pair index vs object.
     *
     * @param source source
     * @param <T>    value type
     * @return stream
     */
    public static <T> Stream<Pair<Integer, T>> zipWithIndex(Stream<T> source) {
        AtomicInteger index = new AtomicInteger();
        return source
                .map(it -> Pair.of(index.getAndIncrement(), it));
    }

    /**
     * Разбиение списка на пачки.
     *
     * @param source source element stream
     * @param batch  batch size
     * @param <T>    element type
     * @return batched stream
     */
    public static <T> Stream<List<T>> batch(@NonNull Stream<T> source, int batch) {
        Stream<List<T>> stream = StreamSupport.stream(new Batcher<>(batch, source.map(List::of)), false);
        return stream.onClose(source::close);
    }

    /**
     * На входе поток списков, на выходе поток списков разбитых примерно по batchSize элементов.
     * Входящие элементы (списки) не должны разбиваться в разные пачки на выходе.
     * Например:
     * на входе поток (1), (2), (3), (4, 5), (6), (7, 8)
     * batchSize = 4
     * На выходе
     * (1, 2, 3, 4, 5), (6, 7, 8)
     * (4,5) и (7, 8) остаются в одной пачке
     *
     * @param source source element stream
     * @param batch  batch size
     * @param <T>    element type
     * @return batched stream
     */
    public static <T> Stream<List<T>> batchList(@NonNull Stream<List<T>> source, int batch) {
        Stream<List<T>> stream = StreamSupport.stream(new Batcher<>(batch, source), false);
        return stream.onClose(source::close);
    }

    /**
     * Spliterator для разбиения списка списков на пачки для batch() и batchList().
     *
     * @param <T> element type
     */
    private static class Batcher<T> implements Spliterator<List<T>> {

        final int batchSize;
        final Spliterator<List<T>> source;
        final List<T> batch;

        Batcher(int batchSize, Stream<List<T>> source) {
            this.batchSize = batchSize;
            this.source = source.spliterator();
            this.batch = new ArrayList<>(batchSize);
        }

        @Override
        public boolean tryAdvance(Consumer<? super List<T>> action) {
            boolean more = source.tryAdvance(batch::addAll);

            if (batch.size() >= batchSize) {
                action.accept(new ArrayList<>(batch));
                batch.clear();
            } else {
                if (!more && !batch.isEmpty()) {
                    action.accept(new ArrayList<>(batch));
                    batch.clear();
                }
            }

            return more;
        }

        @Override
        public Spliterator<List<T>> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public int characteristics() {
            return 0;
        }
    }

    /**
     * Apply function to non null value or return default value.
     *
     * @param t            value
     * @param map          function
     * @param defaultValue default value
     * @param <T>          value type
     * @param <V>          result type
     * @return mapped value
     */
    public static <T, V> V ifNonNull(T t, Function<T, V> map, V defaultValue) {
        if (Objects.nonNull(t)) {
            return map.apply(t);
        }
        return defaultValue;
    }

    /**
     * Apply function to non null value or return null.
     *
     * @param t   value
     * @param map function
     * @param <T> value type
     * @param <V> result type
     * @return mapped value
     */
    public static <T, V> V ifNonNull(T t, Function<T, V> map) {
        return ifNonNull(t, map, null);
    }

    /**
     * Concatenated streams.
     *
     * @param streams array of streams
     * @param <T>     type of value
     * @return stream
     */
    @SafeVarargs
    public <T> Stream<T> concatStreams(Stream<T>... streams) {
        return Arrays.stream(streams).flatMap(Function.identity());
    }

    /**
     * Distinct by key predicate.
     *
     * @param keyExtractor key extractor
     * @param <T>          type of value
     * @return predicate
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> Objects.isNull(seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE));
    }

    /**
     * Extract message from exception.
     *
     * <p>Message is extracted recursively. If current error does not have message but has cause error,
     * message will be searched in cause. If no one error message will be found then empty string is returned.
     * A message of {@link InvocationTargetException} is skipped from search.
     *
     * @param e exception
     * @return extracted message
     */
    public String extractMessageFromException(@NonNull Throwable e) {
        return findFirstNotNullMessage(e).orElse("");
    }

    private Optional<String> findFirstNotNullMessage(Throwable e) {
        if (e instanceof InvocationTargetException) {
            return Optional.ofNullable(e.getCause()).flatMap(CommonUtil::findFirstNotNullMessage);
        }
        Optional<String> result = Optional.ofNullable(e.getMessage());
        return result.or(() -> Optional.ofNullable(e.getCause()).flatMap(CommonUtil::findFirstNotNullMessage));
    }
}

