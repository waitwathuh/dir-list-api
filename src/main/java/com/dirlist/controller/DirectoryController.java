package com.dirlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dirlist.business.IDirectory;
import com.dirlist.model.response.DirectoryContent;

@RestController
public class DirectoryController
{
	@Autowired
	private IDirectory directory;

	@RequestMapping(value="/dir", method = RequestMethod.GET)
	public ResponseEntity< List< DirectoryContent > > listDirectoryContent( @RequestParam(value = "path", required = false) String pathOrNull )
	{
		try
		{
			return directory.listDirectoryContent( pathOrNull );
		}
		catch ( Exception ex )
		{
			return new ResponseEntity< List< DirectoryContent > >( HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
}