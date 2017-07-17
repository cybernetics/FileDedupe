/*
 * Looks for duplicate files based on CRC-32 checksumming.
 * Project requires JDK 8 or later.
 *
 * Copyright (c) 2015 by Andrew Binstock. All rights reserved.
 * Licensed under the Creative Commons Attribution, Share Alike license
 * (CC BY-SA). Consult: https://creativecommons.org/licenses/by-sa/4.0/
 */

package filededup;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Accepts a path and walks the entire path (including subdirectories)
 * and creates a list of all files in the path.
 * 
 * @author alb
 */
public class DirFileListMaker
{
    ArrayList<Path> go( Path dir ) {
        if( dir == null || dir.toString().isEmpty() )
            throw( new InvalidParameterException() );

        ArrayList<Path> fileSet = new ArrayList<Path>();
        try {
            fileSet =
                Files.walk(dir)
                    .filter(p -> p.toFile().isFile())
                    .peek(System.out::println)
                    .collect(Collectors.toCollection(ArrayList::new));
        //    System.out.println("Size of file list: " + fileSet.size());
        } catch( Throwable t ) {
            System.out.println("Exception occurred in DirFileListMaker");
        }
        return( fileSet );
    }
}
