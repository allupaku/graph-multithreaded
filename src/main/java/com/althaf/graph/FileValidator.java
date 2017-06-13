package com.althaf.graph;

import org.apache.commons.lang3.SystemUtils;

import java.util.Arrays;

/**
 * Created by althaf mohamed
 * Validator for validating the file name strings
 */
public abstract class FileValidator {
    private static final String INSTALLED_PLATFORM;

    public static final char[] INVALID_RESOURCE_CHARACTERS;
    private static final String[] INVALID_RESOURCE_BASENAMES;
    private static final String[] INVALID_RESOURCE_FULLNAMES;

    static {
      //find out the OS being used
      //setup the invalid names
      INSTALLED_PLATFORM = SystemUtils.OS_NAME;
      if (SystemUtils.IS_OS_WINDOWS) {

        INVALID_RESOURCE_CHARACTERS = new char[] {'\\', '/', ':', '*', '?', '"', '<', '>', '|'};
        // some of the windows specific file names, which we shouldnt alter.
        INVALID_RESOURCE_BASENAMES = new String[] {"aux", "com1", "com2", "com3", "com4", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "com5", "com6", "com7", "com8", "com9", "con", "lpt1", "lpt2", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
            "lpt3", "lpt4", "lpt5", "lpt6", "lpt7", "lpt8", "lpt9", "nul", "prn"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
        Arrays.sort(INVALID_RESOURCE_BASENAMES);
        //CLOCK$ may be used if an extension is provided
        INVALID_RESOURCE_FULLNAMES = new String[] {"clock$"}; //$NON-NLS-1$
      } else {
        //only front slash and null char are invalid on UNIXes

        INVALID_RESOURCE_CHARACTERS = new char[] {'/', '\0',};
        INVALID_RESOURCE_BASENAMES = null;
        INVALID_RESOURCE_FULLNAMES = null;
      }
    }

    /**
     * Returns true if the given name is a valid resource name on this operating system,
     * and false otherwise.
     */
    public static boolean isNameValid(String name) {
      //. and .. have special meaning on all platforms
      if (name.equals(".") || name.equals(".."))
        return false;
      if (SystemUtils.IS_OS_WINDOWS) {
        //empty names are not valid
        final int length = name.length();
        if (length == 0)
          return false;
        final char lastChar = name.charAt(length-1);
        // filenames ending in dot are not valid
        if (lastChar == '.')
          return false;

        if (Character.isWhitespace(lastChar))
          return false;
        int dot = name.indexOf('.');

        String basename = dot == -1 ? name : name.substring(0, dot);
        if (Arrays.binarySearch(INVALID_RESOURCE_BASENAMES, basename.toLowerCase()) >= 0)
          return false;
        return Arrays.binarySearch(INVALID_RESOURCE_FULLNAMES, name.toLowerCase()) < 0;
      }
      return true;
    }
}
