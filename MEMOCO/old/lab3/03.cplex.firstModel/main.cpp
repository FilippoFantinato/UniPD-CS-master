/**
 * @file first.cpp
 * @brief basic use of newcols and addrow
 * to solve the model
 *  max 2 x1 + 3 x2 + w
 *    x1 + x2 <= 5 z ---> 		x1 + x2 - 5 z <= 0									
 *    x2 + 9 y1 + 9 y2 + 8w = 2
 *    8 y1 >= -1									
 *    -4 y1 + 7z + 5w <= 9				
 *    x1,x2 >=0
 *    y1 <=0
 *    z in {0,1}
 *    w in Z+					
 */

#include <cstdio>
#include <iostream>
#include <vector>
#include <string>
#include "cpxmacro.h"

using namespace std;


// error status and messagge buffer
int status;
char errmsg[BUF_SIZE];

int main (int argc, char const *argv[])
{
	try
	{
	///////////////////////// init env and lp prob
	//
	//	  CPXENVptr CPXopenCPLEX   ( status_out )
	//	  CPXLPptr  CPXcreateprob  ( env, status_out, probname )
	//
	///TODO...
	DECL_ENV(env)
	DECL_PROB(env,lp)

		
	///////////////////////// create variables with newcols
	//
	//    status = CPXnewcols (env, lp, ccnt, obj, lb, ub, xctype, colname)
	//
	///TODO...
	int numvar = 6;
	// set an order			x1, x2, y1, y2, z, w
	double objCoef[6] = {2.0, 3.0, 0.0, 0.0, 0.0, 1.0};
	double lb[6] 			= {0.0, 0.0, -CPX_INFBOUND, -CPX_INFBOUND, 0.0, 0.0};
	double ub[6] 			= {CPX_INFBOUND, CPX_INFBOUND, 0.0, CPX_INFBOUND, 1.0, CPX_INFBOUND};
	char xtype[6]			= {'C', 'C', 'C', 'C', 'B', 'I'};
	char **varname		= NULL;
	CHECKED_CPX_CALL(CPXnewcols, env, lp, numvar, objCoef, lb, ub, xtype, varname);



    
  ///////////////////////// create constraints
  //
  //    status = CPXaddrows (env, lp, colcnt, rowcnt, nzcnt, rhs, sense, rmatbeg, rmatind, rmatval , newcolname, newrowname)
  //
  ///TODO...
	int colcount = 0;
	int rowcount = 4;
	int nonzeros = 11;
	double rhs[4] = {0, 2, -1, 9};
	char sense[4] = {'L', 'E', 'G', 'L'};
	int rmatbeg[4] = {0, 3, 7, 8};
	int rmatind[11] = {0, 1, 4, 1, 2, 3, 5, 2, 2, 4, 5};
	double rmatval[11] = {1.0, 1.0,-1.0,1,9,9,8,8,-4,7,5};
	char **ncn = NULL;
	char **nrn = NULL;
	CHECKED_CPX_CALL(CPXaddrows, env, lp, colcount, rowcount, nonzeros, rhs, sense, rmatbeg, rmatind, rmatval, ncn, nrn);


	///////////////////////// set optimization sense
	//
	//    status = CPXchgobjsen (env, lp, CPX_MAX/CPX_MIN);
	//
	///TODO...
	CHECKED_CPX_CALL(CPXchgobjsen, env, lp, CPX_MAX);
	
	
	///////////////////////// print problem (debug)
	//
	//    status = CPXwriteprob (env, lp, filename, fileformat)
	//
	///TODO...
	CHECKED_CPX_CALL(CPXwriteprob, env, lp, "first.lp", NULL);

	

	///////////////////////// optimize!
	//
	//    status = CPXmipopt (env, lp);
	//
	///TODO...
	CHECKED_CPX_CALL(CPXmipopt, env, lp);

	cout << "Finish" << endl;


    ///////////////////////// print solution (objective function and variables
	//
	//    status = CPXgetobjval (env, lp, obj_func_value_out )
	//	  int CPXgetnumcols(env, lp)
	//    status = CPXgetx (env, lp, varVals_out, fromIdx, toIdx)
	//
	///TODO...
	double objval;
	CHECKED_CPX_CALL(CPXgetobjval, env, lp, &objval);
	cout << "OBJVal: " << objval << endl;
	vector<double> varvals;
	int n = CPXgetnumcols(env, lp);
	varvals.resize(n);
	int fromIdx = 0;
	int toIdx = n-1;

	CHECKED_CPX_CALL(CPXgetx, env, lp, &varvals[0], fromIdx, toIdx);

	for(int i = 0; i < n; ++i)
	{
		cout << "Var in position "<< i << ": " << varvals[i] << endl;
	}


	///////////////////////// export solution
	//
	//    status = CPXsolwrite (env, lp, filename);
	//
	///TODO...
	CHECKED_CPX_CALL(CPXsolwrite, env, lp, "first.sol");

		
	///////////////////////// close and free problem and env
	//
	//    CPXfreeprob (env, problem)    -     CPXcloseCPLEX (env)
	//
	///TODO...
	CPXfreeprob(env, &lp);
	CPXcloseCPLEX(&env);

	}
	catch(std::exception& e)
	{
		std::cout << ">>>EXCEPTION: " << e.what() << std::endl;
	}
	return 0;
}
