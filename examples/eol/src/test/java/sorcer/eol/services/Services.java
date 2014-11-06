package sorcer.eol.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.arithmetic.provider.impl.AdderImpl;
import sorcer.arithmetic.provider.impl.MultiplierImpl;
import sorcer.arithmetic.provider.impl.SubtractorImpl;
import sorcer.core.provider.rendezvous.ServiceJobber;
import sorcer.service.*;
import sorcer.service.Strategy.Access;
import sorcer.service.Strategy.Flow;
import sorcer.util.Sorcer;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static sorcer.co.operator.*;
import static sorcer.eo.operator.*;
import static sorcer.eo.operator.input;
import static sorcer.eo.operator.value;

/**
 * @author Mike Sobolewski
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/eol")
public class Services {
	private final static Logger logger = Logger.getLogger(Services.class.getName());

/*	static {
		String version = "5.0.0-SNAPSHOT";
		System.setProperty("java.util.logging.config.file",
				Sorcer.getHome() + "/configs/sorcer.logging");
		System.setProperty("java.security.policy", Sorcer.getHome()
				+ "/configs/policy.all");
		System.setSecurityManager(new SecurityManager());
		Sorcer.setCodeBase(new String[] { "arithmetic-" + version + "-dl.jar",  "sorcer-dl-"+version +".jar" });
		
		System.setProperty("java.protocol.handler.pkgs", "sorcer.util.url|org.rioproject.url");
		System.setProperty("java.rmi.server.RMIClassLoaderSpi","org.rioproject.rmi.ResolvingLoader");
	}*/
	
	
	@Test
	public void exertTask() throws Exception  {

		Service t5 = service("t5", sig("add", AdderImpl.class),
				cxt("add", inEnt("arg/x1", 20.0), inEnt("arg/x2", 80.0),
						outEnt("result/y")));

		Service out = exert(t5);
		Context cxt = context(out);
		logger.info("out context: " + cxt);
		logger.info("context @ arg/x1: " + value(cxt, "arg/x1"));
		logger.info("context @ arg/x2: " + value(cxt, "arg/x2"));
		logger.info("context @ result/y: " + value(cxt, "result/y"));
		
		assertEquals(100.0, value(cxt, "result/y"));

	}
	
	
	public void evaluateTask() throws SignatureException, ExertionException, ContextException  {

		Service t5 = service("t5", sig("add", AdderImpl.class),
				cxt("add", inEnt("arg/x1", 20.0), inEnt("arg/x2", 80.0),
						outEnt("result/y")));

		Object out = value(t5);
		logger.info("out value: " + out);
		assertEquals(100.0, out);
		
	}

	
	@Test
	public void exertJob() throws Exception {

		Service t3 = srv("t3", sig("subtract", SubtractorImpl.class),
				cxt("subtract", inEnt("arg/x1"), inEnt("arg/x2"), outEnt("result/y")));

		Service t4 = srv("t4", sig("multiply", MultiplierImpl.class),
				// cxt("multiply", in("super/arg/x1"), in("arg/x2", 50.0),
				cxt("multiply", inEnt("arg/x1", 10.0), inEnt("arg/x2", 50.0),
						outEnt("result/y")));

		Service t5 = srv("t5", sig("add", AdderImpl.class),
				cxt("add", inEnt("arg/x1", 20.0), inEnt("arg/x2", 80.0),
						outEnt("result/y")));

		Service job = //j1(j2(t4(x1, x2), t5(x1, x2)), t3(x1, x2))
				srv("j1", sig(ServiceJobber.class),
					cxt(inEnt("arg/x1", 10.0),
					result("job/result", from("j1/t3/result/y"))),
				srv("j2", sig(ServiceJobber.class), t4, t5), 
				t3,
				pipe(output(t4, "result/y"), input(t3, "arg/x1")),
				pipe(output(t5, "result/y"), input(t3, "arg/x2")));

		logger.info("srv job context: " + serviceContext(job));
		logger.info("srv j1/t3 context: " + context(job, "j1/t3"));
		logger.info("srv j1/j2/t4 context: " + context(job, "j1/j2/t4"));
		logger.info("srv j1/j2/t5 context: " + context(job, "j1/j2/t5"));

		Service exertion = exert(job);
		logger.info("srv job context: " + serviceContext(exertion));
		logger.info("exertion value @ j1/t3/arg/x2 = " + get(exertion, "j1/t3/arg/x2"));
//		assertEquals(100.0, get(exertion, "j1/t3/arg/x2"));
		
	}
	
	
	@Test
	public void evaluateJob() throws Exception {

		Service t3 = srv("t3", sig("subtract", SubtractorImpl.class),
				cxt("subtract", inEnt("arg/x1"), inEnt("arg/x2"), result("result/y")));

		Service t4 = srv("t4", sig("multiply", MultiplierImpl.class),
				cxt("multiply", inEnt("arg/x1", 10.0), inEnt("arg/x2", 50.0), result("result/y")));

		Service t5 = srv("t5", sig("add", AdderImpl.class),
				cxt("add", inEnt("arg/x1", 20.0), inEnt("arg/x2", 80.0), result("result/y")));

		Service job = //j1(j2(t4(x1, x2), t5(x1, x2)), t3(x1, x2))
				srv("j1", sig(ServiceJobber.class), result("job/result", from("j1/t3/result/y")),
				srv("j2", sig(ServiceJobber.class), t4, t5, strategy(Flow.PAR, Access.PULL)), 
				t3,
				pipe(out(t4, "result/y"), in(t3, "arg/x1")),
				pipe(out(t5, "result/y"), in(t3, "arg/x2")));

		Object out = value(job);
		logger.info("srv job value: " + out);
//		assertEquals(400.0, out);
		
	}
}
	
	