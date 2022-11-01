package atm.ATM.statechans.A;

import atm.ATM.ops.*;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.statechans.A.ioifaces.*;

public interface ATM_A_2_Handler extends Handle_A_C_Deposit__C_Quit__C_Transfer<ATM_A_3, EndSocket, ATM_A_5> {

	@Override
	abstract public void receive(EndSocket schan, Quit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;

	@Override
	abstract public void receive(ATM_A_3 schan, Deposit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;

	@Override
	abstract public void receive(ATM_A_5 schan, Transfer op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;
}