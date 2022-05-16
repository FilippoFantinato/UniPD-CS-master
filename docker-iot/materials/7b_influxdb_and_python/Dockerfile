FROM alpine

RUN apk add --update py3-pip
RUN pip3 install -U influxdb

WORKDIR /home/
COPY example2.py .

CMD ["python3", "example2.py"]
