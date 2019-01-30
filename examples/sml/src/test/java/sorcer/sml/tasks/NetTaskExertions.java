package sorcer.sml.tasks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.arithmetic.provider.Adder;
import sorcer.arithmetic.provider.Averager;
import sorcer.arithmetic.provider.Multiplier;
import sorcer.arithmetic.provider.Subtractor;
import sorcer.arithmetic.provider.impl.AdderImpl;
import sorcer.arithmetic.provider.impl.MultiplierImpl;
import sorcer.core.provider.Provider;
import sorcer.core.provider.RemoteServiceShell;
import sorcer.po.operator;
import sorcer.service.*;
import sorcer.service.Strategy.Access;
import sorcer.service.Strategy.Monitor;
import sorcer.service.Strategy.Wait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static sorcer.co.operator.*;
import static sorcer.eo.operator.*;
import static sorcer.eo.operator.get;

/**
 * @author Mike Sobolewski
 */
@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/sml")
public class NetTaskExertions {
	private final static Logger logger = LoggerFactory.getLogger(NetTaskExertions.class);

	@Test
	public void exertTask() throws Exception  {

		Task t5 = task("t5", sig("add", Adder.class),
				cxt("add", inVal("arg/x1", 20.0), inVal("arg/x2", 80.0), result("result/y")));

		Exertion out = exert(t5);
		Context cxt = context(out);
		logger.info("out context: " + cxt);
		logger.info("context @ arg/x1: " + value(cxt, "arg/x1"));
		logger.info("context @ arg/x2: " + value(cxt, "arg/x2"));
		logger.info("context @ result/y: " + value(cxt, "result/y"));

		// get a single context argument
		assertEquals(100.0, value(cxt, "result/y"));

		// get the subcontext output from the context
		assertTrue(context(operator.ent("arg/x1", 20.0), operator.ent("result/y", 100.0)).equals(
				value(cxt, result("result/context", outPaths("arg/x1", "result/y")))));
	}

	@Test
	public void exertOpTask() throws Exception  {

		Task t5 = task(sig(Adder.class), op("add", Strategy.Access.PULL),
				cxt("add", inVal("arg/x1", 20.0), inVal("arg/x2", 80.0)));

		Exertion out = exert(t5);
		Context cxt = context(out);
		logger.info("out context: " + cxt);
		logger.info("context @ arg/x1: " + get(cxt, "arg/x1"));
		logger.info("context @ arg/x2: " + value(cxt, "arg/x2"));
		logger.info("context @ result/eval: " + value(cxt, "result/eval"));

		// get a single context argument
		assertEquals(100.0, value(cxt, "result/eval"));

		// get the subcontext output from the context
		assertTrue(context(operator.ent("result/eval", 100.0), operator.ent("arg/x1", 20.0)).equals(
				value(cxt, outPaths("result/eval", "arg/x1"))));

	}

	@Test
	public void exertTaskSrvName() throws Exception  {

		Task t5 = task("t5", sig("add", Adder.class, srvName("Adder")),
				cxt("add", inVal("arg/x1", 20.0), inVal("arg/x2", 80.0), result("result/y")));

		Exertion out = exert(t5);
		Context cxt = context(out);
		logger.info("out context: " + cxt);
		logger.info("context @ arg/x1: " + value(cxt, "arg/x1"));
		logger.info("context @ arg/x2: " + value(cxt, "arg/x2"));
		logger.info("context @ result/y: " + value(cxt, "result/y"));

		// get a single context argument
		assertEquals(100.0, value(cxt, "result/y"));

		// get the subcontext output from the context
		assertTrue(context(operator.ent("arg/x1", 20.0), operator.ent("result/y", 100.0)).equals(
				value(cxt, result("result/context", outPaths("arg/x1", "result/y")))));
	}

	@Test
	public void exertTaskGroups() throws Exception  {
		String group = System.getProperty("user.name");

		Task t5 = task("t5", sig("add", Adder.class, srvName("Adder", group)),
				cxt("add", inVal("arg/x1", 20.0), inVal("arg/x2", 80.0), result("result/y")));

		Exertion out = exert(t5);
		Context cxt = context(out);
		logger.info("out context: " + cxt);
		logger.info("context @ arg/x1: " + value(cxt, "arg/x1"));
		logger.info("context @ arg/x2: " + value(cxt, "arg/x2"));
		logger.info("context @ result/y: " + value(cxt, "result/y"));

		// get a single context argument
		assertEquals(100.0, value(cxt, "result/y"));

		// get the subcontext output from the context
		assertTrue(context(operator.ent("arg/x1", 20.0), operator.ent("result/y", 100.0)).equals(
				value(cxt, result("result/context", outPaths("arg/x1", "result/y")))));
	}

	@Test
	public void exertTaskMatchTypes() throws Exception  {
		String group = System.getProperty("user.name");

		Task t5 = task("t5", sig("add", Adder.class,
				types(Service.class, Provider.class),
				srvName("Adder", group)),
				cxt("add", inVal("arg/x1", 20.0), inVal("arg/x2", 80.0), result("result/y")));

		Exertion out = exert(t5);
		Context cxt = context(out);
		logger.info("out context: " + cxt);
		logger.info("context @ arg/x1: " + value(cxt, "arg/x1"));
		logger.info("context @ arg/x2: " + value(cxt, "arg/x2"));
		logger.info("context @ result/y: " + value(cxt, "result/y"));

		// get a single context argument
		assertEquals(100.0, value(cxt, "result/y"));

		// get the subcontext output from the context
		assertTrue(context(operator.ent("arg/x1", 20.0), operator.ent("result/y", 100.0)).equals(
				value(cxt, result("result/context", outPaths("arg/x1", "result/y")))));
	}

	@Test
	public void exertTaskLookupLocators() throws Exception  {
		String group = System.getProperty("user.name");

		Task t5 = task("t5", sig("add", Adder.class,
				types(Service.class, Provider.class),
				// comma separated list of hosts, when empty localhost is a default locator
				srvName("Adder", locators(), group)),
				cxt("add", inVal("arg/x1", 20.0), inVal("arg/x2", 80.0), result("result/y")));

		Exertion out = exert(t5);
		Context cxt = context(out);
		logger.info("out context: " + cxt);
		logger.info("context @ arg/x1: " + value(cxt, "arg/x1"));
		logger.info("context @ arg/x2: " + value(cxt, "arg/x2"));
		logger.info("context @ result/y: " + value(cxt, "result/y"));

		// get a single context argument
		assertEquals(100.0, value(cxt, "result/y"));

		// get the subcontext output from the context
		assertTrue(context(operator.ent("arg/x1", 20.0), operator.ent("result/y", 100.0)).equals(
				value(cxt, result("result/context", outPaths("arg/x1", "result/y")))));
	}

	@Test
	public void evaluateTask() throws SignatureException, ExertionException, ContextException  {

		Task t5 = task("t5", sig("add", Adder.class),
				cxt("add", inVal("arg/x1", 20.0), inVal("arg/x2", 80.0), result("result/y")));

		// get the result eval
		assertTrue(eval(t5).equals(100.0));

		// get the subcontext output from the exertion
		assertTrue(context(operator.ent("arg/x1", 20.0), operator.ent("result/z", 100.0)).equals(
				eval(t5, result("result/z", outPaths("arg/x1", "result/z")))));

	}

	@Test
	public void evaluateAverager() throws Exception {

		Task t5 = task(
				"t6",
				sig("average", Averager.class),
				context("average", inVal("arg, x1", 20.0),
						inVal("arg, x2", 80.0), result("result/y")));
		t5 = exert(t5);
		logger.info("t6 context: " + context(t5));
		assertEquals(value(context(t5), "result/y"), 50.0);

	}

	@Test
	public void arithmeticNetFiTask() throws Exception {

		Task task = task("add",
				sFi("net", sig("add", Adder.class)),
				sFi("object", sig("add", AdderImpl.class)),
				context(inVal("arg/x1", 20.0), inVal("arg/x2", 80.0),
						result("result/y")));

		logger.info("sFi: " + fi(task));
		logger.info("sFis: " + size(srvFis(task)));

//		task = exert(task, fi("object"));
//		logger.info("exerted: " + task);
//		assertTrue((Double)get(task) == 100.0);

		task = exert(task, fi("net"));
		logger.info("exerted: " + task);
		assertTrue("Wrong eval for 100.0", (Double) returnValue(task) == 100.0);
	}

	@Test
	public void spaceTask() throws Exception {

		Task t5 = task("t5",
				sig("add", Adder.class),
				context("add", inVal("arg/x1", 20.0),
						inVal("arg/x2", 80.0), outVal("result/y")),
				strategy(Access.PULL, Wait.YES));

		t5 = exert(t5);
		logger.info("t5 context: " + context(t5));
		logger.info("t5 eval: " + get(t5, "result/y"));
		assertEquals("Wrong eval for 100.0", get(t5, "result/y"), 100.0);

	}

	
	@Test
	public void serviceShellTest() throws Exception {

		// The signature as a service provider
		Task f5 = task("f5",
				sig("add", Adder.class),
				context("add", inVal("arg/x1", 20.0),
						inVal("arg/x2", 80.0), result("result/y")),
				strategy(Monitor.NO, Wait.YES));

		Context out = (Context) exec(sig(RemoteServiceShell.class), f5);
		assertTrue(value(out, "result/y").equals(100.00));

	}

	@Test
	public void batchTask() throws Exception {
		// batch for the composition f1(f2(f3((x1, x2), f4(x1, x2)), f5(x1, x2))
		// shared context with named paths
		Task batch3 = task("batch3",
				type(sig("multiply", Multiplier.class, result("subtract/x1", Signature.Direction.IN)), Signature.PRE),
				type(sig("add", Adder.class, result("subtract/x2", Signature.Direction.IN)), Signature.PRE),
				sig("subtract", Subtractor.class, result("result/y", inPaths("subtract/x1", "subtract/x2"))),
				context(inVal("multiply/x1", 10.0), inVal("multiply/x2", 50.0),
						inVal("add/x1", 20.0), inVal("add/x2", 80.0)));

		batch3 = exert(batch3);
		//logger.info("task result/y: " + get(batch3, "result/y"));
		assertEquals(get(batch3, "result/y"), 400.0);
	}

	@Test
	public void multiFiTask() throws Exception {

		Task t4 = task("t4",
				sFi("net1", sig("multiply", Multiplier.class)),
				sFi("net2", sig("add", Adder.class)),
				context("shared", inVal("arg/x1", 10.0), inVal("arg/x2", 50.0),
						result("result/y")));

		Context out = context(exert(t4, fi("net1")));
		logger.info("task context: " + context(t4));
		assertTrue(get(out, "result/y").equals(500.0));

		out = context(exert(t4, fi("net2")));
		logger.info("task context: " + context(t4));
		assertTrue(get(out, "result/y").equals(60.0));
	}

	@Test
	public void batchFiTask() throws Exception {

		Task t4 = task("t4",
				sFi("object", sig("multiply", MultiplierImpl.class), sig("add", AdderImpl.class)),
				sFi("net", sig("multiply", Multiplier.class), sig("add", Adder.class)),
				context("shared", inVal("arg/x1", 10.0), inVal("arg/x2", 50.0),
						outVal("result/y")));

		Context out = context(exert(t4, fi("net")));
		logger.info("task context: " + context(t4));
		assertTrue(get(out, "result/y").equals(500.0));
	}

	@Test
	public void multiFiObjectTaskTest() throws Exception {
		ServiceExertion.debug = true;

		Task task = task("add",
				sFi("object", sig("add", Adder.class)),
				sFi("net", sig("add", AdderImpl.class)),
				context(inVal("arg/x1", 20.0), inVal("arg/x2", 80.0),
						result("result/y")));

		logger.info("task fi: " + fi(task));
		assertTrue(fis(task).size() == 2);
		logger.info("selected Fi: " + fiName(task));
		assertTrue(fiName(task).equals("object"));

		task = exert(task, fi("net"));
		logger.info("exerted: " + context(task));
		assertTrue(fiName(task).equals("net"));
		assertTrue(returnValue(task).equals(100.0));
	}


	@Test
	public void netContexterTaskTest() throws Exception {

		Task t5 = task("t5", sig("add", Adder.class),
				sig("getContext", Contexter.class, prvName("Add Contexter"), Signature.APD),
				context("add", inVal("arg/x1"), inVal("arg/x2"),
						result("result/y")));

		Context result =  context(exert(t5));
		logger.info("out context: " + result);
		assertEquals(value(result, "arg/x1"), 20.0);
		assertEquals(value(result, "arg/x2"), 80.0);
		assertEquals(value(result, "result/y"), 100.0);

	}

}
	
	
