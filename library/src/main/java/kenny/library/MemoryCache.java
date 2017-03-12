package kenny.library;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @author Junyao
 * @package kenny.library
 */

public class MemoryCache implements ImageCache {
    /**
     * A map for Caching bitmap that we have downloaded.
     *
     */
    private LruCache<String, Bitmap> mMemoryCache;


    public MemoryCache() {
        initImageCache();
    }

    private void initImageCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        final int cacheSize = maxMemory / 4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap get(String url) {
        return mMemoryCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }
}
