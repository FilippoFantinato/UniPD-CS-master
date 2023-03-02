/**
 * @file ironrods.cpp
 * @brief 
 */

#include <cstdio>
#include <iostream>
#include <vector>
#include "cpxmacro.h"

using namespace std;

// error status and messagge buffer
int status;
char errmsg[BUF_SIZE];

// data
const int I = 3;
const int J = 3;
const char nameI[I] = { 'A', 'B', 'C' }; // origins
const char nameJ[J] = { '1', '2', '3' }; // destinations
const double D[I] = { 7000.0, 6000.0, 4000.0 }; // Availability (origins)
const double R[J] = { 8000.0, 5000.0, 4000.0 }; // Demand (destinations)
const double C[I*J] = {	9.0, 6.0, 5.0,
                        7.0, 4.0, 9.0,
                        4.0, 6.0, 3.0 }; // costs(origin, destination) LINEARIZED (just an implementation choice!)
const double K = 10000.0;
const double F = 50.0;
const double N = 4.0;
const double L = 65.0;
			
const int NAME_SIZE = 512;
char name[NAME_SIZE];
	
void setupLP(CEnv env, Prob lp)
{
	int current_var_position = 0;

	/*MAP FOR X VARS: declare map*/
	vector<vector<int> > map_x;	// x_ij ---> map_x[i][j]	
	/*MAP FOR X VARS: initial memory allocation for map vector*/
	map_x.resize(I);
	for ( int i = 0 ; i < I ; ++i ) {
		map_x[i].resize(J);
		for ( int j = 0 ; j < J ; ++j ) {
			map_x[i][j] = -1;
		}
	} 
 
	// add x vars [in o.f.: sum{i,j} C_ij x_ij + ...]
	for (int i = 0; i < I; i++)
	{
		for (int j = 0; j < J; j++)
		{
			char xtype = 'I';
			double lb = 0.0;
			double ub = CPX_INFBOUND;
			snprintf(name, NAME_SIZE, "x_%c_%c", nameI[i], nameJ[j]);
			char* xname = (char*)(&name[0]);
			CHECKED_CPX_CALL( CPXnewcols, env, lp, 1   , &C[i*J+j], &lb, &ub, &xtype, &xname );
			/// status =      CPXnewcols (env, lp, ccnt, obj      , lb  , ub, xctype, colname);
			
			map_x[i][j] = current_var_position ++;
		}
	}
	
	/*MAP FOR Y VARS: declare map*/
	vector<vector<int> > map_y;	// x_ij ---> map_y[i][j]	
	/*MAP FOR X VARS: initial memory allocation for map vector*/
	map_y.resize(I);
	for ( int i = 0 ; i < I ; ++i ) {
		map_y[i].resize(J);
		for ( int j = 0 ; j < J ; ++j ) {
			map_y[i][j] = -1;
		}
	} 
	
	// add y vars [in o.f.: ... + F sum{ij} y_ij + ... ] ///TODO-done
	for (int i = 0; i < I; i++)
	{
		for (int j = 0; j < J; j++)
		{
			char ytype = 'B';
			double lb = 0.0;
			double ub = 1.0;
			snprintf(name, NAME_SIZE, "y_%c_%c", nameI[i], nameJ[j]);
			char* yname = (char*)(&name[0]);
			CHECKED_CPX_CALL( CPXnewcols, env, lp, 1, &F, &lb, &ub, &ytype, &yname );
			
			map_y[i][j] = current_var_position ++;
		}
	}
	
	int map_z;
	
	// add z var [in o.f.: ... + (L-F) z ] ///TODO-done
	{
		char ztype = 'B';
		double lb = 0.0;
		double ub = 1.0;
		double obj = L - F;
		snprintf(name, NAME_SIZE, "z");
		char* zname = (char*)(&name[0]);
		CHECKED_CPX_CALL( CPXnewcols, env, lp, 1, &obj, &lb, &ub, &ztype, &zname );
		
		map_z = current_var_position ++;
	}


	// add request constraints (destinations) [ forall j, sum{i} x_ij >= R_j ]
	for (int j = 0; j < J; j++)
	{
		std::vector<int> idx(I);
		std::vector<double> coef(I, 1.0);
		char sense = 'G';
		for (int i = 0; i < I; i++)
		{
			idx[i] = map_x[i][j]; //i*J + j; // corresponds to variable x_ij
		}
		int matbeg = 0;
		CHECKED_CPX_CALL( CPXaddrows, env, lp, 0     , 1     , idx.size(), &R[j], &sense, &matbeg, &idx[0], &coef[0], NULL      , NULL      );
    	/// status =      CPXaddrows (        env, lp, colcnt, rowcnt, nzcnt     , rhs  , sense , rmatbeg, rmatind, rmatval , newcolname, newrowname);
	}

	// add capacity constraints (origin) [ forall i, sum{j} x_ij <= D_j ] ///TODO-done
	for (int i = 0; i < I; i++)
	{
		std::vector<int> idx(J);
		std::vector<double> coef(J, 1.0);
		char sense = 'L';
		for (int j = 0; j < J; j++)
		{
			idx[j] = map_x[i][j]; //i*J + j; // corresponds to variable x_ij
		}
		int matbeg = 0;
		CHECKED_CPX_CALL( CPXaddrows, env, lp, 0, 1, idx.size(), &D[i], &sense, &matbeg, &idx[0], &coef[0], 0, 0 );
	}

	// add linking constraints (x_ij - K y_ij <= 0 -- all variables on the left side!!!)
	for ( int i = 0 ; i < I ; ++i ) {
		for ( int j = 0 ; j < J ; ++j ) {
			std::vector<int> idx(2);
			idx[0] = map_x[i][j]; //i*J + j;;        // x var
			idx[1] = map_y[i][j]; //I*J+i*J+j;   // y var
			std::vector<double> coef(2); 
			coef[0] = 1.0;
			coef[1] = -K;
			char sense = 'L';
			double rhs = 0.0;
			int matbeg = 0;
			CHECKED_CPX_CALL( CPXaddrows, env, lp, 0, 1, 2, &rhs, &sense, &matbeg, &idx[0], &coef[0], 0, 0 );
		}
	}

	
	// add counting constraint (sum_ij y_ij - z <= N)
	{
		std::vector<int> idx;
		std::vector<double> coef;
		for ( int i = 0 ; i < I ; ++i ) {
			for ( int j = 0 ; j < J ; ++j ) {
				idx.push_back(map_y[i][j]);
				coef.push_back(1.0);
			}
		}
		idx.push_back(map_z);	// z    var
		coef.push_back(-1.0);   // coeff for z
		char sense = 'L';
		int matbeg = 0;
		CHECKED_CPX_CALL( CPXaddrows, env, lp, 0, 1, idx.size(), &N, &sense, &matbeg, &idx[0], &coef[0], 0, 0 );
	}
	
	// print (debug)
	CHECKED_CPX_CALL( CPXwriteprob, env, lp, "ironrods_map.lp", NULL );
	/// status =      CPXwriteprob (env, lp, "myprob"    , filetype_str);
}


int main (int argc, char const *argv[])
{
	try
	{
		// init
		DECL_ENV( env );
		DECL_PROB( env, lp );
		// setup LP
		setupLP(env, lp);
		// optimize
		CHECKED_CPX_CALL( CPXmipopt, env, lp );
		// print
		double objval;
		CHECKED_CPX_CALL( CPXgetobjval, env, lp, &objval );
		std::cout << "Objval: " << objval << std::endl;
		int n = CPXgetnumcols(env, lp);
		if (n != 2*I*J+1) { throw std::runtime_error(std::string(__FILE__) + ":" + STRINGIZE(__LINE__) + ": " + "different number of variables"); }
	  	std::vector<double> varVals;
		varVals.resize(n);
  		CHECKED_CPX_CALL( CPXgetx, env, lp, &varVals[0], 0, n - 1 );
		/// status =      CPXgetx (env, lp, x          , 0, CPXgetnumcols(env, lp)-1);
  		for ( int i = 0 ; i < n ; ++i ) {
  	  	std::cout << "var in position " << i << " : " << varVals[i] << std::endl;
  	  		/// to read variables names the API function ``CPXgetcolname'' may be used (it is rather tricky, see the API manual if you like...)
  	  		/// status = CPXgetcolname (env, lp, cur_colname, cur_colnamestore, cur_storespace, &surplus, 0, cur_numcols-1);
  		}
		CHECKED_CPX_CALL( CPXsolwrite, env, lp, "ironrods.sol" );
		// free
		CPXfreeprob(env, &lp);
		CPXcloseCPLEX(&env);
	}
	catch(std::exception& e)
	{
		std::cout << ">>>EXCEPTION: " << e.what() << std::endl;
	}
	return 0;
}

