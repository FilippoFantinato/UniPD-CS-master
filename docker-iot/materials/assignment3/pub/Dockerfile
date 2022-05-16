FROM alpine

RUN apk add --update py3-pip
RUN pip3 install -U paho-mqtt

WORKDIR /home/
COPY sipub.py .

ENTRYPOINT ["python3", "sipub.py"]
