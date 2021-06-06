package fr.java.sdk.player.playlists.m3u;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.MalformedInputException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import fr.java.player.PlayList;
import fr.java.player.PlayMap;
import fr.java.sdk.file.Source;
import fr.java.sdk.player.playlists.defaults.DefaultPlayList;
import fr.java.sdk.player.playlists.defaults.DefaultPlayMap;

public class M3U {

	static abstract class BuilderBase<P> {
		Source 				source;
		Map<String, String>	content;

		protected BuilderBase() {
			super();
		}

		public BuilderBase<P> setSource(Source _source) {
			source = _source;
			return this;
		}

		void readSource() {
			content = new HashMap<String, String>();

			try (BufferedReader br = new BufferedReader(new InputStreamReader(source.getInputStream()))) {
				final String HEAD = "#EXTM3U";
				final String HKEY = "#EXTINF:";

				String label, url;

				// CHECK HEADER
				label = br.readLine();
				if (label.compareTo(HEAD) != 0)
					throw new MalformedInputException(-1);

				// READ LINKS
				do {
					// READ LINK LABEL
					label = br.readLine();
					if (!label.contains(HKEY))
						throw new MalformedInputException(-1);
					label = label.substring(label.indexOf(HKEY) + HKEY.length());

					// READ LINK URL
					url = br.readLine();

					// POPULATE CONTENT
					content.put(label, url);
				} while (br.ready());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	static public class ListBuilder<P> extends BuilderBase<P> implements PlayList.Builder<P> {
		Function<Map<String, String>, Collection<P>> playableListBuilder;

		public ListBuilder() {
			super();
		}

		public ListBuilder<P> setSource(Source _source) {
			super.setSource(_source);
			return this;
		}
		public ListBuilder<P> setPlayableListBuilder(Function<Map<String, String>, Collection<P>> _playableBuilder) {
			playableListBuilder = _playableBuilder;
			return this;
		}

		@Override
		public PlayList<P> build() {
			readSource();

			return new DefaultPlayList<P>( playableListBuilder.apply(content) );
		}
		
	}
	static public class MapBuilder<T, P> extends BuilderBase<P> implements PlayMap.Builder<T, P> {
		Function<Map<String, String>, Map<T, P>> playableMapBuilder;

		public MapBuilder() {
			super();
		}

		public MapBuilder<T, P> setSource(Source _source) {
			super.setSource(_source);
			return this;
		}
		public MapBuilder<T, P> setPlayableMapBuilder(Function<Map<String, String>, Map<T, P>> _playableBuilder) {
			playableMapBuilder = _playableBuilder;
			return this;
		}

		@Override
		public PlayMap<T, P> build() {
			readSource();

			return new DefaultPlayMap<T, P>( playableMapBuilder.apply(content) );
		}
		
	}

}
