# Assignment2 - Filippo Fantinato 2041620

> Exercise: Dockerize sisubttn2.py and explain why to run the just created image i need to add the -t option

Here below you can find the content of the Dockerfile:

```dockerfile
FROM alpine # Create the container starting from Alpine

RUN apk add --update py3-pip # Install python3 and pip3 via apk
RUN pip3 install -U paho-mqtt # Install paho-mqtt via pip3

WORKDIR /home/ # Move to /home/ directory
COPY sisubttn2.py . # Copy sisubttn2.py to /home/ into the image 

CMD ["python3", "sisubttn2.py"] # Execute the file sisubttn2.py
```

Then i can build the image executing this command:

```bash
$ docker build -t filippofantinato/mysubttn:v0.1 .
```

and run it via:

```bash
$ docker run -t filippofantinato/mysubttn:v0.1
```

## Why do I need the -t option?

This image, as I've already said, needs to be run with the -t option in order to see something being printed by the program we're running. That's because docker prints on the caller stdout once it ends, but in this particular case the program will never stop since it's supposed to run forever, waiting for some events. Therefore, the image needs to be run with -t option to connect to a tty in the container, where the program prints its output, so we can see what it's printing.
