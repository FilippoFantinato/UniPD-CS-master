package atm.ATM.statechans.A;

import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;
import atm.ATM.statechans.A.ioifaces.*;

public final class ATM_A_2_Cases extends org.scribble.runtime.statechans.CaseSocket<ATM, A> implements Case_A_C_Deposit__C_Quit__C_Transfer<ATM_A_3, EndSocket, ATM_A_5> {
	public static final ATM_A_2_Cases cast = null;
	public final ATM_A_2.Branch_A_C_Deposit__C_Quit__C_Transfer_Enum op;
	private final org.scribble.runtime.message.ScribMessage m;

	protected ATM_A_2_Cases(org.scribble.runtime.session.SessionEndpoint<ATM, A> se, boolean dummy, ATM_A_2.Branch_A_C_Deposit__C_Quit__C_Transfer_Enum op, org.scribble.runtime.message.ScribMessage m) {
		super(se);
		this.op = op;
		this.m = m;
	}

	public atm.ATM.statechans.A.EndSocket receive(C role, Quit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(ATM.Quit)) {
			throw new org.scribble.main.ScribRuntimeException("Wrong branch, received: " + this.m.op);
		}
		this.se.setCompleted();
		return new EndSocket(this.se, true);
	}

	public atm.ATM.statechans.A.EndSocket receive(Quit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(ATM.C, op);
	}

	public ATM_A_3 receive(C role, Deposit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(ATM.Deposit)) {
			throw new org.scribble.main.ScribRuntimeException("Wrong branch, received: " + this.m.op);
		}
		return new ATM_A_3(this.se, true);
	}

	public ATM_A_3 receive(Deposit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(ATM.C, op);
	}

	public ATM_A_5 receive(C role, Transfer op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(ATM.Transfer)) {
			throw new org.scribble.main.ScribRuntimeException("Wrong branch, received: " + this.m.op);
		}
		return new ATM_A_5(this.se, true);
	}

	public ATM_A_5 receive(Transfer op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(ATM.C, op);
	}

	@Override
	public Branch_A_C_Deposit__C_Quit__C_Transfer.Branch_A_C_Deposit__C_Quit__C_Transfer_Enum getOp() {
		return this.op;
	}
}