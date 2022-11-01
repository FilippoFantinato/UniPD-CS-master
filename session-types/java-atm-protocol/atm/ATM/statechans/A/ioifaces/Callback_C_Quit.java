package atm.ATM.statechans.A.ioifaces;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;

public interface Callback_C_Quit<__Succ extends Succ_In_C_Quit> {

	abstract public void receive(__Succ schan, Quit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;
}