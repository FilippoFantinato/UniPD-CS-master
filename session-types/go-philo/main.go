package main

import (
	"fmt"
)

const NPhil int = 5

type Fork struct {
	name  string
	free  bool
	Take  chan int
	Leave chan int
}

func (self *Fork) Run() {
	for {
		<-self.Take
		fmt.Printf("Fork %s taken\n", self.name)
		<-self.Leave
		fmt.Printf("Fork %s released\n", self.name)
	}
}

func Phil(id int, left *Fork, right *Fork) {
	for {
		fmt.Printf("Phil %d is thinking ...\n", id)
		// time.Sleep(time.Duration(rand.Int63n(2 * 1e9)))
		// time.Sleep(time.Duration(rand.Int63n(2 * 1e9)))
		right.Take <- 1
		left.Take <- 1
		fmt.Printf("Philosopher %d is eating ...\n", id)
		left.Leave <- 1
		right.Leave <- 1
	}
}

func main() {
	var forks [NPhil]Fork

	for i := 0; i < NPhil; i++ {
		// create the ith-fork
		forks[i] = Fork{
			fmt.Sprintf("F%d", i),
			true,
			make(chan int),
			make(chan int),
		}
		go forks[i].Run()
	}

	for i := 0; i < NPhil-1; i++ {
		go Phil(i, &forks[i], &forks[(i+1)%NPhil])
	}

	Phil(NPhil-1, &forks[0], &forks[NPhil-1])
}
