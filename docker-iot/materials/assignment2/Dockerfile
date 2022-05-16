FROM alpine

RUN apk add --update py3-pip
RUN pip3 install -U paho-mqtt

WORKDIR /home/
COPY sisubttn2.py .

CMD ["python3", "sisubttn2.py"]
