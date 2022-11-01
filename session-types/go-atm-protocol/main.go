package main

import (
	"fmt"
	"sync"
)

type ATM struct {
	transfer chan bool
	deposit  chan bool
	quit     chan bool

	name        chan string
	bank_addess chan string
	money       chan int

	approved chan int
	err      chan string
}

func (atm *ATM) Run(wg *sync.WaitGroup) {
	defer wg.Done()
	name := <-atm.name
	fmt.Println("Hi", name)
	balance := 400
	stay := true
	for stay {
		select {
		case <-atm.deposit:
			money := <-atm.money
			if money > 0 {
				fmt.Printf("Depositing %d\n", money)
				balance = balance + money
				atm.approved <- balance
			} else {
				atm.err <- "Error: the amount of money is negative or equal to 0"
			}

		case <-atm.transfer:
			bank_address := <-atm.bank_addess
			money := <-atm.money
			if balance >= money && money > 0 {
				fmt.Printf("Transferring From %s to %s %d\n", name, bank_address, money)
				balance = balance - money
				atm.approved <- balance
			} else {
				atm.err <- "Error: not enough money"
			}

		case <-atm.quit:
			fmt.Println("Bye bye", name)
			stay = false
		}
	}
}

func costumer_deposit(name string, atm ATM, wg *sync.WaitGroup) {
	defer wg.Done()

	atm.name <- name
	atm.deposit <- true
	atm.money <- 100

	select {
	case newBalance := <-atm.approved:
		fmt.Println("Now my balance is", newBalance)
	case e := <-atm.err:
		fmt.Println(e)
	}

	atm.quit <- true
}

func costumer_transfer(name string, receiver string, atm ATM, wg *sync.WaitGroup) {
	defer wg.Done()

	atm.name <- name
	atm.transfer <- true
	atm.bank_addess <- receiver
	atm.money <- 300

	select {
	case newBalance := <-atm.approved:
		fmt.Println("Now my balance is", newBalance)
	case e := <-atm.err:
		fmt.Println(e)
	}

	atm.quit <- true
}

func main() {
	var wg sync.WaitGroup

	runs := 2
	atm := ATM{
		make(chan bool),
		make(chan bool),
		make(chan bool),
		make(chan string),
		make(chan string),
		make(chan int),
		make(chan int),
		make(chan string),
	}

	go atm.Run(&wg)

	wg.Add(runs)
	go costumer_transfer("Filippo", "Alice", atm, &wg)

	wg.Wait()
}
