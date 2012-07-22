package ioio.examples.hello_console;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOConnectionManager.Thread;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.pc.IOIOConsoleApp;

public class HelloIOIOConsole extends IOIOConsoleApp {
	private boolean ledOn_ = false;

	// Boilerplate main(). Copy-paste this code into any IOIOapplication.
	public static void main(String[] args) throws Exception {
		new HelloIOIOConsole().go(args);
	}

	@Override
	protected void run(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		boolean abort = false;
		String line;
		while (!abort && (line = reader.readLine()) != null) {
			switch (line) {
			case "t":
				ledOn_ = !ledOn_;
				break;
			case "n":
				ledOn_ = true;
				break;
			case "f":
				ledOn_ = false;
				break;
			case "q":
				abort = true;
				break;
			default:
				System.out
						.println("Unknown input. t=toggle, n=on, f=off, q=quit.");
			}
		}
	}

	@Override
	public IOIOLooper createIOIOLooper(String connectionType, Object extra) {
		return new BaseIOIOLooper() {
			private DigitalOutput led_;

			@Override
			protected void setup() throws ConnectionLostException,
					InterruptedException {
				led_ = ioio_.openDigitalOutput(IOIO.LED_PIN, true);
			}

			@Override
			public void loop() throws ConnectionLostException,
					InterruptedException {
				led_.write(!ledOn_);
				Thread.sleep(10);
			}
		};
	}
}