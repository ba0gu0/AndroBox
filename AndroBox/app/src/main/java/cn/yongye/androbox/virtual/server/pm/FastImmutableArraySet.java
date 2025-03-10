package cn.yongye.androbox.virtual.server.pm;

import java.util.AbstractSet;
import java.util.Iterator;

/**
 * A fast immutable set wrapper for an array that is optimized for
 * non-concurrent iteration. The same iterator instance is reused each time to
 * avoid creating lots of garbage. Iterating over an array in this fashion is
 * 2.5x faster than iterating over a {@link java.util.HashSet} so it is worth
 * copying the contents of the set to an array when iterating over it hundreds
 * of times.
 *
 * @hide
 */
public final class FastImmutableArraySet<T> extends AbstractSet<T> {
    FastIterator<T> mIterator;
    T[] mContents;

    public FastImmutableArraySet(T[] contents) {
        mContents = contents;
    }

    @Override
    public Iterator<T> iterator() {
        FastIterator<T> it = mIterator;
        if (it == null) {
            it = new FastIterator<T>(mContents);
            mIterator = it;
        } else {
            it.mIndex = 0;
        }
        return it;
    }

    @Override
    public int size() {
        return mContents.length;
    }

    private static final class FastIterator<T> implements Iterator<T> {
        private final T[] mContents;
        int mIndex;

        public FastIterator(T[] contents) {
            mContents = contents;
        }

        @Override
        public boolean hasNext() {
            return mIndex != mContents.length;
        }

        @Override
        public T next() {
            return mContents[mIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
