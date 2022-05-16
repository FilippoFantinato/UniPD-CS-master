FROM alpine

RUN apk add --update py3-pip

RUN pip3 install -U Flask

WORKDIR /home/

COPY api.py .

EXPOSE 5000

CMD ["python3", "/home/api.py"]
