package com.dirlist.model.response;

import java.io.Serializable;
import java.util.Map;

public class DirectoryContent implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String path;
	private Map< String, String > attributes;

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath( String path )
	{
		this.path = path;
	}

	public Map< String, String > getAttributes()
	{
		return attributes;
	}

	public void setAttributes( Map< String, String > attributes )
	{
		this.attributes = attributes;
	}
}