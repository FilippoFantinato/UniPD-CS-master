/*********************************************
 * OPL Model
 *********************************************/

execute{
  var now = new Date();
    Opl.srand(now.getTime()%Math.pow(2,31));
}

int Nop = 500;
int Nroom = ftoi(round(Nop/3.3));
setof(int) I = asSet(1..Nop);
setof(int) J = asSet(1..Nroom);

int 	w[i in I] = 3 + rand(7);
int 	W[j in J] = 6 + rand(20);
int		compatibility[i in I][j in J] = rand(2);

dvar boolean x[i in I,j in J];

maximize sum (i in I, j in J) x[i][j];

subject to {
forall ( j in J ) {
	request: sum ( i in I ) w[i] * x[i][j] <= W[j];
}

forall ( i in I ) {
	capacity: sum ( j in J ) x[i][j] <= 1;
}

forall ( i in I, j in J : compatibility[i][j] == 0 ) {
	x[i][j] == 0;
}

}





 
 
