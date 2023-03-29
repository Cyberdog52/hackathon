package ch.zuehlke.fullstack.hackathon.service;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class RoundRobin<T> implements Iterable<T> {

    private final List<T> items;
    private int index = 0;

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return true;
            }
            @Override
            public T next() {
                if (index >= items.size()) {
                    index = 0;
                }
                return items.get(index++);
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

}
