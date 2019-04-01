package net.kaneka.planttech2.property;

import java.util.Collection;
import java.util.Optional;

import net.kaneka.planttech2.librarys.CropList;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.minecraft.state.IProperty;

public class PropertyCrop implements IProperty<CropListEntry>
{
	private String name; 
	private CropList croplist; 
	private Collection<CropListEntry> allowedValues; 

	public PropertyCrop(String name, CropList croplist)
	{
		this.name = name; 
		this.croplist = croplist; 
		this.allowedValues = croplist.getAllEntries(); 
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public Collection<CropListEntry> getAllowedValues()
	{
		return this.allowedValues;
	}

	@Override
	public Class getValueClass()
	{
		//THIS IS A TEST
		int i = 10;
		return CropListEntry.class;
	}
	
	@Override
	public String getName(CropListEntry value)
	{
		return value.getString();
	}

	@Override
	public Optional<CropListEntry> parseValue(String value)
	{
		return Optional.<CropListEntry>ofNullable(this.croplist.getEntryByName(value));
	}


}
