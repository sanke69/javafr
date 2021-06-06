package fr.java.data.provider;

import fr.java.data.Data;

public interface DataProvider<COORD, TYPE> {

	public interface Async<COORD, TYPE> extends DataProvider<COORD, TYPE> {

		@Override
		public Data.Async<COORD, TYPE> 	get(COORD _coords);
		public void 		 			startLoading(Data.Async<COORD, TYPE> abstractData);

	}

	public Data<COORD, TYPE> get(COORD _coord);

}
