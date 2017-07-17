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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * De-duplicates a directory
 * @author alb (Andrew Binstock)
 */
class DirDeduper {
    private File dir;
    private String origPath;
    private HashMap tableOfChecksums;
    int i = 0;
    
    DirDeduper(String pathToDir) {
        origPath = pathToDir;
        dir = new File( pathToDir );
    }
    
    int go() {
        tableOfChecksums = new HashMap(101); // 101 is prime

        if( !dir.isDirectory() ) {
            System.err.println("Error: " + origPath + " is not a directory");
            return (Status.FILE_ERROR);
        }

        Path path = FileSystems.getDefault().getPath(origPath);

        List fileSet = new ArrayList();
        try {
            fileSet =
                Files.walk(path)
                    .filter(p -> p.toFile().isFile())
                    .peek(System.out::println)
                    .collect(Collectors.toCollection(ArrayList::new));
            System.out.println("Size of file list: " + fileSet.size());
        } catch( Throwable t ) {
            System.out.println("Exception occurred in go()");
        }
        return( fileSet.size() );
    }
    
//    public Path updateChecksums( Path p ) {
//        FileChecksum chksumCalculator = new FileChecksum( p );
//        long chksum = chksumCalculator.calculate();
//        tableOfChecksums.put( chksum, p );
//        System.out.println( "cksum: " + chksum + " file: " + p );
//        return p;
//    }
}