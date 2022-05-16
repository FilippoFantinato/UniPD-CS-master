FROM alpine

RUN apk add --update py3-pip
RUN pip3 install -U paho-mqtt

WORKDIR /home/
COPY sisub.py .

ENTRYPOINT ["python3", "sisub.py"]
