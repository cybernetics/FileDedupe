/*
 * Looks for duplicate files based on CRC-32 checksumming. 
 * Project requires JDK 8 or later.
 *
 * Copyright (c) 2015 by Andrew Binstock. All rights reserved.
 * Licensed under the Creative Commons Attribution, Share Alike license
 * (CC BY-SA). Consult: https://creativecommons.org/licenses/by-sa/4.0/
 */
package filededup;

import java.io.File;
import java.nio.file.*;
import java.util.*;

/**
 * De-duplicates a directory
 * @author alb (Andrew Binstock)
 */
class DirDeduper {
    private File dir;
    private String origPath;
    private HashMap fileChecksums;

    DirDeduper(String pathToDir) {
        origPath = pathToDir;
        dir = new File( pathToDir );
    }
    
    int go() {

        if( !dir.isDirectory() ) {
            System.err.println("Error: " + origPath + " is not a directory");
            return( Status.FILE_ERROR );
        }

        // create a list of all the files in the directory and its subdirectories
        Path path = FileSystems.getDefault().getPath(origPath);
        ArrayList<Path> fileSet = new DirFileListMaker().go( path );
        if( fileSet.isEmpty() ) {
            System.err.println("Error: Directory " + origPath + " contains no files");
            return( Status.FILE_ERROR );
        }

        return( Status.OK );
    }
    
//    public Path updateChecksums( Path p ) {
//        FileChecksum chksumCalculator = new FileChecksum( p );
//        long chksum = chksumCalculator.calculate();
//        tableOfChecksums.put( chksum, p );
//        System.out.println( "cksum: " + chksum + " file: " + p );
//        return p;
//    }
}