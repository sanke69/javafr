package fr.java.sdk.data.async.caches;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.NotYetBoundException;

import fr.java.data.DataException;
import fr.java.data.DataLoadException;
import fr.java.data.DataNotFoundException;
import fr.java.data.provider.caches.DataCacheOnLine;
import fr.java.lang.enums.AccessMode;
import fr.java.patterns.priority.Priority;

public abstract class AbstractDataCacheOnLine<COORD, TYPE> extends AbstractDataCache<COORD, TYPE> implements DataCacheOnLine<COORD, TYPE> {
	private static final String user_agent =
//			"Research Software for Study Application";
//			"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
			"Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20100101 Firefox/10.0";

	public AbstractDataCacheOnLine() {
		super(CacheType.INTERNET);
	}
	public AbstractDataCacheOnLine(Priority _priority) {
		super(CacheType.INTERNET, AccessMode.ReadOnly, _priority);
	}

	@Override
	public TYPE 			get(COORD _coords) throws DataException {
		try {
			InputStream is = download( getURL(_coords) );
			if(is == null)
				throw new DataNotFoundException("No data downloaded");

			return getContent(is);

		} catch(IOException e) {
			throw new DataLoadException("Failed to decode inputstream", e);
		}
	}

	public abstract URL		getURL(COORD _coords);
	public abstract TYPE	getContent(InputStream _file) throws DataException;

	protected InputStream 	download(URL _url) throws DataException, IOException {
		if(!isServerOnline())
			throw new DataNotFoundException("Server is Offline");

		return download_with_agent(_url);
	}
	protected InputStream 	download_default(URL _url) throws DataException {
		try {
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
			InputStream       is   = conn.getInputStream();

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buf    = new byte[256];
			int    nbRead = 0;
			while((nbRead = is.read(buf)) != -1) {
				bout.write(buf, 0, nbRead);
			}
			return new ByteArrayInputStream(bout.toByteArray());
		} catch(IOException e) {
			e.printStackTrace();
			throw new DataNotFoundException("Failed to download content at " + _url, e);
		}
	}
	protected InputStream 	download_with_agent(URL _url) throws DataException {
		try {
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
	        conn.setRequestProperty("User-Agent", user_agent);
			InputStream       is   = conn.getInputStream();

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buf    = new byte[256];
			int    nbRead = 0;
			while((nbRead = is.read(buf)) != -1) {
				bout.write(buf, 0, nbRead);
			}

			return new ByteArrayInputStream(bout.toByteArray());
		} catch(IOException e) {
			e.printStackTrace();
			throw new DataNotFoundException("Failed to download content at " + _url + " using agent", e);
		}
	}

	protected void		 	upload(URL url, InputStream _is) throws IOException {
		throw new NotYetBoundException();
	}

}
