package atm.ATM.statechans.A;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.statechans.A.ioifaces.*;

public final class ATM_A_2 extends org.scribble.runtime.statechans.BranchSocket<ATM, A> implements Branch_A_C_Deposit__C_Quit__C_Transfer<ATM_A_3, EndSocket, ATM_A_5> {
	public static final ATM_A_2 cast = null;

	protected ATM_A_2(org.scribble.runtime.session.SessionEndpoint<ATM, A> se, boolean dummy) {
		super(se);
	}

	@Override
	public ATM_A_2_Cases branch(C role) throws org.scribble.main.ScribRuntimeException, IOException, ClassNotFoundException {
		org.scribble.runtime.message.ScribMessage m = super.readScribMessage(ATM.C);
		Branch_A_C_Deposit__C_Quit__C_Transfer_Enum openum;
		if (m.op.equals(ATM.Quit)) {
			openum = Branch_A_C_Deposit__C_Quit__C_Transfer_Enum.Quit;
		}
		else if (m.op.equals(ATM.Deposit)) {
			openum = Branch_A_C_Deposit__C_Quit__C_Transfer_Enum.Deposit;
		}
		else if (m.op.equals(ATM.Transfer)) {
			openum = Branch_A_C_Deposit__C_Quit__C_Transfer_Enum.Transfer;
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
		return new ATM_A_2_Cases(this.se, true, openum, m);
	}

	public void branch(C role, ATM_A_2_Handler handler) throws org.scribble.main.ScribRuntimeException, IOException, ClassNotFoundException {
		branch(role, (Handle_A_C_Deposit__C_Quit__C_Transfer<ATM_A_3, EndSocket, ATM_A_5>) handler);
	}

	@Override
	public void branch(C role, Handle_A_C_Deposit__C_Quit__C_Transfer<ATM_A_3, EndSocket, ATM_A_5> handler) throws org.scribble.main.ScribRuntimeException, IOException, ClassNotFoundException {
		org.scribble.runtime.message.ScribMessage m = super.readScribMessage(ATM.C);
		if (m.op.equals(ATM.Quit)) {
			this.se.setCompleted();
			handler.receive(new EndSocket(this.se, true), ATM.Quit);
		}
		else
		if (m.op.equals(ATM.Deposit)) {
			handler.receive(new ATM_A_3(this.se, true), ATM.Deposit);
		}
		else
		if (m.op.equals(ATM.Transfer)) {
			handler.receive(new ATM_A_5(this.se, true), ATM.Transfer);
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
	}

	@Override
	public void handle(C role, Handle_A_C_Deposit__C_Quit__C_Transfer<Succ_In_C_Deposit, Succ_In_C_Quit, Succ_In_C_Transfer> handler) throws org.scribble.main.ScribRuntimeException, IOException, ClassNotFoundException {
		org.scribble.runtime.message.ScribMessage m = super.readScribMessage(ATM.C);
		if (m.op.equals(ATM.Quit)) {
			this.se.setCompleted();
			handler.receive(new EndSocket(this.se, true), ATM.Quit);
		}
		else
		if (m.op.equals(ATM.Deposit)) {
			handler.receive(new ATM_A_3(this.se, true), ATM.Deposit);
		}
		else
		if (m.op.equals(ATM.Transfer)) {
			handler.receive(new ATM_A_5(this.se, true), ATM.Transfer);
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
	}
}