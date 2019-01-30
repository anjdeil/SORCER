package sorcer.arithmetic.requestor;

import sorcer.arithmetic.provider.Adder;
import sorcer.arithmetic.provider.Averager;
import sorcer.arithmetic.provider.Multiplier;
import sorcer.arithmetic.provider.Subtractor;
import sorcer.arithmetic.provider.impl.AdderImpl;
import sorcer.arithmetic.provider.impl.AveragerImpl;
import sorcer.arithmetic.provider.impl.MultiplierImpl;
import sorcer.arithmetic.provider.impl.SubtractorImpl;
import sorcer.co.operator;
import sorcer.core.provider.Jobber;
import sorcer.core.provider.rendezvous.ServiceJobber;
import sorcer.core.requestor.SorcerRequestor;
import sorcer.service.*;

import static sorcer.co.operator.*;
import static sorcer.eo.operator.*;

public class ArithmeticSorcerRequestor extends SorcerRequestor {

    public Exertion getExertion(String... args) throws ExertionException, ContextException, SignatureException {

        Task t3 = task("t3", sFi("object/subtract", sig("subtract", SubtractorImpl.class)),
                sFi("object/average", sig("average", AveragerImpl.class)),
                sFi("net/subtract", sig("subtract", Subtractor.class)),
                sFi("net/average", sig("average", Averager.class)),
                context("t3-cxt", operator.inVal("arg/x1"), operator.inVal("arg/x2"),
                        outVal("result/y")));

        Task t4 = task("t4", sFi("object", sig("multiply", MultiplierImpl.class)),
                sFi("net", sig("multiply", Multiplier.class)),
                context("multiply", operator.inVal("arg/x1", 10.0), operator.inVal("arg/x2", 50.0),
                        outVal("result/y")));

        Task t5 = task("t5", sFi("object", sig("add", AdderImpl.class)),
                sFi("net", sig("add", Adder.class)),
                context("add", operator.inVal("arg/x1", 20.0), operator.inVal("arg/x2", 80.0),
                        outVal("result/y")));

        Job job = job("j1", sFi("object", sig("exert", ServiceJobber.class)),
                sFi("net", sig("exert", Jobber.class)),
                job("j2", sig("exert", ServiceJobber.class), t4, t5),
                t3,
                pipe(outPoint(t4, "result/y"), inPoint(t3, "arg/x1")),
                pipe(outPoint(t5, "result/y"), inPoint(t3, "arg/x2")),
                fi("job1", fi("net", "j1"), fi("net", "j1/j2/t4")),
                fi("job2", fi("net", "j1"), fi("net", "j1/j2/t4"), fi("net", "j1/j2/t5")));

        return job;

    }

    @Override
    public void run(String... args) throws Exception {
        Exertion exertion = exert(getExertion());
        logger.info("<<<<<<<<<< f5 context: \n" + upcontext(exertion));

    }
}