package com.dirlist.business;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dirlist.model.response.DirectoryContent;

@Service
public class Directory implements IDirectory
{
	public ResponseEntity< List< DirectoryContent > > listDirectoryContent( String path )
	{
		if ( path == null )
		{
			// If the API is called without a RequestParam we assume the root folder is requested. 
			path = "/";
			
			// We can also force a RequestParam to be sent by using the bellow code to return a 404 indicating the path was not found.
			// return new ResponseEntity< List< DirectoryContent > >( HttpStatus.NOT_FOUND );
		}

		File folder = new File( path );

		if ( folder.exists() == false )
		{
			// The requested path does not exist so return a 404.
			return new ResponseEntity< List< DirectoryContent > >( HttpStatus.NOT_FOUND );
		}

		List< DirectoryContent > response = processPathContent( folder.listFiles() );
		return response.size() > 0 ? returnContent( response ) : returnNoContent();
	}

	private List< DirectoryContent > processPathContent( File[] fileList )
	{
		List< DirectoryContent > response = new ArrayList< DirectoryContent >();

		for ( File file : fileList )
		{
			// Get information for each file in folder
			DirectoryContent dc = new DirectoryContent();
			dc.setName( file.getName() );
			dc.setPath( file.getAbsolutePath() );
			dc.setAttributes( getBasicFileAttributes( Paths.get( file.toURI() ) ) );

			response.add( dc );
		}

		return response;
	}

	/**
	 * Returns a map of attributes for file or folder
	 * @param filePath - Absolute path to file/folder
	 * @return File attributes. 
	 */
	private Map< String, String > getBasicFileAttributes( Path filePath )
	{
		Map< String, String > basicFileAttributes = new HashMap< String, String >();

		try
		{
			BasicFileAttributes attr = Files.readAttributes( filePath, BasicFileAttributes.class );

			basicFileAttributes.put( "creationTime", attr.creationTime().toString() );
			basicFileAttributes.put( "lastAccessTime", attr.lastAccessTime().toString() );
			basicFileAttributes.put( "lastModifiedTime", attr.lastModifiedTime().toString() );
			basicFileAttributes.put( "isDirectory", Boolean.toString( attr.isDirectory() ) );
			basicFileAttributes.put( "isRegularFile", Boolean.toString( attr.isRegularFile() ) );
			basicFileAttributes.put( "isSymbolicLink", Boolean.toString( attr.isSymbolicLink() ) );
			basicFileAttributes.put( "sizeInBytes", Long.toString( attr.size() ) );
		}
		catch ( IOException ex )
		{
			// Unable to get file attributes.
			// This should not stop us from returning the information we can get so just suppress the error.
		}

		return basicFileAttributes;
	}

	/**
	 * The request completed with expected result. 
	 * @param content - Content to be returned to the controller.
	 * @return 200 - Request completed with content in the body.
	 */
	private ResponseEntity< List< DirectoryContent > > returnContent( List< DirectoryContent > content )
	{
		return new ResponseEntity< List< DirectoryContent > >( content, HttpStatus.OK );
	}

	/**
	 * The requested path does exist but has no content inside. 
	 * @return 204 - Requested completed but no content to return.
	 */
	private ResponseEntity< List< DirectoryContent > > returnNoContent()
	{
		return new ResponseEntity< List< DirectoryContent > >( HttpStatus.NO_CONTENT );
	}
}