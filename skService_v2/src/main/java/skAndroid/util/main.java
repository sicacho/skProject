package skAndroid.util;

import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * Created by khangtnse60992 on 2/13/2015.
 */
    import  java.util.Hashtable;
    import  javax.naming.*;
    import  javax.naming.directory.*;

    public class main {
        public static void main( String args[] ) {
            try {
                System.out.println(doLookup("lazyen2g.com"));
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }

        static int doLookup( String hostName ) throws NamingException {
            Hashtable env = new Hashtable();
            env.put("java.naming.factory.initial",
                    "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext( env );
            Attributes attrs =
                    ictx.getAttributes( hostName, new String[] { "MX" });
            Attribute attr = attrs.get( "MX" );
            if( attr == null ) return( 0 );
            return( attr.size() );
        }
    }

