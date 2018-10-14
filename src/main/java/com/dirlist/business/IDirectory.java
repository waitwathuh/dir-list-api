package com.dirlist.business;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dirlist.model.response.DirectoryContent;

public interface IDirectory
{
	public ResponseEntity< List< DirectoryContent > > listDirectoryContent( String path );
}