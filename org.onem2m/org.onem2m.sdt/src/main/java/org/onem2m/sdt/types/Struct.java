package org.onem2m.sdt.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.onem2m.sdt.types.DataType.TypeChoice;

public class Struct implements TypeChoice {
	
	private Collection<DataType> dataTypes;

	public Struct() {
		dataTypes = new ArrayList<DataType>();
	}

	public Collection<DataType> getDataTypes() {
		return Collections.unmodifiableCollection(dataTypes);
	}

	public void addDataType(final DataType dataType) {
		this.dataTypes.add(dataType);
	}

	public void removeDataType(final DataType dataType) {
		this.dataTypes.remove(dataType);
	}

}
