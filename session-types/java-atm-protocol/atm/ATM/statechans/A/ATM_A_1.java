package atm.ATM.statechans.A;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;
import atm.ATM.statechans.A.ioifaces.*;

public final class ATM_A_1 extends org.scribble.runtime.statechans.OutputSocket<ATM, A> implements Select_A_C_SendName_String<ATM_A_2> {
	public static final ATM_A_1 cast = null;

	protected ATM_A_1(org.scribble.runtime.session.SessionEndpoint<ATM, A> se, boolean dummy) {
		super(se);
	}

	public ATM_A_1(org.scribble.runtime.session.MPSTEndpoint<ATM, A> se) throws org.scribble.main.ScribRuntimeException {
		super(se);
		se.init();
	}

	public ATM_A_2 send(C role, SendName op, java.lang.String arg0) throws org.scribble.main.ScribRuntimeException, IOException {
		super.writeScribMessage(role, ATM.SendName, arg0);

		return new ATM_A_2(this.se, true);
	}
}