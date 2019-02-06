package org.jfree.fx;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FXImageCache {
	static long getCount = 0;
	static Map<BufferedImage, FXImageCacheEntry> cache = new HashMap<>();

	static WritableImage getImage(BufferedImage bufferedImage)
	{
		checkCacheAge();
		getCount++;
		if(!cache.containsKey(bufferedImage)) {
			cache.put(bufferedImage, new FXImageCacheEntry(bufferedImage, getCount));
		}

		FXImageCacheEntry entry = cache.get(bufferedImage);
		entry.setLastUse(getCount);

		return entry.fxImage;
	}


	static void checkCacheAge()
	{
		if(getCount % 100 == 0)
		{
			Iterator<FXImageCacheEntry> iterator = cache.values().iterator();
			long minAge = getCount - 3 * cache.size();

			while(iterator.hasNext())
			{
				FXImageCacheEntry entry = iterator.next();
				if(entry.lastUse < minAge)
					iterator.remove(); //does this work?
			}

		}
	}


}


class FXImageCacheEntry {
	public BufferedImage bufferedImage;
	public WritableImage fxImage;
	public long lastUse;

	public FXImageCacheEntry(BufferedImage bufferedImage, long getCount) {
		this.bufferedImage = bufferedImage;
		fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
		lastUse = getCount;
	}

	public void setLastUse(long lastUse) {
		this.lastUse = lastUse;
	}
}