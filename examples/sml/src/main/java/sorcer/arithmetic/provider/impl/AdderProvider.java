package sorcer.arithmetic.provider.impl;

import com.sun.jini.start.LifeCycle;
import net.jini.lookup.entry.UIDescriptor;
import net.jini.lookup.ui.MainUI;
import sorcer.arithmetic.provider.RemoteAdder;
import sorcer.arithmetic.provider.ui.CalculatorUI;
import sorcer.core.provider.ServiceTasker;
import sorcer.service.Context;
import sorcer.service.ContextException;
import sorcer.service.MonitorException;
import sorcer.serviceui.UIComponentFactory;
import sorcer.serviceui.UIDescriptorFactory;
import sorcer.util.Sorcer;

import java.net.URL;
import java.rmi.RemoteException;

import static sorcer.eo.operator.value;

public class AdderProvider extends ServiceTasker implements RemoteAdder {
	private Arithmometer arithmometer = new Arithmometer();
	
	public AdderProvider(String[] args, LifeCycle lifeCycle) throws Exception {
		super(args, lifeCycle);
	}
	
	public Context add(Context context) throws RemoteException,
			ContextException, MonitorException {
		Context out = arithmometer.add(context);		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		out.checkpoint();

//		Logger remoteLogger =  provider.getRemoteLogger();
//		remoteLogger.info("remote logging; add result: " + out);
		
		return out;
	}

	@Override
	public Context add2(Context context) throws RemoteException, ContextException, MonitorException {
		Context out = arithmometer.add(context);
		out.putValue("result/eval", (double)value(out, "result/eval") + 100.0);
		return out;
	}

	public static UIDescriptor getCalculatorDescriptor() {
		UIDescriptor uiDesc = null;
		try {
			uiDesc = UIDescriptorFactory.getUIDescriptor(MainUI.ROLE,
					new UIComponentFactory(new URL[] { new URL(Sorcer
							.getWebsterUrl()
							+ "/calculator-ui.jar") }, CalculatorUI.class
							.getName()));
		} catch (Exception ex) {
			// do nothing
		}
		return uiDesc;
	}
}
